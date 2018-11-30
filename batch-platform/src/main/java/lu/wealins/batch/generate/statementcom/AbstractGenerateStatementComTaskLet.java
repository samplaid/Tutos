package lu.wealins.batch.generate.statementcom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import lu.wealins.utils.KeycloakUtils;

public abstract class AbstractGenerateStatementComTaskLet implements Tasklet {
	private static final String BROKER_BIL_CODE = "A04160";
	private static final String MATCH_BROKER = "broker";
	private static final String MATCH_PERIOD = "period";
	private static final String MATCH_TYPE = "type";
	private static final String MATCH_STATEMENT_ID = "statementId";
	private static final String BROKER_BIL = "BROKER_BIL";
	private static final String OPCVM_TYPE = "OPCVM";
	private static final String ADM_TYPE = "ADM";
	private static final String ENTRY_TYPE = "ENTRY";
	private static final String SURR_TYPE = "SURR";
	private static final String SWITCH_TYPE = "SWITCH";
		
	private static volatile List<String> ENTRY_TYPE_VALUE = Arrays.asList(ENTRY_TYPE);
	private static volatile List<String> ADM_TYPE_VALUE = Arrays.asList(ADM_TYPE, OPCVM_TYPE, SURR_TYPE, SWITCH_TYPE);
	private static volatile List<String> OPCVM_TYPE_VALUE = Arrays.asList(OPCVM_TYPE);
	
	private static volatile List<String> PARAM_JOB_TYPE_VALUE = new ArrayList<String>();
	private static volatile String PARAM_JOB_TYPE = null;
	private static volatile List<String> PARAM_JOB_PERIOD_VALUE = new ArrayList<String>();
	private static volatile String PARAM_JOB_BROKER = null;
	private static volatile String PARAM_JOB_PERIOD = null;
	private static volatile String PARAM_JOB_ID_STATEMENT = null;
	
	private static volatile Map<String, List<String>> COMMISSION_TYPE = new HashMap<String, List<String>>();

	private static volatile JobExecution jobExecutionAbstract = null;
	
	private static volatile Exception jobErrorExecution = null;

	Log logger = LogFactory.getLog(AbstractGenerateStatementComTaskLet.class);
	
	@Value("${piaRootContextURL}")
	String piaRootContextURL;
	
	@Autowired
	KeycloakUtils keycloackUtils;
	
	/**
	 * @return the piaRootContextURL
	 */
	public String getPiaRootContextURL() {
		return piaRootContextURL;
	}

	/**
	 * @param piaRootContextURL the piaRootContextURL to set
	 */
	public void setPiaRootContextURL(String piaRootContextURL) {
		this.piaRootContextURL = piaRootContextURL;
	}
	
