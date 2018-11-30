/**
 * 
 */
package lu.wealins.common.dto.liability.services.enums;

/**
 * @author oro
 *
 */
public enum AgentContactStatus {
	ACTIVE("1"), INACTIVE("2");

	private final String status;

	private AgentContactStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

}
