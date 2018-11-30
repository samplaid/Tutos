package lu.wealins.batch.extract.bloomberg;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.ConnectionFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import lu.wealins.camel.utils.MessageUtils;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.CheckSignaletiqueResponse;
import lu.wealins.common.dto.webia.services.SasIsinDTO;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueRequest;
import lu.wealins.common.dto.webia.services.SaveSignaletiqueResponse;
import lu.wealins.utils.BasicFileWriter;
import lu.wealins.utils.CsvFileReader;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

/**
 * Tasklet to get fund which need a signaletic. Build a request file with all information related to fund for Bloomberg.
 * 
 * 
 * @author xqv60
 *
 */
@EnableScheduling
public class ExtractSignaleticFundsTasklet implements Tasklet {
	/**
	 * The constant ERROR_ISIN_B2
	 */
	private static final String ERROR_ISIN_B2 = "CURR_ACC";

	/**
	 * The constant ERROR_CURRENCY_B2
	 */
	private static final String ERROR_CURRENCY_B2 = "UNKNOWN";

	/**
	 * Constant check webia signaletic funds url
	 */
	private static final String CHECK_WEBIA_SIGNALETIC_FUNDS_URL = "webia/signaletique/isAvailableInBloomberg";

	/**
	 * Constant save webia signaletic funds url
	 */
	private static final String SAVE_WEBIA_SIGNALETIC_FUNDS_URL = "webia/signaletique/save";

	/**
	 * Constant FILE EXTENSION FOR BLOOMBERG
	 */
	private final static String FILE_EXTENSION = ".req";

	/**
	 * Constant BLOOMBERG_FILENAME
	 */
	private final static String BLOOMBERG_FILENAME = "sig";

	/**
	 * Constant EOF for the output file
	 */
	private static final String EOF = "\r\n";

	/**
	 * The Logger
	 */
	private Log logger = LogFactory.getLog(ExtractSignaleticFundsTasklet.class);

	/**
	 * Constant JOB Parameters extractor
	 */
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	/**
	 * Constant job name
	 */
	private static final String EXTRACT_JOB = "extractSignaleticFundsJob";

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	/**
	 * Root Folder
	 */
	@Value("${inputSignaleticBloombergFolder}")
	private String inputSignaleticBloombergFolder;

	/**
	 * output Folder with the files treated
	 */
	@Value("${outputSignaleticFolder}")
	private String outputSignaleticFolder;
	
