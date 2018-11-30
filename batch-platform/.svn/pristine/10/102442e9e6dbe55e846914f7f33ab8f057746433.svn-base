/**
 * 
 */
package lu.wealins.batch.procedureexecutor;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.domain.support.JobParametersExtractor;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.EnableScheduling;

import lu.wealins.service.BatchUtilityService;
import lu.wealins.utils.JobConstantsUtils;
import lu.wealins.utils.JobExecutionMode;

/**
 * @author xqv66
 *
 */
@EnableScheduling
public class ProcedureExecutorTaskLet implements Tasklet {

	/**
	 * The logger
	 */
	private Log logger = LogFactory.getLog(ProcedureExecutorTaskLet.class);

	private static final JobParametersExtractor JOB_PARAMETERS_EXTRACTOR = new JobParametersExtractor();

	@Autowired
	private BatchUtilityService batchUtilityService;

	@Autowired
	private JobService jobService;

	private SimpleJdbcCall simpleJdbcCall;

	private String procedureName;
	private String schemaName;
	private String parameters;
	private String jobName;
	private String result;

	@PostConstruct
	public void postConstruct() {
		this.simpleJdbcCall = simpleJdbcCall.withProcedureName(getProcedureName()).withSchemaName(getSchemaName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org. springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		logger.info("Trying to execute the stored procedure " + procedureName);

		Map<String, Object> jobParameters = batchUtilityService.getJobParameters(chunkContext);

		MapSqlParameterSource params = new MapSqlParameterSource(jobParameters);

		Map<String, Object> execute = simpleJdbcCall.execute(params);

		if (MapUtils.isNotEmpty(execute)) {
			if (execute.containsKey(result)) {
				if (result.equals("0")) {
					logger.info(procedureName + " is succeed");
				} else {
					logger.info(procedureName + " is failed");
				}
			} else {
				logger.info(procedureName + " is failed");
			}
		} else {
			logger.info(procedureName + " is failed");
		}

		return RepeatStatus.FINISHED;
	}

	public void scheduleExecute() throws Exception {
		logger.info("Trying to execute the stored procedure scheduled " + procedureName);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JobParameters jobParameters = JOB_PARAMETERS_EXTRACTOR.fromString(parameters + "," + InetAddress.getLocalHost().getHostAddress() + " :  executionTime=" + timeStamp);

		jobService.launch(jobName,
				new JobParametersBuilder(jobParameters)
						.addString(JobConstantsUtils.MODE, JobExecutionMode.AUTOMATIC.name()).toJobParameters());
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public SimpleJdbcCall getSimpleJdbcCall() {
		return simpleJdbcCall;
	}

	public void setSimpleJdbcCall(SimpleJdbcCall simpleJdbcCall) {
		this.simpleJdbcCall = simpleJdbcCall;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * @return the parameters
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
}
