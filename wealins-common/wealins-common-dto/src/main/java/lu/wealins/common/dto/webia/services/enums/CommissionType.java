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
public enum CommissionType {

	/**
	 * {@link CommissionConstant#ADM ADM} commission type.
	 */
	ADM(CommissionConstant.ADM),

	/**
	 * {@link CommissionConstant#SWITCH SWITCH} commission type.
	 */
	SWITCH(CommissionConstant.SWITCH),

	/**
	 * {@link CommissionConstant#SURR SURR} commission type.
	 */
	SURR(CommissionConstant.SURR),

	/**
	 * {@link CommissionConstant#OPCVM OPCVM} commission type.
	 */
	OPCVM(CommissionConstant.OPCVM),

	/**
	 * {@link CommissionConstant#PRADM PRADM} commission type.
	 */
	PRADM(CommissionConstant.PRADM),

	/**
	 * {@link CommissionConstant#ENTRY ENTRY} commission type.
	 */
	ENTRY(CommissionConstant.ENTRY),

	/**
	 * {@link CommissionConstant#PORTFOLIO PORTFOLIO} commission type.
	 */
	PORTFOLIO(CommissionConstant.PORTFOLIO);

	private final String value;

	private CommissionType(String value) {
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
