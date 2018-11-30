package lu.wealins.common.dto.liability.services;

public class PolicyClientRoleViewRequest {

	private ClientRoleActivationFlagDTO clientRoleActivationFlagDTO;
	private String policyId;

	public ClientRoleActivationFlagDTO getClientRoleActivationFlagDTO() {
		return clientRoleActivationFlagDTO;
	}

	public void setClientRoleActivationFlag(ClientRoleActivationFlagDTO clientRoleActivationFlagDTO) {
		this.clientRoleActivationFlagDTO = clientRoleActivationFlagDTO;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

}