	/**
	 * Init job with parameters send on spring batch UI
	 * 
	 * @param jobParameters
	 */
	public static void initJobParameters(JobParameters jobParameters) {
		Map<String, JobParameter> params = jobParameters.getParameters();
		
		// Init var statement ID
		if (params.containsKey(MATCH_STATEMENT_ID)) {
			JobParameter paramStatementId =  params.get(MATCH_STATEMENT_ID);
			PARAM_JOB_ID_STATEMENT = paramStatementId.getValue().toString();
		} else {
			// Init var type
			if (params.containsKey(MATCH_TYPE)) {
				JobParameter paramCommissionType = params.get(MATCH_TYPE);
				if (paramCommissionType.getValue() != null) {
					PARAM_JOB_TYPE = paramCommissionType.getValue().toString().toUpperCase().trim();
					if (COMMISSION_TYPE.containsKey(PARAM_JOB_TYPE)) {
						PARAM_JOB_TYPE_VALUE = COMMISSION_TYPE.get(PARAM_JOB_TYPE);
					}
				}
			} 
			
			if (CollectionUtils.isEmpty(PARAM_JOB_TYPE_VALUE)) {
				throw new IllegalArgumentException("Invalid commission type");
			}
			
			// Init var period
			if (params.containsKey(MATCH_PERIOD)) {
				JobParameter paramPeriod = params.get(MATCH_PERIOD);
				if (paramPeriod.getValue() != null) {
					PARAM_JOB_PERIOD = paramPeriod.getValue().toString().toUpperCase().trim();
					if (PARAM_JOB_PERIOD.length() == 6) {
						if (ADM_TYPE.equals(PARAM_JOB_TYPE) || OPCVM_TYPE.equals(PARAM_JOB_TYPE)) {
							if (PARAM_JOB_PERIOD.startsWith("Q1", 4)) {
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q1", "01"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q1", "02"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q1", "03"));
							}
							if (PARAM_JOB_PERIOD.startsWith("Q2", 4)) {
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q2", "04"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q2", "05"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q2", "06"));
							}	
							if (PARAM_JOB_PERIOD.startsWith("Q3", 4)) {
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q3", "07"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q3", "08"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q3", "09"));
							}
							if (PARAM_JOB_PERIOD.startsWith("Q4", 4)) {
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q4", "10"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q4", "11"));
								PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD.replace("Q4", "12"));
							}	
						}
						if (ENTRY_TYPE.equals(PARAM_JOB_TYPE)) {
							if (PARAM_JOB_PERIOD.matches("[0-9]+")) {
								String mounth = PARAM_JOB_PERIOD.substring(4, 6);
								if (Integer.valueOf(mounth) < 13) {
									PARAM_JOB_PERIOD_VALUE.add(PARAM_JOB_PERIOD);
								}
							}						
						}
					}
				}
			} 

			if (CollectionUtils.isEmpty(PARAM_JOB_PERIOD_VALUE)) {
				throw new IllegalArgumentException("Invalid period");
			}
			
			// Init var broker
			if (params.containsKey(MATCH_BROKER)) {
				JobParameter paramBroker = params.get(MATCH_BROKER);
				if (paramBroker.getValue() != null) {
					if (BROKER_BIL.equals(paramBroker.getValue().toString().trim())) {
						PARAM_JOB_BROKER = BROKER_BIL_CODE;
					} else {
						PARAM_JOB_BROKER = paramBroker.getValue().toString().trim();
					}
				}
			}
		}
	}
	
	/**
	 * Reset all variables
	 */
	public static void reset()
	{
		initCommissionType();
		PARAM_JOB_TYPE_VALUE = new ArrayList<String>();
		PARAM_JOB_PERIOD_VALUE = new ArrayList<String>();
		PARAM_JOB_BROKER = null;
		PARAM_JOB_TYPE = null;
		PARAM_JOB_PERIOD = null;
		jobExecutionAbstract = null;
		jobErrorExecution = null;
		PARAM_JOB_ID_STATEMENT = null;
	}
	
	/**
	 * Getter PARAM_JOB_TYPE
	 * @return
	 */
	public String getParamJobType() {
		return PARAM_JOB_TYPE;
	}

	/**
	 * Getter PARAM_JOB_TYPE_VALUE
	 * @return
	 */
	public List<String> getParamJobTypeValue() {
		return PARAM_JOB_TYPE_VALUE;
	}
	
	/**
	 * Getter PARAM_JOB_PERIOD_VALUE
	 * @return
	 */
	public List<String> getParamJobPeriodValue() {
		return PARAM_JOB_PERIOD_VALUE;
	}
	
	/**
	 * Getter PARAM_JOB_PERIOD
	 * @return
	 */
	public String getParamJobPeriod() {
		return PARAM_JOB_PERIOD;
	}

	/**
	 * Getter PARAM_JOB_BROKER
	 * @return
	 */
	public String getParamJobBroker() {
		return PARAM_JOB_BROKER;
	}
	
	/**
	 * Getter PARAM_JOB_ID_STATEMENT
	 * @return
	 */
	public String getParamJobStatementId() {
		return PARAM_JOB_ID_STATEMENT;
	}
	
	/**
	 * Update repository for throw error message
	 * @param jobRepository
	 */
	public static void throwBusinessExceptionJobError(JobRepository jobRepository) {
		jobExecutionAbstract.setEndTime(new Date());
		jobExecutionAbstract.upgradeStatus(BatchStatus.FAILED);
		jobExecutionAbstract.setExitStatus(new ExitStatus(ExitStatus.FAILED.getExitCode(), jobErrorExecution == null ? null : jobErrorExecution.getMessage()));
	    jobRepository.update(jobExecutionAbstract);
	}
			
	/**
	 * @return the jobExecutionAbstract
	 */
	public static JobExecution getJobExecutionAbstract() {
		return jobExecutionAbstract;
	}
	
	/**
	 * @param jobExecution the jobExecutionAbstract to set
	 */
	public static void setJobExecutionAbstract(JobExecution jobExecution) {
		jobExecutionAbstract = jobExecution;
	}
	
	/**
	 * Set jobErrorExecution
	 * @param e
	 */
	public static void setJobErrorExecution(Exception e) {
		jobErrorExecution = e;
	}
	
	/**
	 * Getter jobErrorExecution
	 */
	public static Exception getJobErrorExecution() {
		return jobErrorExecution;
	}
	
	/**
	 * Init commission type variable
	 */
	private static void initCommissionType() {
		COMMISSION_TYPE.clear();
		COMMISSION_TYPE.put(ENTRY_TYPE, ENTRY_TYPE_VALUE);
		COMMISSION_TYPE.put(ADM_TYPE, ADM_TYPE_VALUE);
		COMMISSION_TYPE.put(OPCVM_TYPE, OPCVM_TYPE_VALUE);
	}
	
}
