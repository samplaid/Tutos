/**
 * 
 */
package lu.wealins.common.dto.liability.services.enums;

/**
 * Enumerates the agent contact function. All the functions have been copied from the {@link lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity UOPT_DETAILS} table with definition
 * id equal to {@code 62}
 * 
 * @author oro
 *
 */
public enum AgentContactFunction {
	BACKOFF("BACKOFF"), COMPTA("COMPTA"), COMMISSI("COMMISSI"), CPS("CPS"), DIRECT("DIRECT"), LEGAL("LEGAL"), OTHERF("LEGAL"), SALES("LEGAL");

	private final String code;

	private AgentContactFunction(String code) {
		this.code = code;
	}

	/**
	 * Returns the function code like the one that is stored in {@link lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity UOPT_DETAILS} table with definition id equal to {@code 62}
	 * 
	 * @return the code the code like the one stored in {@link lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity UOPT_DETAILS} table.
	 */
	public String getCode() {
		return code;
	}

}
