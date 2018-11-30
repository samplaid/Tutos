package lu.wealins.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.utils.exceptions.JobPlannerException;

/**
 * JobPlannerUtils 
 * 
 * Can launch job with parameter. Job is running with synchronous task executor.
 * 
 * @author pur
 *
 */
@Component
public class JobPlannerUtils {

	Log logger = LogFactory.getLog(JobPlannerUtils.class);
	
    @Autowired
    private SimpleJobLauncher simpleJobLauncher;
    @Autowired
    private JobRegistry jobRegistry;
    

	/**
	 * Run job synchronous with parameters.
	 * 
	 * @param jobName Job name to execute
	 * @param jobParameters Job parameters 
	 * @return JobExecution
	 * @throws Exception
	 */
	public JobExecution runJobSynchronousWithParameters(String jobName, JobParameters jobParameters) throws JobPlannerException {
		logger.info("JobPlannerUtils trying to execute " + jobName + " with parameter");
		Assert.notNull(jobName);
		Assert.notNull(jobParameters);
		JobExecution jobExecution = simpleJobLauncherSynchronous(jobName, jobParameters);
		
		if (jobExecution.isRunning()) {
			// Throw exception if job is running.
			throw new JobPlannerException("Error job execution. The current job running : " + jobName + " is not finished.");
		}
		return jobExecution;
	}
	
	/**
	 * Simple job launcher synchronous.
	 * 
	 * @param jobName
	 * @param jobParameters
	 * @return JobExecution
	 */
	private JobExecution simpleJobLauncherSynchronous(String jobName, JobParameters jobParameters) {
		JobExecution jobExecution = null;
		try {
			logger.info("Calling job registry for launching " + jobName);
			Job job = jobRegistry.getJob(jobName);
			SyncTaskExecutor taskExecutorSync = new SyncTaskExecutor();
			simpleJobLauncher.setTaskExecutor(taskExecutorSync);
			jobExecution = simpleJobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.error("Error durring launch job : " + jobName, e);
		}		
		if (jobExecution.getStatus() != null)
			logger.info("Service has launched " + jobName);
		return jobExecution;
	}

}
