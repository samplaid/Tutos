package lu.wealins.common.dto.liability.services;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryChangeRequest extends AmendmentRequest {

	private ClientRoleActivationFlagDTO clientRoleActivationFlagDTO;
	private Map<String, String> applicationParams = new HashMap<>();


	public ClientRoleActivationFlagDTO getClientRoleActivationFlagDTO() {
		return clientRoleActivationFlagDTO;
	}

	public void setClientRoleActivationFlagDTO(ClientRoleActivationFlagDTO clientRoleActivationFlagDTO) {
		this.clientRoleActivationFlagDTO = clientRoleActivationFlagDTO;
	}

	public Map<String, String> getApplicationParams() {
		return applicationParams;
	}
	
	public void setApplicationParams(Map<String, String> applicationParams) {
		this.applicationParams = applicationParams;
	}
}
