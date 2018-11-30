package lu.wealins.batch.extract.reportingcom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.TransactionDTO;
import lu.wealins.utils.KeycloakUtils;

public abstract class AbstractExtractBILCommissionToReportComTaskLet implements Tasklet {
	final static int LIMIT_BATCH = 1000;
	final static int PAGE_SIZE = 6000;
	
	private static final String STATE_COMPLETED = "COMPLETED";
	private static final String STATE_READY_FOR_UPDATE = "READY_FOR_UPDATE";
	private static final String STATE_PROCESS = "PROCESS";
	private static final String STATE_READY = "READY";
	
	private static final String MATCH_REPORT_ID = "reportId";
	private static final String MATCH_DATE_COM = "date";
	private static Long reportId = 0L;
	private static Date comDate = null;
	
	static volatile PageResult<TransactionDTO> lissiaTransaction = null;
	static volatile List<Long> idsForRollback = new ArrayList<Long>();
	static volatile HashMap<Long, String> pstAvailable = null;
	Log logger = LogFactory.getLog(AbstractExtractBILCommissionToReportComTaskLet.class);
	
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
	
	public static void reset()
	{
		lissiaTransaction = null;
		pstAvailable = null;
		reportId = null;
		comDate = null;
		idsForRollback.clear();
	}
	
	public static void initJobParameters(JobParameters jobParameters) {
		Map<String, JobParameter> params = jobParameters.getParameters();
		
		if (params.containsKey(MATCH_REPORT_ID)) {
			JobParameter paramReportId =  params.get(MATCH_REPORT_ID);
			setReportId(Long.valueOf(paramReportId.getValue().toString()));
		} else {
			throw new IllegalArgumentException("Invalid missing report id");
		}
		
		if (params.containsKey(MATCH_DATE_COM)) {
			JobParameter paramDateCom =  params.get(MATCH_DATE_COM);
			
			Date dateConvert = null;
			
			try {
				dateConvert = convertStringDate(paramDateCom.getValue().toString());
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid date : " + e);
			}
			
			
			if (dateConvert != null) {
				setComDate(dateConvert);
			} else {
				throw new IllegalArgumentException("Invalid date");
			}
			
		} else {
			throw new IllegalArgumentException("Invalid missing date");
		}
	}

	/**
	 * Convert string date DD/MM/AAAA to Date
	 * @param string
	 * @throws ParseException 
	 */
	private static Date convertStringDate(String string) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = format.parse(string);
		return date;
	}

	/**
	 * @return the reportId
	 */
	public static Long getReportId() {
		return reportId;
	}

	/**
	 * @param id the reportId to set
	 */
	public static void setReportId(Long id) {
		reportId = id;
	}

	/**
	 * @return the comDate
	 */
	public static Date getComDate() {
		return comDate;
	}

	/**
	 * @param date the comDate to set
	 */
	public static void setComDate(Date date) {
		comDate = date;
	}
	
	
}
