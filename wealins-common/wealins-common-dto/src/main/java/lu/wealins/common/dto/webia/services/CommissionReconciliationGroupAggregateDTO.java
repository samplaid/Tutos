/**
 * 
 */
package lu.wealins.common.dto.webia.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the aggregate of the commission reconciliation group. <br>
 * The fields {@code agent code} and {@code agent name} is used to identify the uniqueness of this object and also the group. The {@code status} field is only used to provide the state of the group.
 * 
 * @author oro
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommissionReconciliationGroupAggregateDTO {

	private final String agentId;

	/** Contains the agent name */
	private final String name;

	/** Contains the agent crmId */
	private String crmId;

	/** Represents the status of reconciliation group. */
	private String status;
	
	/** Represents if the reconciliation group has a statement id. */
	private boolean statement;

	@JsonCreator
	public CommissionReconciliationGroupAggregateDTO(@JsonProperty("agentId") String agentId, @JsonProperty("name") String name) {
		Assert.notNull(agentId, "Could not construct an instance of this object. The agentId passed in constrcutor is null.");
		Assert.notNull(name, "Could not construct an instance of this object. The name passed in constrcutor is null.");
		this.agentId = StringUtils.stripToNull(agentId);
		this.name = StringUtils.stripToNull(name);
	}
	/**
	 * Default constructor of this class
	 */
	@JsonIgnore
	public CommissionReconciliationGroupAggregateDTO(CommissionReconciliationDTO commission) {
		this(commission.getAgentId(), commission.getName());
	}

	/**
	 * Retrieve the status of the group
	 * 
	 * @return the status thegroup status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of the group
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Retrieves the agent code
	 * 
	 * @return the agentId the agent code
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * Retrieves the agent name.
	 * 
	 * @return the name the agent name
	 */
	public String getName() {
		return name;
	}

	public String getCrmId() {
		return crmId;
	}

	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}
	
	/**
	 * @return the statement
	 */
	public boolean isStatement() {
		return statement;
	}
	
	/**
	 * @param statement the statement to set
	 */
	public void setStatement(boolean statement) {
		this.statement = statement;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommissionReconciliationGroupAggregateDTO other = (CommissionReconciliationGroupAggregateDTO) obj;
		if (agentId == null) {
			if (other.agentId != null)
				return false;
		} else if (!agentId.equals(other.agentId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommissionReconciliationGroupAggregateDTO [agentId=");
		builder.append(agentId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", crmId=");
		builder.append(crmId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
