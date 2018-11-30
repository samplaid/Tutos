package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRoleActivationDTO implements ActivableRoleBasedCountry {

	private Integer roleNumber;

	private boolean enable;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry#getRoleNumber()
	 */
	@Override
	public Integer getRoleNumber() {
		return this.roleNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry#setEnable(boolean)
	 */
	@Override
	public ActivableRoleBasedCountry setEnable(boolean enable) {
		this.enable = enable;
		return this;
	}

	public void setRoleNumber(Integer roleNumber) {
		this.roleNumber = roleNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry#isEnable()
	 */
	@Override
	public boolean isEnable() {
		return this.enable;
	}



}
