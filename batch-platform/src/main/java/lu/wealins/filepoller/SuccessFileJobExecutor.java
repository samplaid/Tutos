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
public class SuccessFileJobExecutor implements InitializingBean {

	private static final String CSV_EXTENSION = ".csv";

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobRegistry jobRegistry;

	private String successOutputDirectory;

	private String jobName;

	private Log logger = LogFactory.getLog(SuccessFileJobExecutor.class);

	public File launchJob(File input) throws JobExecutionException {

		if (input == null) {
			throw new IllegalArgumentException("File cannot be null.");
		}

		String[] fileNameNoExtension = input.getName().split("\\.");

		File succesFile = new File(successOutputDirectory + File.separator, fileNameNoExtension[0] + CSV_EXTENSION);

		if (succesFile.exists()) {
			logger.info("Launch job " + succesFile.getAbsolutePath());

			Job job = jobRegistry.getJob(jobName);

			if (job == null) {
				throw new IllegalStateException("Job " + jobName + " cannot be retrieve from the spring context.");
			}

			jobLauncher.run(job,
					new JobParametersBuilder().addDate("executionDate", new Date())
							.addString("path",
									succesFile.getAbsolutePath())
							.toJobParameters());

		} else {
			logger.info("Job is not started because no success file is generated in : " + successOutputDirectory);
		}

		return succesFile;
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

	/**
	 * @param successOutputDirectory
	 *            the successOutputDirectory to set
	 */
	public void setSuccessOutputDirectory(String successOutputDirectory) {
		this.successOutputDirectory = successOutputDirectory;
	}

}
