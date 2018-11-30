/**
 * 
 */
package lu.wealins.batch.extract;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.ReportingComDTO;
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
public class ExtractReportingComTaskLet implements JobExecutionListener, Tasklet {

	private static final String SUPPORT_CODE = "355070000000";

	private static final String BIL_CODE = "BIL";
	
	private static final String MATCH_REPORT_ID = "reportId";
	private static Long reportId = 0L;


	private Log logger = LogFactory.getLog(ExtractReportingComTaskLet.class);
	private final static int PAGE_SIZE = 70000;

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;
	@Value("${extractReportingComSuccessFile}")
	private String reportingComSuccessFile;
	@Value("${extractReportingComErrorFile}")
	private String reportingComErrorFile;

	@Autowired
	KeycloakUtils keycloackUtils;

	@Autowired
	private JobService jobService;
	
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "extractReportingComJob";
	private static final String EXTRACT_REPORTING_COM = "webia/extract/reporting-com";
	private static final String EXTRACT_REPORTING_COM_CONFIRM = "webia/extract/reporting-com/confirm";

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
		// extract reporting com from webia
		PageResult<ReportingComDTO> reportingComs = null;
		int page = 0;
		Long lastId = 0L;
		try {
			initFiles();
			
			do {			
				reportingComs = extractReportingComs(page, lastId);

				if (reportingComs != null) {
					// get last id
					Optional<ReportingComDTO> result = reportingComs.getContent().stream().max((x, y) -> Long.compare(x.getReportComId(), y.getReportComId()));
					if (result.isPresent()) {
						lastId = result.get().getReportComId();
					}
					
					logger.debug("lastId " + lastId);
					writeReportingCom(reportingComs.getContent());
					if (!successIds.isEmpty()) {
						confirmWriteReportingCom();
					}
				}
			} while (reportingComs != null && reportingComs.hasNext());
			
		} catch (Exception e) {
			logger.error("Erreur de génération du fichier " + e);
			failureFileWriter.append("Erreur de génération du fichier \n " + e);
			try {
				if (!successIds.isEmpty()) {
					confirmWriteReportingCom();
				}
				
			} catch (Exception e1) {
				throw e1;
			}
			throw e;
		} finally {
			successFileWriter.close();
			failureFileWriter.close();
		}
		
