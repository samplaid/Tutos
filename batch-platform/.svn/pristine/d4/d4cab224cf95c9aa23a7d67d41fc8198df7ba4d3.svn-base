/**
 * 
 */
package lu.wealins.batch.extract;

import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.KeycloakUtils;
import lu.wealins.utils.RestCallUtils;

/**
 * @author xqv95
 *
 */
@EnableScheduling
public class ExtractSapAccountingTaskLet implements Tasklet {


	private Log logger = LogFactory.getLog(ExtractSapAccountingTaskLet.class);
	private final static int PAGE_SIZE = 70000;

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;
	@Value("${sapAccountingInjectionSuccessFile}")
	private String sapAccountingInjectionSuccessFile;
	@Value("${sapAccountingInjectionErrorFile}")
	private String sapAccountingInjectionErrorFile;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;
	
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "extractSapAccountingJob";
	private static final String EXTRACT_SAP_ACCOUNTING = "webia/extract/sap-accounting";
	private static final String EXTRACT_SAP_ACCOUNTING_CONFIRM = "webia/extract/sap-accounting/confirm";
	private static final String[] EXTRACT_ORIGIN_LIST = { "LISSIA", "DALI" };

	private CsvFileWriter successFileWriter;
	private CsvFileWriter failureFileWriter;
	private String successFilePath;
	private String failureFilePath;
	
	private Collection<Long> successIds = new HashSet<>();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				
		for (String origin : EXTRACT_ORIGIN_LIST) {
			// extract account transactions from Lissia
			PageResult<SapAccountingDTO> sapAccountings = null;
			int page = 0;
			Long lastId = 0L;
			try {
				initFiles(origin);
				
				do {			
					sapAccountings = extractSapAccountings(origin, page, lastId);

					if (sapAccountings != null) {
						// get last id
						Optional<SapAccountingDTO> result = sapAccountings.getContent().stream().max((x, y) -> Long.compare(x.getIdSapAcc(), y.getIdSapAcc()));
						if (result.isPresent()) {
							lastId = result.get().getIdSapAcc();
						}
						
						logger.debug("lastId " + lastId);
						writeSapAccounting(sapAccountings.getContent());
						if (!successIds.isEmpty()) {
							confirmWriteSapAccounting(origin);
						}
					}
				} while (sapAccountings != null && sapAccountings.hasNext());
				
			} catch (Exception e) {
				logger.error("Erreur de génération du fichier " + e);
				failureFileWriter.append("Erreur de génération du fichier \n " + e);
				try {
					if (!successIds.isEmpty()) {
						confirmWriteSapAccounting(origin);
					}
					
				} catch (Exception e1) {
					throw e1;
				}
				throw e;
			} finally {
				successFileWriter.close();
				failureFileWriter.close();
			}
		}
		
		
		return RepeatStatus.FINISHED;
	}

	@Scheduled(cron = "${extractSapAccountingCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of sap accounting");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters).addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * extract the sap accounting from database
	 * @param lastId 
	 * 
	 * @return
	 */
	private PageResult<SapAccountingDTO> extractSapAccountings(String origin, int page, Long lastId) {
		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));
		params.add("lastId", String.valueOf(lastId));
		
		ParameterizedTypeReference<PageResult<SapAccountingDTO>> typeRef = new ParameterizedTypeReference<PageResult<SapAccountingDTO>>() {};
		logger.info("Trying to get transactions for SAP ACCOUNTING from " + origin + ", WEBIA DB ...");
		ResponseEntity<PageResult<SapAccountingDTO>> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_SAP_ACCOUNTING + "/" + origin + "/pagination", params, PageResult.class, typeRef, keycloackUtils, logger);
		PageResult<SapAccountingDTO> sapAccList = response.getBody();
		logger.info("Successfully got transactions for SAP ACCOUNTING from " + origin + ", WEBIA DB  ");
		return sapAccList;
	}

	/**
	 * write Sap Accounting
	 * 
	 * @param Sap Accounting list
	 */
	private void writeSapAccounting(Collection<SapAccountingDTO> temp) {
		for (SapAccountingDTO sapAccounting : temp) {
			try {
				successFileWriter.append(sapAccounting.buildLineForFile() + "\r\n");
				successIds.add(sapAccounting.getIdSapAcc());
			} catch (Exception e) {
				logger.error("Error during expot line", e);
				throw e;
			}
		}
	}

	public void confirmWriteSapAccounting(String origin) {
		logger.info("Trying to post success ids of SAP ACCOUNTING into " + origin + ", WEBIA DB ...");
		ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<Boolean>() {};
		RestCallUtils.postRest(getPiaRootContextURL() + EXTRACT_SAP_ACCOUNTING_CONFIRM, successIds, Collection.class, typeRef, keycloackUtils, logger);
		logger.info("Sucessfully posted success ids of SAP ACCOUNTING into " + origin + ", WEBIA DB");
		
		successIds.clear();
	}

	/**
	 * init success and error writer
	 * 
	 * @param fileName
	 */
	private void initFiles(String origin) {
		File successFile = new File(sapAccountingInjectionSuccessFile, origin.substring(0, 1)+ getCurrentDate() + ".txt");
		successFilePath = successFile.getAbsolutePath();
		successFileWriter = new CsvFileWriter(successFilePath);

		File failureFile = new File(sapAccountingInjectionErrorFile, origin.substring(0, 1) + getCurrentDate() + ".txt");
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);

	}

	/**
	 * get the current date
	 * 
	 * @return
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * @return the piaRootContextURL
	 */
	public String getPiaRootContextURL() {
		return piaRootContextURL;
	}

	/**
	 * @param piaRootContextURL the piaRootContextURL to set
	 */
	public void setPiaRootContextURL(String piaRootContextURL) {
		this.piaRootContextURL = piaRootContextURL;
	}

	/**
	 * @return the sapAccountingInjectionSuccessFile
	 */
	public String getSapAccountingInjectionSuccessFile() {
		return sapAccountingInjectionSuccessFile;
	}

	/**
	 * @param sapAccountingInjectionSuccessFile the sapAccountingInjectionSuccessFile to set
	 */
	public void setSapAccountingInjectionSuccessFile(String sapAccountingInjectionSuccessFile) {
		this.sapAccountingInjectionSuccessFile = sapAccountingInjectionSuccessFile;
	}

	/**
	 * @return the sapAccountingInjectionErrorFile
	 */
	public String getSapAccountingInjectionErrorFile() {
		return sapAccountingInjectionErrorFile;
	}

	/**
	 * @param sapAccountingInjectionErrorFile the sapAccountingInjectionErrorFile to set
	 */
	public void setSapAccountingInjectionErrorFile(String sapAccountingInjectionErrorFile) {
		this.sapAccountingInjectionErrorFile = sapAccountingInjectionErrorFile;
	}

}
