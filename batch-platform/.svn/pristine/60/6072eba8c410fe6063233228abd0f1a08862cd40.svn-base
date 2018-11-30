package lu.wealins.batch.simplerest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SimpleTaskScheduler implements InitializingBean {

	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private static final String EXECUTION_TIME = "executionTime";

	private static final String HOST_NAME = "hostName";

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobRegistry jobRegistry;

	private String jobName;

	private Map<String, String> stringParameters = new HashMap<>();
	private Map<String, Date> dateParameters = new HashMap<>();
	private Map<String, Long> longParameters = new HashMap<>();

	public void run() {

		try {

			if (getJobName() == null) {
				throw new IllegalStateException("Job name cannot be null.");
			}

			Job job = jobRegistry.getJob(getJobName());

			if (job == null) {
				throw new IllegalStateException("Job " + getJobName() + " cannot be retrieve from the spring context.");
			}

			jobLauncher.run(job, createJobParameters());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jobLauncher == null) {
			throw new IllegalStateException("Job launcher cannot be null.");
		}
		if (jobRegistry == null) {
			throw new IllegalStateException("Job registry cannot be null.");
		}
	}

	private JobParameters createJobParameters() throws UnknownHostException {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

		getStringParameters().forEach((k, v) -> jobParametersBuilder.addString(k, v));
		getDateParameters().forEach((k, v) -> jobParametersBuilder.addDate(k, v));
		getLongParameters().forEach((k, v) -> jobParametersBuilder.addLong(k, v));

		/*
		 * We add the hostname argument and the execution time argument in order to create a new job. Tips: A job must have unique arguments in order to execute it.
		 */
		String timeStamp = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(Calendar.getInstance().getTime());
		jobParametersBuilder.addString(EXECUTION_TIME, timeStamp);
		jobParametersBuilder.addString(HOST_NAME, InetAddress.getLocalHost().getHostAddress());

		return jobParametersBuilder.toJobParameters();
	}

	public Map<String, String> getStringParameters() {
		return stringParameters;
	}

	public void setStringParameters(Map<String, String> stringParameters) {
		this.stringParameters = stringParameters;
	}

	public Map<String, Date> getDateParameters() {
		return dateParameters;
	}

	public void setDateParameters(Map<String, Date> dateParameters) {
		this.dateParameters = dateParameters;
	}

	public Map<String, Long> getLongParameters() {
		return longParameters;
	}

	public void setLongParameters(Map<String, Long> longParameters) {
		this.longParameters = longParameters;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
