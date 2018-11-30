package lu.wealins.common.dto.liability.services.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * See [OPTION_DETAILS] where FK_OPTIONSOPT_ID like '%AGSPME%'
 * 
 * @author AMC
 *
 */
public enum PaymentMethod {
	CHEQUE(1), TRANSFER(2), FAX(3), SEPA(4), SWIFT(5), SWIFT_FAX(6);

	public static Map<Integer, PaymentMethod> PAYMENT_METHOD_MAP = null;

	private int value;

	PaymentMethod(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static PaymentMethod toPaymentMethod(Integer value) {
		if (PAYMENT_METHOD_MAP == null) {
			PAYMENT_METHOD_MAP = Stream.of(values()).collect(Collectors.toMap(k -> Integer.valueOf(k.getValue()), v -> v));
		}

		return PAYMENT_METHOD_MAP.get(value);
	}
}
