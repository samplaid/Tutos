package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.mapper.PolicyTransferFormMapper;
import lu.wealins.webia.services.core.persistence.entity.PolicyTransferFormEntity;
import lu.wealins.webia.services.core.persistence.repository.PolicyTransferFormRepository;
import lu.wealins.webia.services.core.service.PolicyTransferFormService;
import lu.wealins.common.dto.webia.services.PolicyTransferFormDTO;

@Service
public class PolicyTransferFormServiceImpl implements PolicyTransferFormService {

	private static final String FORM_ID_CANNOT_BE_NULL = "Form id cannot be null";
	private static final String POLICY_TRANSFER_FORM_CANNOT_BE_NULL = "Policy Transfer form cannot be null.";

	@Autowired
	private PolicyTransferFormRepository policyTransferFormRepository;

	@Autowired
	private PolicyTransferFormMapper policyTransferFormMapper;
	
	private PolicyTransferFormEntity update(PolicyTransferFormDTO policyTransferForm) {
		Assert.notNull(policyTransferForm, POLICY_TRANSFER_FORM_CANNOT_BE_NULL);

		PolicyTransferFormEntity partnerEntity = policyTransferFormMapper.asPolicyTransferForm(policyTransferForm);

		return policyTransferFormRepository.save(partnerEntity);
	}

	@Override
	public void delete(Collection<PolicyTransferFormEntity> policyTransferFormsToDelete) {
		for (PolicyTransferFormEntity pt : policyTransferFormsToDelete) {
			policyTransferFormRepository.delete(pt);
		}
	}

	@Override
	public Collection<PolicyTransferFormEntity> update(Collection<PolicyTransferFormDTO> policyTransferForms, Integer formId) {
		Assert.notNull(formId, FORM_ID_CANNOT_BE_NULL);
		Collection<PolicyTransferFormEntity> updatedPolicyTransferForms = new ArrayList<>();
		for (PolicyTransferFormDTO policyTransferForm : policyTransferForms) {
			policyTransferForm.setFormId(formId);
			updatedPolicyTransferForms.add(update(policyTransferForm));
		}
		return updatedPolicyTransferForms;
	}

	@Override
	public Collection<PolicyTransferFormEntity> getPolicyTransfers(Integer appFormId) {
		return policyTransferFormRepository.findByFormId(appFormId);
	}

}
