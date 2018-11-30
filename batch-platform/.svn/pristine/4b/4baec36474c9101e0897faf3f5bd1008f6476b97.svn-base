package lu.wealins.batch.extract.reportingcom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;

import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;

/**
 * 
 * @author xqv95
 *
 */
@EnableScheduling
public class ExtractBILCommissionToReportComJobLauncher implements JobExecutionListener {

	Log logger = LogFactory.getLog(ExtractBILCommissionToReportComJobLauncher.class);

	@Autowired
	private JobService jobService;
	
	private JobExecution activeExecution;

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "extractBILCommissionToReportComJob";

	//@Scheduled(cron = "${extractBILCommissionToReportComCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the extraction BIL commission to report com");
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String dateTimeString = dateTime.format(formatter);
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(EXTRACT_JOB + ", executionDate=" + dateTimeString);
		logger.info("Calling job service for launching " + EXTRACT_JOB);
		JobExecution jobExecution = jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
		if (jobExecution.isRunning())
			logger.info("Service has launched " + EXTRACT_JOB);
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	        if(activeExecution != null && activeExecution.isRunning()) {
	            jobExecution.stop();
	            //jobExecution.setExitStatus(new ExitStatus(jobExecution.getExitStatus().getExitCode(), "A job has already started. Please wait until the end of the current job before start a new jobs."));
	        } else {
	            activeExecution = jobExecution;
	            // reset
	            AbstractExtractBILCommissionToReportComTaskLet.reset();
	            AbstractExtractBILCommissionToReportComTaskLet.initJobParameters(jobExecution.getJobParameters());
	        }
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		synchronized(jobExecution) {
	          if(jobExecution == activeExecution) {
	              activeExecution = null; 
	          }
	      }
	}

}