	@Autowired
	@Qualifier("connectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Value("${signaleticRequestQueue}")
	private String queue;
	
	@Value("${signaleticRequestSubFolder}")
	private String requestFolder;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;

	// Could be improved
	/** Regex matching an isin code. */
	private String IsinRegex = "[a-zA-Z]{2}[a-zA-Z0-9]{10}";

	/** pattern to use in order to find cell containing comma. */
	private Pattern pattern = Pattern.compile("\".*\"");

	/**
	 * The output file
	 */
	private String signaleticFundsFileName;
	private File requestFile;
	private BasicFileWriter signaleticFundsFile;

	/**
	 * The outputPath
	 */
	private Path outputPath;


	/**
	 * Add cron on the job
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "${signaleticBloombergCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of signaletic fund for bloomberg");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB, new JobParametersBuilder(jobParameters)
				.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info(EXTRACT_JOB + " started ");
		List<SasIsinDTO> sasIsinDtos = readInputFiles();
		logger.info(sasIsinDtos.size() + " isin to treat");

		if (!CollectionUtils.isEmpty(sasIsinDtos)) {
			initOutputFile();

			boolean firstPage = true;
			boolean lastPage = false;
			int cpt = 0;
			int nbSaved = 0;

			for (SasIsinDTO current : sasIsinDtos) {

				// Call webia service to handle the check
				CheckSignaletiqueResponse checkIsinsResponse = checkIsins(piaRootContextURL + CHECK_WEBIA_SIGNALETIC_FUNDS_URL, Arrays.asList(current));
				if (checkIsinsResponse == null) {
					logger.error("Erreur de verification des isins pour Bloomberg: response is null ");
				} else {
					// If last loop
					if (cpt == sasIsinDtos.size() - 1) {
						lastPage = true;
					}
					// Write bloomberg file
					writeBloombergFile(checkIsinsResponse.getIsinMissing(), firstPage, lastPage);
					firstPage = false;
					cpt++;

					// Call webia service to handle the save
					if (!CollectionUtils.isEmpty(checkIsinsResponse.getIsinMissing())) {
						SaveSignaletiqueResponse saveIsinsResponse = saveIsins(piaRootContextURL + SAVE_WEBIA_SIGNALETIC_FUNDS_URL, new ArrayList<SasIsinDTO>(checkIsinsResponse.getIsinMissing()));
						if (saveIsinsResponse != null && !saveIsinsResponse.isSuccess()) {
							logger.error("Error during the save of the isin send to bloomberg");
						} else {
							nbSaved++;

						}
					}
				}
			}

			logger.info(nbSaved + " isin to save");
			if(requestFile.exists()) {
				MessageUtils.sendFileToArtemis(connectionFactory, queue, requestFile, signaleticFundsFileName, new HashMap<String, String>());
			}
		}
		logger.info(EXTRACT_JOB + " finished ");

		return RepeatStatus.FINISHED;

	}

	/**
	 * Read the input files and get the isin from the input files
	 * 
	 * @return the isin from the input files
	 * @throws IOException
	 */
	private List<SasIsinDTO> readInputFiles() throws IOException {
		List<SasIsinDTO> sasIsinDtos = new ArrayList<>();
		// Read the B2 signaletic folder
		logger.info("Parsing b2 signaletic folder : " + inputSignaleticBloombergFolder);
		// Initialize output folder (to move the input file into)
		outputPath = Paths.get(outputSignaleticFolder);

		Files.list(Paths.get(inputSignaleticBloombergFolder)).forEach(file -> {

			try {
				// Parse B2 file
				sasIsinDtos.addAll(parseFile(file.toFile()));
				// We move the input file into the output folder
				logger.debug("Move " + file.getFileName() + " into " + outputPath.getFileName());
				Files.move(Paths.get(inputSignaleticBloombergFolder).resolve(file.getFileName()), outputPath.resolve(file.getFileName() + getCurrentDate()));
			} catch (IOException e) {
				logger.error(e);
			}
		});

		return sasIsinDtos;
	}

	/**
	 * Save the isin into Webia
	 * 
	 * @param url the rest url
	 * @param currentParition the list to check
	 * 
	 * @return
	 */
	private SaveSignaletiqueResponse saveIsins(String url, List<SasIsinDTO> currentParition) {
		SaveSignaletiqueRequest request = new SaveSignaletiqueRequest();
		request.setIsinData(currentParition);
		ParameterizedTypeReference<SaveSignaletiqueResponse> responseType = new ParameterizedTypeReference<SaveSignaletiqueResponse>() {
		};
		ResponseEntity<SaveSignaletiqueResponse> response = RestCallUtils.postRest(url, request, SaveSignaletiqueRequest.class, responseType,
				keycloackUtils, logger);

		return response.getBody();
	}

	/**
	 * Check the isin into Webia
	 * 
	 * @param url the rest url
	 * @param currentParition the list to check
	 * 
	 * @return
	 */
	private CheckSignaletiqueResponse checkIsins(String url, List<SasIsinDTO> currentParition) {
		CheckSignaletiqueRequest request = new CheckSignaletiqueRequest();
		request.setIsinData(currentParition);
		ParameterizedTypeReference<CheckSignaletiqueResponse> responseType = new ParameterizedTypeReference<CheckSignaletiqueResponse>() {
		};
		ResponseEntity<CheckSignaletiqueResponse> response = RestCallUtils.postRest(url, request, CheckSignaletiqueRequest.class, responseType,
				keycloackUtils, logger);

		return response.getBody();
	}

	/**
	 * Specific parsing for B2 file
	 * 
	 * Example of line : IPL,,1,DB00003,30.11.2017,3651601,EUR,FR0000120271,562,Total SA,30.11.2017,47.405,1,EUR,1,0,26641.61,26641.61,
	 * 
	 * 
	 * @param file the b2 file
	 * @return the result of parsing
	 * @throws IOException
	 */
	private List<SasIsinDTO> parseFile(File file) throws IOException {

		List<SasIsinDTO> sasIsinDtos = new ArrayList<>();

		CsvFileReader csvFileReader = new CsvFileReader(file);

		csvFileReader.getLines().forEach(line -> {

			// replace some cell content if it contains comma
			line = replaceCellContent(line);

			String[] split = line.split(CsvFileReader.COMMA_DELIMITER);
			if (split.length > 13) {
				String currency = split[13];
				String isin = split[7];
				String bic = split[3];
				String fundTitle = split[9];

				// If ISIN code and currency are valid
				if (StringUtils.isNotEmpty(isin) && !isin.contains(ERROR_ISIN_B2) && StringUtils.isNotEmpty(currency) && !currency.equals(ERROR_CURRENCY_B2)
						&& StringUtils.isNotEmpty(bic)) {
					SasIsinDTO sasIsin = new SasIsinDTO();
					sasIsin.setCurrency(currency);
					sasIsin.setIsin(isin);
					sasIsin.setBankBic(bic);
					sasIsin.setFundTitle(fundTitle);
					sasIsinDtos.add(sasIsin);

				}
			}
		});
		csvFileReader.close();
		return sasIsinDtos;
	}

	/**
	 * Replace some cell content if it contains comma
	 * 
	 * @param line the line to process
	 * @return the line processed
	 */
	private String replaceCellContent(String line) {
		Matcher matcher = pattern.matcher(line);

		Map<String, String> replacementMap = new HashMap<String, String>();
		while (matcher.find()) {
			String cellContent = line.substring(matcher.start(), matcher.end());
			String newCellContent = cellContent.replaceAll(",", ".");
			newCellContent = newCellContent.replaceAll("\"", "");
			newCellContent = newCellContent.trim();

			replacementMap.put(cellContent, newCellContent);
		}

		for (Entry<String, String> entry : replacementMap.entrySet()) {
			line = line.replace(entry.getKey(), entry.getValue());
		}
		return line;
	}

	/**
	 * Build the file for Bloomberg with the isin information to get signaletic fund
	 * 
	 * @param checkIsinsResponse the list of isin which need a signaletic
	 * @param firstPage
	 * @throws IOException
	 */
	private void writeBloombergFile(Collection<SasIsinDTO> sasIsins, boolean firstPage, boolean lastPage) throws IOException {
		if (firstPage) {
			writeHeader(signaleticFundsFile);
			logger.debug("Bloomberg header's file built");
		}
		writeSignaleticFunds(sasIsins, signaleticFundsFile);
		logger.debug("Bloomberg body's file is building...");

		if (lastPage) {
			writeFooter(signaleticFundsFile);
			signaleticFundsFile.close();
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
	 *            START-OF-FILE PROGRAMFLAG=oneshot PROGRAMNAME=getdata FIRMNAME=dl785184 CLOSINGVALUES=Yes DATEFORMAT=yyyy/mm/dd DERIVED=Yes SECID=ISIN SECMASTER=yes SN=884060 USERNUMBER=8910621 WS=0
	 *            START-OF-FIELDS ID_ISIN CRNCY BBG_NAME CIE_DES EXCH_NAMES LISTED_EXCH FUND_ASSET_ALLOC_EQUITY FUND_BENCHMARK_PRIM COUNTRY_FULL_NAME LONG_COMP_NAME UNDERLYING_SECURITY_DES
	 *            FUND_MANAGEMENT_CO_LONG ID_BB_COMPANY ISSUER ZERO_CPN IS_PERPETUAL CNTRY_OF_DOMICILE MATURITY FUND_ASSET_CLASS_FOCUS FUND_EURO_DIRECT_UCIT FUND_LEVERAGE DEFAULTED EXCH_MARKET_STATUS
	 *            PRIMARY_EXCHANGE_NAME COMPOSITE_EXCH_CODE FUND_TYP INDUSTRY_SECTOR INDUSTRY_GROUP FUND_ASSET_ALLOC_CALC SECURITY_NAME CPN SECURITY_TYP2 SECURITY_TYP MARKET_SECTOR_DES EXCH_CODE
	 *            END-OF-FIELDS START-OF-DATA
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
		fileWriter.append(EOF, StringUtils.EMPTY, "CRNCY"); // CURRENCY
		fileWriter.append(EOF, StringUtils.EMPTY, "BBG_NAME");
		fileWriter.append(EOF, StringUtils.EMPTY, "CIE_DES");
		fileWriter.append(EOF, StringUtils.EMPTY, "EXCH_NAMES");
		fileWriter.append(EOF, StringUtils.EMPTY, "LISTED_EXCH");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_ASSET_ALLOC_EQUITY");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_BENCHMARK_PRIM");
		fileWriter.append(EOF, StringUtils.EMPTY, "COUNTRY_FULL_NAME");
		fileWriter.append(EOF, StringUtils.EMPTY, "LONG_COMP_NAME");
		fileWriter.append(EOF, StringUtils.EMPTY, "UNDERLYING_SECURITY_DES");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_MANAGEMENT_CO_LONG");
		fileWriter.append(EOF, StringUtils.EMPTY, "ID_BB_COMPANY");
		fileWriter.append(EOF, StringUtils.EMPTY, "ISSUER");
		fileWriter.append(EOF, StringUtils.EMPTY, "ZERO_CPN");
		fileWriter.append(EOF, StringUtils.EMPTY, "IS_PERPETUAL");
		fileWriter.append(EOF, StringUtils.EMPTY, "CNTRY_OF_DOMICILE");
		fileWriter.append(EOF, StringUtils.EMPTY, "MATURITY");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_ASSET_CLASS_FOCUS");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_EURO_DIRECT_UCIT");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_LEVERAGE");
		fileWriter.append(EOF, StringUtils.EMPTY, "DEFAULTED");
		fileWriter.append(EOF, StringUtils.EMPTY, "EXCH_MARKET_STATUS");
		fileWriter.append(EOF, StringUtils.EMPTY, "PRIMARY_EXCHANGE_NAME");
		fileWriter.append(EOF, StringUtils.EMPTY, "COMPOSITE_EXCH_CODE");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_TYP");
		fileWriter.append(EOF, StringUtils.EMPTY, "INDUSTRY_SECTOR");
		fileWriter.append(EOF, StringUtils.EMPTY, "INDUSTRY_GROUP");
		fileWriter.append(EOF, StringUtils.EMPTY, "FUND_ASSET_ALLOC_CALC");
		fileWriter.append(EOF, StringUtils.EMPTY, "SECURITY_NAME");
		fileWriter.append(EOF, StringUtils.EMPTY, "CPN");
		fileWriter.append(EOF, StringUtils.EMPTY, "SECURITY_TYP2");
		fileWriter.append(EOF, StringUtils.EMPTY, "SECURITY_TYP");
		fileWriter.append(EOF, StringUtils.EMPTY, "MARKET_SECTOR_DES");
		fileWriter.append(EOF, StringUtils.EMPTY, "EXCH_CODE");
		fileWriter.append(EOF, StringUtils.EMPTY, "CPN_FREQ");
		fileWriter.append(EOF, StringUtils.EMPTY, "CPN_TYP");
		fileWriter.append(EOF, StringUtils.EMPTY, "FIRST_CPN_DT");
		fileWriter.append(EOF, StringUtils.EMPTY, "ISSUE_DT");
		fileWriter.append(EOF, StringUtils.EMPTY, "ISSUE_PX");
		fileWriter.append(EOF, StringUtils.EMPTY, "PX_LAST");
		fileWriter.append(EOF, StringUtils.EMPTY, "DAY_CNT");
		fileWriter.append(EOF, StringUtils.EMPTY, "END-OF-FIELDS");
		fileWriter.append(EOF, StringUtils.EMPTY, "START-OF-DATA");
	}

