package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherClientDTO extends ClientDTO {

	private int roleNumber;
	private String role;
	private BigDecimal percentageSplit;

	public int getRoleNumber() {
		return roleNumber;
	}

	public void setRoleNumber(int roleNumber) {
		this.roleNumber = roleNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public BigDecimal getPercentageSplit() {
		return percentageSplit;
	}
	
	public void setPercentageSplit(BigDecimal percentageSplit) {
		this.percentageSplit = percentageSplit;
	}

	// DO NOT ATTEMPT TO REMOVE THIS CODE.
	// A same client (with same id and same role) should not appear more than once in policy view.
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + roleNumber;
		return result;
	}

	// DO NOT ATTEMPT TO REMOVE THIS CODE.
	// A same client (with same id and same role) should not appear more than once in policy view.
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OtherClientDTO other = (OtherClientDTO) obj;

		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (roleNumber != other.roleNumber)
			return false;
		return true;
	}

}
