package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.Date;

import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.OtherClientLiteDTO;

public interface OtherClientService {

	/**
	 * Get other clients linked to a policy.
	 * 
	 * @param policy The policy.
	 * @return The other clients.
	 */
	Collection<OtherClientDTO> getOtherClients(PolicyEntity policy);

	Collection<OtherClientLiteDTO> getOtherClientLites(PolicyEntity policy);

	Collection<OtherClientDTO> getOtherClients(PolicyEntity policy, ClientRoleActivationFlagDTO clientRoleActivationFlag);

	Collection<OtherClientDTO> getOtherClients(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag);

	void updateOtherClients(Collection<OtherClientDTO> clients, String policyId, Date activeDate, String workflowItemId);
}
