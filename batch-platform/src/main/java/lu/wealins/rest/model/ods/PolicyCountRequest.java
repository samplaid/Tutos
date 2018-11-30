/**
 * 
 */
package lu.wealins.rest.model.ods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author lax
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyCountRequest {

	private String agentId;

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

}
