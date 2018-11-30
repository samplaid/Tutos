package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;

public interface PolicyHolderService {

	/**
	 * Get policy holders from client forms.
	 * 
	 * @param clientForms The client forms
	 * @return The policy holders.
	 */
	Collection<PolicyHolderFormDTO> getPolicyHolders(Collection<ClientFormEntity> clientForms);

	Collection<ClientFormEntity> update(Collection<PolicyHolderFormDTO> policyHolders, Integer formId);
}
