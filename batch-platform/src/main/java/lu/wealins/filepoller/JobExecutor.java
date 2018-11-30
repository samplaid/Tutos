/**
 * 
 */
package lu.wealins.filepoller;

import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xqv66
 *
 */
public class JobExecutor implements InitializingBean {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobRegistry jobRegistry;

	private String outputDirectory;

	private String jobName;

	private Log logger = LogFactory.getLog(JobExecutor.class);

	public File launchJob(File input) throws JobExecutionException {

		if (input == null) {
			throw new IllegalArgumentException("File cannot be null.");
		}

		logger.info("Launch job " + input.getAbsolutePath());

		Job job = jobRegistry.getJob(jobName);

		if (job == null) {
			throw new IllegalStateException("Job " + jobName + " cannot be retrieve from the spring context.");
		}

		jobLauncher.run(job,
				new JobParametersBuilder().addDate("executionDate", new Date()).addString("path", outputDirectory + File.separator + input.getName())
						.toJobParameters());

		return input;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jobLauncher == null) {
			throw new IllegalStateException("Job launcher cannot be null.");
		}
		if (jobRegistry == null) {
			throw new IllegalStateException("Job registry cannot be null.");
		}
		if (jobName == null) {
			throw new IllegalStateException("Job name cannot be null.");
		}
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

}
