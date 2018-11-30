package lu.wealins.batch.extract.sapaccounting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.utils.KeycloakUtils;

public abstract class AbstractExtractLissiaSapAccountingTaskLet implements Tasklet {
	final static int LIMIT_BATCH = 1000;
	final static int PAGE_SIZE = 3000;

	private static final String STATE_COMPLETED = "COMPLETED";
	private static final String STATE_READY_FOR_UPDATE = "READY_FOR_UPDATE";
	private static final String STATE_PROCESS = "PROCESS";
	private static final String STATE_READY = "READY";
	
	private static volatile Exception jobErrorExecution = null;
	static volatile PageResult<TransactionDTO> lissiaTransaction = null;
	static volatile Collection<PstIdWrapper> pstIdWrapperList = null;
	static volatile List<Long> idsForRollback = new ArrayList<Long>();
	static volatile HashMap<Long, String> pstAvailable = null;
	Log logger = LogFactory.getLog(AbstractExtractLissiaSapAccountingTaskLet.class);
	
	private static volatile JobExecution jobExecutionAbstract = null;

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

	// Initialize pst available
	public void initPstAvailable(List<Long> postsingSetAvalaible) {
		pstAvailable = new HashMap<Long, String>();
		postsingSetAvalaible.forEach(pst -> {
			pstAvailable.put(pst, STATE_READY);
		});
	}

	// Get the next or current pst available
	public Long getNextPstAvailableReady() {
		for (Long i : pstAvailable.keySet()) {
			if (pstAvailable.get(i).equals(STATE_PROCESS)) {
				return i;
			}
		}

		for (Long i : pstAvailable.keySet()) {
			if (pstAvailable.get(i).equals(STATE_READY)) {
				setPstAvailableProcess(i);
				return i;
			}
		}
		return 0L;
	}

	// Get list of pst available for update sap status
	public List<Long> getPstAvailableReadyForUpdate() {
		List<Long> pstAvailableReadyForUpdate = new ArrayList<Long>();
		for (Long i : pstAvailable.keySet()) {
			if (pstAvailable.get(i).equals(STATE_READY_FOR_UPDATE)) {
				pstAvailableReadyForUpdate.add(i);
			}
		}
		return pstAvailableReadyForUpdate;
	}

	private Boolean setPstAvailableProcess(Long i) {
		if (pstAvailable.get(i).equals(STATE_READY)) {
			pstAvailable.put(i, STATE_PROCESS);
			return true;
		}
		return false;
	}

	public Boolean setPstAvailableReadyForUpdate(Long i) {
		if (pstAvailable.get(i).equals(STATE_PROCESS)) {
			pstAvailable.put(i, STATE_READY_FOR_UPDATE);
			return true;
		}
		return false;
	}

	public void setPstAvailableCompleted(List<Long> listIds) {
		for (Long i : listIds) {
			if (pstAvailable.get(i).equals(STATE_READY_FOR_UPDATE)) {
				pstAvailable.put(i, STATE_COMPLETED);
				idsForRollback.clear();
			}
		}
	}

	// Get the status of current context pst
	public Boolean pstAvailableIsAllCompleted() {
		for (Long i : pstAvailable.keySet()) {
			if (!pstAvailable.get(i).equals(STATE_COMPLETED)) {
				return false;
			}
		}
		return true;
	}

	public int countPstAvailableCompleted() {
		int k = 0;
		for (Long i : pstAvailable.keySet()) {
			if (pstAvailable.get(i).equals(STATE_COMPLETED)) {
				k++;
			}
		}
		return k;
	}

	public static void reset() {
		lissiaTransaction = null;
		pstIdWrapperList = null;
		pstAvailable = null;
		idsForRollback.clear();
		jobErrorExecution = null;
		jobExecutionAbstract = null;
	}
	
	public static void setJobErrorExecution(Exception e) {
		jobErrorExecution = e;
	}
	
	public static Exception getJobErrorExecution() {
		return jobErrorExecution;
	}
	
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
}
