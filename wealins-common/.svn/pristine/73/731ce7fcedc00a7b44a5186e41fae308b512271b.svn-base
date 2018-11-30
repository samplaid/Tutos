package lu.wealins.common.dto.liability.services;

import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RolesByPolicyDTO {

	private Collection<OptionDetailDTO> roles = new HashSet<>();
	private PolicyLightDTO policy;

	public Collection<OptionDetailDTO> getRoles() {
		return roles;
	}

	public void setRoles(Collection<OptionDetailDTO> roles) {
		this.roles = roles;
	}

	public PolicyLightDTO getPolicy() {
		return policy;
	}

	public void setPolicy(PolicyLightDTO policy) {
		this.policy = policy;
	}

}
