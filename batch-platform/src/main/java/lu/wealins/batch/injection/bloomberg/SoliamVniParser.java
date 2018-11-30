package lu.wealins.batch.injection.bloomberg;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lu.wealins.batch.injection.AbstractInjectionSimpleParser;
import lu.wealins.camel.utils.MessageUtils;
import lu.wealins.rest.model.BatchInjectionControlResponse;
import lu.wealins.rest.model.Error;

/**
 * Parser for the bloomberg response file
 * 
 * @author xqv60
 *
 */
@Component
public class SoliamVniParser extends AbstractInjectionSimpleParser implements MessageListener {

	private static final String ERROR = "ERROR";

	private static final String FLD = "FLD";

	private static final String N_S = "N.S.";

	private static final String N_D = "N.D.";

	private static final String N_A = "N.A.";

	private static final String RETURN_OK = "0";

	/**
	 * Soliam folder to inject the vni
	 */
	@Value("${soliamVniInjectionSuccessPath:}")
	private String soliamVniInjectionSuccessPath;

	/**
	 * Error folder with vni information
	 */
	@Value("${soliamVniInjectionFailurePath:}")
	private String soliamVniInjectionFailurePath;
	
	/**
	 * Input folder containing vni responses
	 */
	@Value("${soliamVniInjectionInputFolder}")
	private String soliamVniInputFolder;

	private static Map<String, String> errorsCode = new HashMap<>();
	{
		errorsCode.put("9", "Asset class not supported for BVAL Tier1 pricing");
		errorsCode.put("10", "Bloomberg cannot find the security as specified");
		errorsCode.put("11", "Restricted Security");
		errorsCode.put("123", "User not authorized for private loan (PRPL)");
		errorsCode.put("605", "Invalid macro value");
		errorsCode.put("988", "System Error on security level");
		errorsCode.put("989", "Unrecognized pricing source");
		errorsCode.put("990", "System Error. Contact Product Support and Technical Assistance");
		errorsCode.put("991", "Invalid override value (e.g., bad date or number) or Maximum number of overrides (20) exceeded");
		errorsCode.put("992", "Unknown override field");
		errorsCode.put("993", "Maximum number of overrides exceeded");
		errorsCode.put("994", "Permission denied");
		errorsCode.put("995", "Maximum number of fields exceeded");
		errorsCode.put("996", "Maximum number of data points exceeded (some data for this security is missing)");
		errorsCode.put("997", "General override error (e.g., formatting error)");
		errorsCode.put("998", "Security identifier type (e.g., CUSIP) is not recognized");
		errorsCode.put("999", "Unloadable secruity");
		errorsCode.put(" ", "requested field and security combination is not applicable");
		errorsCode.put(N_A, "Data is missing, because Bloomberg does not have the data");
		errorsCode.put(N_D, "Not downloadable, because user does not have permission to download the field");
		errorsCode.put(N_S,
				"Not subscribed, because user 1) is not entitled to download requested field and security combination, or 2) has hit the montly limits on a test account, or 3) user has not flagged correct field category in request header");
		errorsCode.put(FLD, "UNKNOWN");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.batch.injection.AbstractInjectionParser#buildInjectionControlRequest(java.lang.String)
	 * 
	 * Example of line : LU0157929810|0|5|LU0157929810|2096.990000|20170919|EUR|LX|
	 */
	@Override
	public BatchInjectionControlResponse buildInjectionControlRequest(String line) {
		BatchInjectionControlResponse response = new BatchInjectionControlResponse();

		String[] columns = line.split("\\|");

		String id = columns[0];
		String returnCode = columns[1];
		if (StringUtils.equals(returnCode, RETURN_OK)) {
			String isinCode = columns[3];
			String price = columns[4];
			String priceDate = columns[5];
			String currency = columns[6] == null ? StringUtils.EMPTY : columns[6].toUpperCase();

			// Control data
			if (isDataInError(columns[3])) { // isin code in error
				response.getErrors().add(buildError(id, isinCode, line));
			}

			if (isDataInError(columns[4])) { // price in error
				response.getErrors().add(buildError(id, price, line));
			}

			if (isDataInError(columns[5])) { // close date price in error
				response.getErrors().add(buildError(id, priceDate, line));
			}

			if (isDataInError(columns[6])) { // currency in error
				response.getErrors().add(buildError(id, currency, line));
			}

			// If no errors, we create a line
			if (response.getErrors().isEmpty()) {
				String[] spliDate = priceDate.split("\\/");
				String dateFormated = spliDate[2] + "/" + spliDate[1] + "/" + spliDate[0];
				
				// Check the currency for GBp. If GBp the price is divided by 100, otherwise it doesn't change.
				if ("GBp".equals(columns[6])) {
					BigDecimal GBpPrice = new BigDecimal(price);
					GBpPrice = GBpPrice.divide(new BigDecimal(100));
					price = GBpPrice.toPlainString();
				}

				StringBuilder lineInjection = new StringBuilder();
				lineInjection.append("CLE").append(TABULATION_DELIMITER).append(isinCode + "+" + currency)
						.append(TABULATION_DELIMITER).append("CVF").append(TABULATION_DELIMITER).append(price).append(TABULATION_DELIMITER).append(currency)
						.append(TABULATION_DELIMITER)
						.append(dateFormated)
						.append(TABULATION_DELIMITER).append(TABULATION_DELIMITER).append("INJECT");

				response.getLines().add(lineInjection.toString());

			}

		} else {
			// Error
			response.getErrors().add(buildError(id, returnCode, line));

		}

		// No errors
		if (response.getErrors().isEmpty()) {
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}

	/**
	 * Build an error
	 * 
	 * @param id the id line
	 * @param key the code of the error
	 * @param line the line in error
	 * @return the error object
	 */
	private Error buildError(String id, String key, String line) {
		Error error = new Error();
		error.setCode(ERROR);

		if (errorsCode.containsKey(key)) {
			error.setMessage(line + ":" + errorsCode.get(key));
			logger.error("Error during the bloomberg file parsing : the line with the isin " + id + " is not right. Error code " + key + " : " + errorsCode.get(key));

		} else {
			error.setMessage(line + ": unknwon error");
			logger.error("Error during the bloomberg file parsing : the line with the isin " + id + " is not right. Error code " + key + " : unknwon error");
		}

		return error;
	}

	/**
	 * Control the data following the bloomberg rules
	 * 
	 * @param data
	 * @return
	 */
	private boolean isDataInError(String data) {

		if (StringUtils.isEmpty(data) || StringUtils.equals(data, N_A) || StringUtils.equals(data, N_D)
				|| StringUtils.equals(data, N_S) || StringUtils.equals(data, FLD)) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	@Override
	public String getInjectionFailurePath() {
		return soliamVniInjectionFailurePath;
	}

	@Override
	public String getInjectionSuccessPath() {
		return soliamVniInjectionSuccessPath;
	}

	@Override
	public void onMessage(Message message) {
		try {
			File vniFile = MessageUtils.saveFileArchive(message, soliamVniInputFolder);
			parse(vniFile);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}