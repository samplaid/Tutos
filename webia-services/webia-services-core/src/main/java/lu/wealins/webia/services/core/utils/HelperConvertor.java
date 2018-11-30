package lu.wealins.webia.services.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class HelperConvertor {
	public static String toString(BigDecimal value, int scale) {
		return value.setScale(scale, RoundingMode.DOWN).toPlainString();
	}
}
