package lu.wealins.batch.extract.bloomberg;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lu.wealins.camel.utils.MessageUtils;
import lu.wealins.rest.model.soliam.ExtractSoliamFundsToGetVniResponse;
import lu.wealins.utils.BasicFileWriter;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;

/**
 * Tasklet to get fund which need a vni. Build a file with all information related to fund for Bloomberg
 * 
 * @author xqv60
 *
 */
@EnableScheduling
public class ExtractSoliamFundsToGetVniTasklet implements Tasklet {
	/**
	 * The Logger
	 */
	private Log logger = LogFactory.getLog(ExtractSoliamFundsToGetVniTasklet.class);

	/**
	 * The DEFAULT PAGE SIZE FOR THE PAGINATION
	 */
	private final static int PAGE_SIZE = 500;

	/**
	 * Constant JOB Parameters extractor
	 */
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	/**
	 * Constant EOF for the output file
	 */
	private static final String EOF = "\r\n";

	/**
	 * Constant job name
	 */
	private static final String EXTRACT_JOB = "extractSoliamFundToGetVniJob";

	/**
	 * Constant extract fund from soliam url
	 */
	private static final String EXTRACT_SOLIAM_FUNDS_URL = "soliam/funds/needVni";

	/**
	 * Constant FILE EXTENSION FOR BLOOMBERG
	 */
	private final static String FILE_EXTENSION = ".req";

	/**
	 * Constant BLOOMBERG_FILENAME
	 */
	private final static String BLOOMBERG_FILENAME = "vni";

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;

	/**
	 * The output file
	 */
	private String soliamFundsFileName;
	private File lissiaEstimatedOrderFile;
	private BasicFileWriter soliamFundsFile;
	
