/**
 * 
 */
package lu.wealins.webia.services.core.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.common.dto.webia.services.constantes.CommissionConstant;
import lu.wealins.common.dto.webia.services.enums.CommissionStatus;

/**
 * This class holds utilities for commission
 * 
 * @author oro
 *
 */
public class CommissionUtils {

	/**
	 * Indicates if the commission is reconciled.
	 * 
	 * @param commission the commission to check
	 * @return true if the commission is reconciled
	 */
	public static boolean isReconciled(CommissionReconciliationDTO commission) {
		return CommissionStatus.RECONCILED.getValue().equals(commission.getStatus()) && BigDecimal.ZERO.compareTo(commission.getGap()) == 0;
	}

	/**
	 * Convert the sap commission type to webia commission type.
	 * 
	 * @param sapCommissionType the sap commission type.
	 * @return list of webia commission type
	 */
	public static List<String> asWebiaCommissionTypes(String sapCommissionType) {
		List<String> commissionTypes = new ArrayList<>();

		if (CommissionConstant.ENTRY.equalsIgnoreCase(sapCommissionType)) {
			commissionTypes.add(CommissionConstant.ENTRY);
		} else if (CommissionConstant.PORTFOLIO.equalsIgnoreCase(sapCommissionType)) {
			commissionTypes = CommissionConstant.PORTFOLIO_GROUP;
		} else {
			commissionTypes.add(CommissionConstant.UNKNOWN);
		}

		return commissionTypes;
	}

	/**
	 * Default constructor of this class. This method cannot be invoked.
	 */
	private CommissionUtils() {
		throw new UnsupportedOperationException("Could not invoke the constructor of this class");
	}
}
