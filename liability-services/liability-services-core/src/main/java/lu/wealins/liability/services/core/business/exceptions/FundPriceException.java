package lu.wealins.liability.services.core.business.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FundPriceException extends Exception {

	private static final long serialVersionUID = -7209960877084615482L;

	private static Logger businessLogger = LoggerFactory.getLogger("business");

	private FundPriceExceptionCode errorCode;

	public FundPriceException(FundPriceExceptionCode errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public static void throwIfNull(Object o, FundPriceExceptionCode errorCode, String msg) throws FundPriceException {
		if (o == null) {
			businessLogger.error(msg);
			throw new FundPriceException(errorCode, msg);
		}
	}

	public static void throwIfNotNull(Object o, FundPriceExceptionCode errorCode, String msg) throws FundPriceException {
		if (o != null) {
			businessLogger.error(msg);
			throw new FundPriceException(errorCode, msg);
		}
	}

	public FundPriceExceptionCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(FundPriceExceptionCode errorCode) {
		this.errorCode = errorCode;
	}

}