	@Autowired
	@Qualifier("connectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Value("${soliamVniQueue}")
	private String queue;
	
	@Value("${extractSoliamFundToGetVniRequestFolder}")
	private String requestFolder;


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		boolean failed = false;
		
		try {
			initFiles();

			int page = 0;
			ExtractSoliamFundsToGetVniResponse extractSoliamFundsToGetVniResponse = null;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			do {
				extractSoliamFundsToGetVniResponse = extractSoliamFunds(piaRootContextURL + EXTRACT_SOLIAM_FUNDS_URL, page);
				page++;
				if (extractSoliamFundsToGetVniResponse == null) {
					logger.error("Erreur de génération des extraction des fonds de Soliam pour Bloomberg: response is null ");
				} else {
					if (!StringUtils.isEmpty(extractSoliamFundsToGetVniResponse.getError())) {
						logger.error("Erreur de génération des extraction des fonds de Soliam pour Bloomberg: " + extractSoliamFundsToGetVniResponse.getError());
					} else {
						writeBloombergFile(extractSoliamFundsToGetVniResponse);
					}
				}
			} while (!extractSoliamFundsToGetVniResponse.isLastPage());

		} catch (Exception e) {
			failed = true;
			logger.error("Erreur de génération des extraction des fonds de Soliam pour Bloomberg \n " + e);
			throw e;
		} finally {
			soliamFundsFile.close();
			
			if(lissiaEstimatedOrderFile.exists()) {
				if(!failed) {
					MessageUtils.sendFileToArtemis(connectionFactory, queue, lissiaEstimatedOrderFile, soliamFundsFileName, new HashMap<String, String>());
				}
			}
		}
		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${extractSoliamFundToGetVniCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of soliam fund for bloomberg (get VNI)");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters).addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * Extract the funds from SOLIAM (those which need a VNI)
	 * 
	 * @param url the rest url
	 * @param page the page requested
	 * @return
	 */
	private ExtractSoliamFundsToGetVniResponse extractSoliamFunds(String url, int page) {
		ExtractSoliamFundsToGetVniResponse extractSoliamFundsResponse = new ExtractSoliamFundsToGetVniResponse();
		ResponseEntity<ExtractSoliamFundsToGetVniResponse> extractSoliamFundsRest = extractSoliamFundsRest(url, page);
		if (extractSoliamFundsRest != null && extractSoliamFundsRest.getBody() != null) {
			extractSoliamFundsResponse = extractSoliamFundsRest.getBody();
		}
		return extractSoliamFundsResponse;
	}

	/**
	 * Rest call http
	 * 
	 * @param url the url of the web service
	 * @param page the page requested
	 * @return the rest response
	 */
	private ResponseEntity<ExtractSoliamFundsToGetVniResponse> extractSoliamFundsRest(String url, int page) {
		logger.debug("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		logger.debug("Token response " + tokenResponse);
		String token = tokenResponse.getToken();
		logger.info("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build();

		RestTemplate template = new RestTemplate();
		ResponseEntity<ExtractSoliamFundsToGetVniResponse> response = template.exchange(uriComponents.toUri(), HttpMethod.GET, entity, ExtractSoliamFundsToGetVniResponse.class);
		logger.info("Response page : " + page + " finished");
		return response;
	}

	/**
	 * Build the file for Bloomberg with the funds information to get VNI
	 * 
	 * @param extractSoliamFundsToGetVniResponse the list of funds which need a VNI
	 */
	private void writeBloombergFile(ExtractSoliamFundsToGetVniResponse extractSoliamFundsToGetVniResponse) {
		if (extractSoliamFundsToGetVniResponse.isFirstPage()) {
			writeHeader(soliamFundsFile);
			logger.debug("Bloomberg header's file built");
		}
		writeSoliamFunds(extractSoliamFundsToGetVniResponse, soliamFundsFile);
		logger.debug("Bloomberg body's file is building...");

		if (extractSoliamFundsToGetVniResponse.isLastPage()) {
			writeFooter(soliamFundsFile);
			logger.debug("Bloomberg footer's file built");
		}
	}

	/**
	 * Build the header of the soliam file
	 * 
	 * @param fileWriter the file writer of the soliam funds file
	 * 
	 *            Header example :
	 * 
	 *            START-OF-FILE PROGRAMFLAG=oneshot PROGRAMNAME=getdata FIRMNAME=dl785184 CLOSINGVALUES=Yes DATEFORMAT=yyyymmdd DERIVED=Yes SECID=ISIN SECMASTER=yes SN=683033 USERNUMBER=4134189 WS=0
	 *            START-OF-FIELDS ID_ISIN PX_YEST_DT PX_YEST_BID PX_YEST_MID PX_YEST_ASK PX_CLOSE_DT PRICING_SOURCE PRIOR_CLOSE_ASK PRIOR_CLOSE_BID PRIOR_CLOSE_MID END-OF-FIELDS START-OF-DATA
	 * 
	 */
	private void writeHeader(BasicFileWriter fileWriter) {
		fileWriter.append(EOF, StringUtils.EMPTY, "START-OF-FILE");
		fileWriter.append(EOF, StringUtils.EMPTY, "PROGRAMFLAG=oneshot");
		fileWriter.append(EOF, StringUtils.EMPTY, "PROGRAMNAME=getdata");
		fileWriter.append(EOF, StringUtils.EMPTY, "FIRMNAME=dl785184");
		fileWriter.append(EOF, StringUtils.EMPTY, "CLOSINGVALUES=Yes");
		fileWriter.append(EOF, StringUtils.EMPTY, "DATEFORMAT=yyyy/mm/dd");
		fileWriter.append(EOF, StringUtils.EMPTY, "DERIVED=Yes");
		fileWriter.append(EOF, StringUtils.EMPTY, "SECID=ISIN");
		fileWriter.append(EOF, StringUtils.EMPTY, "SECMASTER=yes");
		fileWriter.append(EOF, StringUtils.EMPTY, "SN=884060");
		fileWriter.append(EOF, StringUtils.EMPTY, "USERNUMBER=8910621");
		fileWriter.append(EOF, StringUtils.EMPTY, "WS=0");
		fileWriter.append(EOF, StringUtils.EMPTY, "START-OF-FIELDS");// Define a list of field that we need in the response. We want :
		fileWriter.append(EOF, StringUtils.EMPTY, "ID_ISIN"); // ISIN CODE
		fileWriter.append(EOF, StringUtils.EMPTY, "PX_YEST_CLOSE");
		fileWriter.append(EOF, StringUtils.EMPTY, "PX_YEST_DT");
		fileWriter.append(EOF, StringUtils.EMPTY, "CRNCY"); // CURRENCY
		fileWriter.append(EOF, StringUtils.EMPTY, "PRICING_SOURCE");
		fileWriter.append(EOF, StringUtils.EMPTY, "END-OF-FIELDS");
		fileWriter.append(EOF, StringUtils.EMPTY, "START-OF-DATA");
	}

	/**
	 * Build the body of the soliam file.
	 * 
	 * Add any isin code in the ouput file
	 * 
	 * @param extractSoliamFundsToGetVniResponse
	 * @param fileWriter the file writer of the soliam funds file
	 */
	private void writeSoliamFunds(ExtractSoliamFundsToGetVniResponse extractSoliamFundsToGetVniResponse, BasicFileWriter fileWriter) {
		if (CollectionUtils.isEmpty(extractSoliamFundsToGetVniResponse.getIsins())) {
			logger.info("No isin code fo add in the output file");
		} else {
			for (String isin : extractSoliamFundsToGetVniResponse.getIsins()) {
				fileWriter.append(EOF, StringUtils.EMPTY, isin);

			}
		}
	}

	/**
	 * Build the footer of the soliam file
	 * 
	 * @param fileWriter the file writer of the soliam funds file
	 * 
	 *            Footer Example : END-OF-DATA END-OF-FILE
	 */
	private void writeFooter(BasicFileWriter fileWriter) {
		fileWriter.append(EOF, StringUtils.EMPTY, "END-OF-DATA");
		fileWriter.append(EOF, StringUtils.EMPTY, "END-OF-FILE");
	}

	/**
	 * Initialize the output files
	 * @throws IOException 
	 */
	private void initFiles() throws IOException {
		// Initialization of output file
		soliamFundsFileName = BLOOMBERG_FILENAME + getCurrentDate() + FILE_EXTENSION;
		lissiaEstimatedOrderFile = Paths.get(requestFolder).resolve(soliamFundsFileName).toFile();
		soliamFundsFile = new BasicFileWriter(lissiaEstimatedOrderFile.getAbsolutePath());
	}

	/**
	 * Get the current date
	 * 
	 * @return the current date
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		return sdf.format(date);
	}
}
