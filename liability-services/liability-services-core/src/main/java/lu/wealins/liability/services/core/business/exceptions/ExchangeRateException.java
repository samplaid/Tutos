package lu.wealins.liability.services.core.business.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeRateException extends Exception {

	private static final long serialVersionUID = -5513286695489637858L;

	private static Logger businessLogger = LoggerFactory.getLogger("business");

	private ExchangeRateExceptionCode errorCode;

	public ExchangeRateException(ExchangeRateExceptionCode errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public static void throwIfNull(Object o, ExchangeRateExceptionCode errorCode, String msg) throws ExchangeRateException {
		if (o == null) {
			businessLogger.error(msg);
			throw new ExchangeRateException(errorCode, msg);
		}
	}

	public static void throwIfNotNull(Object o, ExchangeRateExceptionCode errorCode, String msg) throws ExchangeRateException {
		if (o != null) {
			businessLogger.error(msg);
			throw new ExchangeRateException(errorCode, msg);
		}
	}

	public ExchangeRateExceptionCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ExchangeRateExceptionCode errorCode) {
		this.errorCode = errorCode;
	}

}
