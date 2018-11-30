package lu.wealins.batch.extract.sapaccounting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;

/**
 * 
 * @author xqv99
 *
 */
@EnableScheduling
public class ExtractLissiaSapAccountingJobLauncher {

	Log logger = LogFactory.getLog(ExtractLissiaSapAccountingJobLauncher.class);

	@Autowired
	private JobService jobService;

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_LISSIA_INTO_SAP_ACCOUNTING_JOB = "extractLissiaIntoSapAccountingJob";

	@Scheduled(cron = "${extractLissiaSapAccountingCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction of Lissia transactions into sap accounting");
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String dateTimeString = dateTime.format(formatter);
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(EXTRACT_LISSIA_INTO_SAP_ACCOUNTING_JOB + ", executionDate=" + dateTimeString);
		logger.info("Calling job service for launching " + EXTRACT_LISSIA_INTO_SAP_ACCOUNTING_JOB);
		
		JobExecution jobExecution = jobService.launch(EXTRACT_LISSIA_INTO_SAP_ACCOUNTING_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());

		if (jobExecution.isRunning())
			logger.info("Service has launched " + EXTRACT_LISSIA_INTO_SAP_ACCOUNTING_JOB);
	}
}
