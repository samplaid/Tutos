package lu.wealins.batch.injection.drm;

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

@EnableScheduling
public class InjectLissiaAgentIdsForDRMLauncher {
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String INJECT_JOB = "injectLissiaAgentIdsForDRM";
	private Log logger = LogFactory.getLog(InjectLissiaAgentIdsForDRMLauncher.class);
	
	@Autowired
	private JobService jobService;
	
	@Scheduled(cron = "${injectLissiaAgentIdsForDRMCron}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute Lissia agent ID injection for DRM job");
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String dateTimeString = dateTime.format(formatter);
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(INJECT_JOB + ", executionDate=" + dateTimeString);
		
		logger.info("Calling job service for launching " + INJECT_JOB);
		JobExecution jobExecution = jobService.launch(INJECT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name())
						.toJobParameters());
		
		if (jobExecution.isRunning())
			logger.info("Service has launched " + INJECT_JOB);
	}
}