		return RepeatStatus.FINISHED;
	}

	//@Scheduled(cron = "${extractReportingComCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of reporting com");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(InetAddress.getLocalHost().getHostAddress() + " : executionTime=" + timeStamp);

		jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters).addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	/**
	 * extract the reporting com from database
	 * 
	 */
	private PageResult<ReportingComDTO> extractReportingComs(int page, Long lastId) {
		// get URI and query parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("page", String.valueOf(page));
		params.add("size", String.valueOf(PAGE_SIZE));
		params.add("lastId", String.valueOf(lastId));
		params.add("reportId", String.valueOf(getReportId()));
		
		
		ParameterizedTypeReference<PageResult<ReportingComDTO>> typeRef = new ParameterizedTypeReference<PageResult<ReportingComDTO>>() {};
		logger.info("Trying to get reporting com from WEBIA DB ...");
		ResponseEntity<PageResult<ReportingComDTO>> response = RestCallUtils.get(getPiaRootContextURL() + EXTRACT_REPORTING_COM + "/pagination", params, PageResult.class, typeRef, keycloackUtils, logger);
		PageResult<ReportingComDTO> reportComList = response.getBody();
		logger.info("Successfully got reporting com  from WEBIA DB ");
		return reportComList;
	}

	/**
	 * write Reporting Com
	 * 
	 * @param Reporting Com list
	 */
	private void writeReportingCom(Collection<ReportingComDTO> temp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();	
		for (ReportingComDTO reportingCom : temp) {
			try {

				BigDecimal baseAmt = null;
				if (reportingCom.getBaseAmt() != null && reportingCom.getBaseAmt().signum() == -1) {
					baseAmt = reportingCom.getBaseAmt().negate();
				} else if (reportingCom.getBaseAmt() != null) {
					baseAmt = reportingCom.getBaseAmt();
				}
				
				StringBuilder sb = new StringBuilder();
				
				// Contrat de base 8 chars
				sb.append(StringUtils.defaultString(
						StringUtils.rightPad(reportingCom.getBrokerPolicy() == null ? StringUtils.EMPTY : reportingCom.getBrokerPolicy().trim(), 8, ' '), 
						StringUtils.repeat(" ", 10)));
				
				// Empty 10 chars
				sb.append(StringUtils.defaultString(
						StringUtils.rightPad(StringUtils.EMPTY, 9, ' '), 
						StringUtils.repeat(" ", 9)));
				
				// Produit d'apporteur
				sb.append(StringUtils.defaultString(
						StringUtils.rightPad(reportingCom.getProductCd() == null ? StringUtils.EMPTY : reportingCom.getProductCd(), 3, ' '),
						StringUtils.repeat(" ", 3)));
				
				// Fond type
				sb.append(StringUtils.defaultString(
						StringUtils.rightPad(BIL_CODE, 3, ' '), 
						StringUtils.repeat(" ", 3)));
				
				sb.append(" ");
						
				// Support
				sb.append(StringUtils.defaultIfBlank(
						SUPPORT_CODE,
						StringUtils.repeat(" ", 12)));
				
				// Code d'événement
				sb.append(StringUtils.defaultString(
						StringUtils.leftPad(
							StringUtils.deleteWhitespace(reportingCom.getMvtCd() == null ? StringUtils.EMPTY : reportingCom.getMvtCd()), 3, '0'),
						StringUtils.repeat(" ", 3)));
				
				// Date valueur
				sb.append(sdf.format(reportingCom.getComDt() == null ? StringUtils.EMPTY : reportingCom.getComDt()));
				
				// Devise
				sb.append(reportingCom.getCurrency() == null ? StringUtils.EMPTY : reportingCom.getCurrency());
				
				// Montant provision
				sb.append(StringUtils.remove(
						String.format(Locale.US, "%012.2f",
								baseAmt == null ? StringUtils.EMPTY : baseAmt), '.'));
				
				// Direction du mouvement
				sb.append(reportingCom.getSignbase() == null ? StringUtils.EMPTY : reportingCom.getSignbase().toString());
				
				// Montant commission
				sb.append(StringUtils.remove(
						String.format(Locale.US, "%012.2f",
								reportingCom.getComAmt() == null ? StringUtils.EMPTY : reportingCom.getComAmt()), '.'));
				
				// Direction de la commission
				sb.append(reportingCom.getSigncom() == null ? StringUtils.EMPTY : reportingCom.getSigncom().toString());
				
				// Date d'envoi
				sb.append(sdf.format(date));

				// CRLF
				sb.append("\r\n");
				successFileWriter.append(sb.toString());

				successIds.add(reportingCom.getReportComId());
			} catch (Exception e) {
				logger.error("Error during expot line", e);
				throw e;
			}
		}
	}

	/**
	 * post method
	 * 
	 */
	public void confirmWriteReportingCom() {
		logger.info("Trying to post success ids of REPORTING COM into Webia DB ...");
		ParameterizedTypeReference<Boolean> typeRef = new ParameterizedTypeReference<Boolean>() {};
		RestCallUtils.postRest(getPiaRootContextURL() + EXTRACT_REPORTING_COM_CONFIRM, successIds, Collection.class,typeRef, keycloackUtils, logger);
		logger.info("Sucessfully posted success ids of REPORTING COM into Webia DB");
		successIds.clear();
	}

	/**
	 * init success and error writer
	 * 
	 */
	private void initFiles() {
		File successFile = new File(reportingComSuccessFile, "DLPACOMTXT." + getCurrentDate() + "." + getReportId());
		successFilePath = successFile.getAbsolutePath();
		successFileWriter = new CsvFileWriter(successFilePath);

		File failureFile = new File(reportingComErrorFile, "DLPACOMTXT.error." + getCurrentDate() + "." + getReportId());
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);
	}

	/**
	 * get the current date
	 * 
	 */
	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
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
	 * @return the reportingComSuccessFile
	 */
	public String getReportingComSuccessFile() {
		return reportingComSuccessFile;
	}

	/**
	 * @param reportingComSuccessFile the reportingComSuccessFile to set
	 */
	public void setReportingComSuccessFile(String reportingComSuccessFile) {
		this.reportingComSuccessFile = reportingComSuccessFile;
	}

	/**
	 * @return the reportingComErrorFile
	 */
	public String getReportingComErrorFile() {
		return reportingComErrorFile;
	}

	/**
	 * @param reportingComErrorFile the reportingComErrorFile to set
	 */
	public void setReportingComErrorFile(String reportingComErrorFile) {
		this.reportingComErrorFile = reportingComErrorFile;
	}
	
	
	public static void initJobParameters(JobParameters jobParameters) {
		Map<String, JobParameter> params = jobParameters.getParameters();
		
		if (params.containsKey(MATCH_REPORT_ID)) {
			JobParameter paramReportId =  params.get(MATCH_REPORT_ID);
			setReportId(Long.valueOf(paramReportId.getValue().toString()));
		} else {
			throw new IllegalArgumentException("Invalid missing report id");
		}
	}

	/**
	 * @return the reportId
	 */
	public static Long getReportId() {
		return reportId;
	}

	/**
	 * @param id the reportId to set
	 */
	public static void setReportId(Long id) {
		reportId = id;
	}
	
	/**
	 * Reset report id param
	 */
	public static void reset() {
		reportId = null;
	}
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
			reset();
			initJobParameters(jobExecution.getJobParameters());
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		
	}

}
