/**
 * 
 */
package lu.wealins.webia.core.mapper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lu.wealins.editing.common.webia.AmountType;

/**
 * @author NGA
 *
 */
@Component
public class BigDecimalToAmountTypeMapper {
	public BigDecimal asBigDecimal(AmountType amountType) {
		return amountType == null ? null : amountType.getValue();
	}

	public AmountType asAmountType(BigDecimal amount) {

			AmountType amountType = new AmountType();
			if (amount == null) {
				return null;
			}
			amountType.setValue(amount);
			return amountType;

	}

}
