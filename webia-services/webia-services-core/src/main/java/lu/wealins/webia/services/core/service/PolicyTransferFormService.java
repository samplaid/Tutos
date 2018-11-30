package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.webia.services.core.persistence.entity.PolicyTransferFormEntity;
import lu.wealins.common.dto.webia.services.PolicyTransferFormDTO;

public interface PolicyTransferFormService {

	Collection<PolicyTransferFormEntity> getPolicyTransfers(Integer appFormId);
	
	Collection<PolicyTransferFormEntity> update(Collection<PolicyTransferFormDTO> policyTransferForms, Integer formId);

	void delete(Collection<PolicyTransferFormEntity> policyTransferForms);
	
}
