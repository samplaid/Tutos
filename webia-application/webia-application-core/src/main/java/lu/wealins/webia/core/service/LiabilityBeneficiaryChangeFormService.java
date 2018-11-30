package lu.wealins.webia.core.service;

import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;

public interface LiabilityBeneficiaryChangeFormService {

	BeneficiaryChangeFormDTO initBeneficiaryChangeForm(String policyId, Integer workflowItemId);

	BeneficiaryChangeFormDTO getBeneficiaryChangeForm(String policyId, Integer workflowItemId);

	BeneficiaryChangeDTO getBeneficiaryChange(String policyId, Integer workflowItemId, String productCd, String lang);
}
