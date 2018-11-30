/**
 * 
 */
package lu.wealins.common.dto.webia.services.enums;

import lu.wealins.common.dto.webia.services.constantes.CommissionConstant;

/**
 * Enumerates the commission type.
 * 
 * @author oro
 *
 */
public enum CommissionStatus {

	/**
	 * {@link CommissionConstant#VALIDATED VALIDATED} commission status.
	 */
	VALIDATED(CommissionConstant.VALIDATED),

	/**
	 * {@link CommissionConstant#NOT_RECONCILED NOT_RECONCILED} commission status.
	 */
	NOT_RECONCILED(CommissionConstant.NOT_RECONCILED),

	/**
	 * {@link CommissionConstant#RECONCILED RECONCILED} commission status.
	 */
	RECONCILED(CommissionConstant.RECONCILED),

	/**
	 * {@link CommissionConstant#DONE DONE} commission status.
	 */
	DONE(CommissionConstant.DONE);

	private final String value;

	private CommissionStatus(String value) {
		this.value = value;
	}

	/**
	 * Returns the value of this type.
	 * 
	 * @return the value the type value
	 */
	public String getValue() {
		return value;
	}

}
