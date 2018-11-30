package lu.wealins.batch.extract.mathematicalReserve;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
 * @author xqt5q
 *
 */
@EnableScheduling
public class ExtractLISSIAPoliciesForMathematicalReserveJobLauncher {

	Log logger = LogFactory.getLog(ExtractLISSIAPoliciesForMathematicalReserveJobLauncher.class);

	@Autowired
	private JobService jobService;

	private JobExecution activeExecution;

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_JOB = "extractLISSIAPoliciesForMathematicalReserveJob";

	@Scheduled(cron = "${extractLISSIAMathematicalReserveCronExpression:}")
	private void scheduleExecute() throws Exception {
		logger.info("Trying to execute the LISSIA mathematical reserve job");
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String dateTimeString = dateTime.format(formatter);
		// add parameters
		Date date = generateDateForMathematicalReserve();
		logger.info("Calcul date generated : " + date);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateString = format.format(date);
		String mode = "T";
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(EXTRACT_JOB + ", mode=" + mode + ", date=" + dateString + ", executionDate=" + dateTimeString);
		logger.info("Calling job service for launching " + EXTRACT_JOB);
		JobExecution jobExecution = jobService.launch(EXTRACT_JOB,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
		if (jobExecution.isRunning())
			logger.info("Service has launched " + EXTRACT_JOB);
	}

	private Date generateDateForMathematicalReserve() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

}
