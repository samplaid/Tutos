package lu.wealins.batch.extract.encashmentSapAccounting;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.utils.KeycloakUtils;

public abstract class AbstractExtractEncashmentForSAPAccountingTaskLet implements Tasklet {
	final static int LIMIT_BATCH = 1000;
	final static int PAGE_SIZE = 500;

	private static final String STATE_COMPLETED = "COMPLETED";
	private static final String STATE_READY_FOR_UPDATE = "READY_FOR_UPDATE";
	private static final String STATE_PROCESS = "PROCESS";
	private static final String STATE_READY = "READY";

	private static String mode = "T";
	private static Date date = new Date();

	Log logger = LogFactory.getLog(AbstractExtractEncashmentForSAPAccountingTaskLet.class);

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

	public static void initJobParameters(JobParameters jobParameters) {

	}

}