	/**
	 * Build the body of the bloomberg file.
	 * 
	 * Add any isin code in the ouput file
	 * 
	 * @param checkIsinsResponse the list of isin
	 * @param fileWriter the file writer of the signaletic funds file
	 */
	private void writeSignaleticFunds(Collection<SasIsinDTO> sasIsins, BasicFileWriter fileWriter) {
		if (!CollectionUtils.isEmpty(sasIsins)) {
			for (SasIsinDTO sasIsinDTO : sasIsins) {
				fileWriter.append(EOF, StringUtils.EMPTY, sasIsinDTO.getIsin(), "||1|EQY_FUND_CRNCY|", sasIsinDTO.getCurrency());
			}
		}
	}

	/**
	 * Build the footer of the signaletic request file
	 * 
	 * @param fileWriter the file writer of the signaletic request file
	 * 
	 *            Footer Example : END-OF-DATA END-OF-FILE
	 */
	private void writeFooter(BasicFileWriter fileWriter) {
		fileWriter.append(EOF, StringUtils.EMPTY, "END-OF-DATA");
		fileWriter.append(EOF, StringUtils.EMPTY, "END-OF-FILE");
	}

	/**
	 * Initialize the output file
	 * @throws IOException 
	 */
	private void initOutputFile() throws IOException {
		// Initialization of output file
		signaleticFundsFileName = BLOOMBERG_FILENAME + getCurrentDate() + FILE_EXTENSION;
		
		requestFile = Paths.get(requestFolder).resolve(signaleticFundsFileName).toFile();
		signaleticFundsFile = new BasicFileWriter(requestFile.getAbsolutePath());
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
