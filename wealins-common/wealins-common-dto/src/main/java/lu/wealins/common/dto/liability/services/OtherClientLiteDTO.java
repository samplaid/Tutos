package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherClientLiteDTO extends ClientLiteDTO {

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

}
