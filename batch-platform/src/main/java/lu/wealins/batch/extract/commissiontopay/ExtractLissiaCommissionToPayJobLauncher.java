package lu.wealins.batch.extract.commissiontopay;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;
import lu.wealins.utils.JobPlannerUtils;
import lu.wealins.utils.exceptions.JobPlannerException;

/**
 * 
 * @author pur
 *
 */
@EnableScheduling
public class ExtractLissiaCommissionToPayJobLauncher {

	Log logger = LogFactory.getLog(ExtractLissiaCommissionToPayJobLauncher.class);
    @Autowired
    private JobPlannerUtils jobPlannerUtils;
    //@Value("${jobPlannerCommissionsWorkingDay}")
	int scheduledWorkingDayProperties = 0;
	
	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();
	private static final String EXTRACT_COMMISSION_TO_PAY_JOB = "extractLissiaCommissionToPayJob";
	private static final String GENERATE_STATEMENT_COM_JOB = "generateStatementJob";
	
	/**
	 * Job planner commissions to pay
	 * STEP 1 : Control the current day. Decides if scheduledWorkingDay is same that today 
	 * STEP 2 : Execute extractLissiaCommissionToPayJob with parameter
	 * STEP 3 : Execute generate statement commissions with parameter
	 * @throws Exception
	 */
	@Scheduled(cron = "${jobPlannerCommissionsCronExpression:}")
	private void jobPlannerCommissionsExecute() throws Exception {
		// Remove : Control the current day. Decides if scheduledWorkingDay is same that today. 
		// Now : Just execute jobs at the schedule date
		//if (step1()) {
			JobExecution jobExecutionStep2 = step2();
			if (jobExecutionStep2 != null && jobExecutionStep2.getStatus().equals(BatchStatus.COMPLETED)) {
				step3();
			}
		//}			
	}
		
	/**
	 * Return true if today match with scheduledWorkingDay selected
	 * 
	 * @return
	 */
	private boolean step1() {
		Calendar cal = Calendar.getInstance();
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		int workingDay = 0;
		int scheduledWorkingDay = scheduledWorkingDayProperties;
		
		cal.set(Calendar.DAY_OF_MONTH, workingDay);
		while (scheduledWorkingDay > 0) {
			workingDay = workingDay + 1;
			cal.set(Calendar.DAY_OF_MONTH, workingDay);
			
			if (isWorkingDay(cal)) {
				scheduledWorkingDay = scheduledWorkingDay - 1;
			}			
		}
		
		if (currentDay == workingDay){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verify if current day is working day 
	 * 
	 * @param cal
	 * @return
	 */
	private boolean isWorkingDay(Calendar cal) {
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) 
			return false;
		else 
			return true;
	}
	
	/**
	 * Execute extractLissiaCommissionToPayJob with parameter
	 * 
	 * @return BatchStatus
	 */
	private JobExecution step2() {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String dateTimeString = dateTime.format(formatter);
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString("executionDate=" + dateTimeString);
		
		JobExecution jobExecution = null;
		try {
			jobExecution = jobPlannerUtils.runJobSynchronousWithParameters(EXTRACT_COMMISSION_TO_PAY_JOB,
					new JobParametersBuilder(jobParameters)
							.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
		} catch (JobPlannerException e) {
			logger.error("Error durring planner job ", e);
		}
		
		return jobExecution;
	}
	
	/**
	 * Execute generate statement commissions
	 * 
	 * @return BatchStatus
	 */
	private JobExecution step3() {
		Calendar calNow = Calendar.getInstance();
		String dateTimeString = calNow.getTime().toString();
		calNow.add(Calendar.MONTH, -1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString("period="+dateFormat.format(calNow.getTime())+",type=ENTRY,executionDate=" + dateTimeString);
		
		JobExecution jobExecution = null;
		try {
			jobExecution = jobPlannerUtils.runJobSynchronousWithParameters(GENERATE_STATEMENT_COM_JOB,
					new JobParametersBuilder(jobParameters)
							.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());

		} catch (JobPlannerException e) {
			logger.error("Error durring planner job ", e);
		}
		
		return jobExecution;
	}
}