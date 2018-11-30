package lu.wealins.batch.extract.mathematicalReserve;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lu.wealins.utils.KeycloakUtils;

public abstract class AbstractExtractLISSIAPoliciesForMathematicalReserveTaskLet implements Tasklet {
	final static int LIMIT_BATCH = 1000;
	final static int PAGE_SIZE = 500;

	private static final String STATE_COMPLETED = "COMPLETED";
	private static final String STATE_READY_FOR_UPDATE = "READY_FOR_UPDATE";
	private static final String STATE_PROCESS = "PROCESS";
	private static final String STATE_READY = "READY";

	private static final String MODE_PARAMETER = "mode";
	private static final String DATE_PARAMETER = "date";
	private static final List<String> MODES = new ArrayList<String>(Arrays.asList(new String[] { "C", "T", "c", "t" }));
	private static String mode = "T";
	private static Date date = new Date();

	static Log logger = LogFactory.getLog(AbstractExtractLISSIAPoliciesForMathematicalReserveTaskLet.class);

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
		Map<String, JobParameter> params = jobParameters.getParameters();

		logger.info("Initialization of job parameters :");

		if (params.containsKey(MODE_PARAMETER)) {
			JobParameter modeParam = params.get(MODE_PARAMETER);
			String mode = String.valueOf(modeParam.getValue().toString());
			if (MODES.contains(mode))
				setMode(mode);
			else
				throw new IllegalArgumentException("mode must be C or T");
		} else {
			throw new IllegalArgumentException("Invalid missing mode");
		}

		if (params.containsKey(DATE_PARAMETER)) {
			JobParameter paramDateCom = params.get(DATE_PARAMETER);

			Date dateConvert = null;

			try {
				dateConvert = convertStringDate(paramDateCom.getValue().toString());
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid date : " + e);
			}

			if (dateConvert != null) {
				if (dateConvert.before(new Date())) {
					if (mode.toLowerCase().equals("c")) {
						if (!checkIfDateIsAMonthEnd(dateConvert)) {
							throw new IllegalArgumentException("The date must be the end of the month for the mode C");
						}
					}
					setDate(dateConvert);
				} else {
					throw new IllegalArgumentException("Invalid date : must be previous to today");
				}

			} else {
				throw new IllegalArgumentException("Invalid date");
			}

		} else {
			throw new IllegalArgumentException("Invalid missing date");
		}

		logger.info("Mode parameter : " + mode);
		logger.info("Date parameter : " + date);
	}

	public static void setMode(String param) {
		mode = param;

	}

	public String getMode() {
		return mode;

	}

	public static void setDate(Date param) {
		date = param;

	}

	public Date getDate() {
		return date;

	}

	/**
	 * Convert string date DD/MM/AAAA to Date
	 * 
	 * @param string
	 * @throws ParseException
	 */
	private static Date convertStringDate(String string) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = format.parse(string);
		return date;
	}

	private static boolean checkIfDateIsAMonthEnd(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return calendar.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth;
	}

}
