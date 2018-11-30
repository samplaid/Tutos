package lu.wealins.webia.core.service.helper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MoneyOutFeesHelper {

	private static final int SCALE = 8;

	private static final String FORM_TRANSACTION_FEES_CANT_BE_NULL = "The transaction fees can not be null.";
	private static final String FORM_BROKER_FEES_CANT_BE_NULL = "The broker fees can not be null.";
	private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

	public BigDecimal getBrokerFees(BigDecimal formTransactionFees, BigDecimal formBrokerFees) {

		Assert.notNull(formTransactionFees, FORM_TRANSACTION_FEES_CANT_BE_NULL);
		Assert.notNull(formBrokerFees, FORM_BROKER_FEES_CANT_BE_NULL);

		if (formTransactionFees.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}

		return HUNDRED.multiply(formBrokerFees.divide(formTransactionFees, SCALE, BigDecimal.ROUND_HALF_UP));
	}

}
