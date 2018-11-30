package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.webia.core.service.LiabilityBeneficiaryChangeFormService;
import lu.wealins.webia.ws.rest.LiabilityBeneficiaryChangeFormRESTService;

@Component
public class LiabilityBeneficiaryChangeFormRESTServiceImpl implements LiabilityBeneficiaryChangeFormRESTService {

	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";
	@Autowired
	private LiabilityBeneficiaryChangeFormService beneficiaryChangeFormService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityBeneficiaryChangeFormRESTService#initBeneficiaryChangeForm(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public BeneficiaryChangeFormDTO initBeneficiaryChangeForm(SecurityContext context, String policyId, Integer workflowItemId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return beneficiaryChangeFormService.initBeneficiaryChangeForm(policyId, workflowItemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityBeneficiaryChangeFormRESTService#getBeneficiaryChangeForm(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public BeneficiaryChangeFormDTO getBeneficiaryChangeForm(SecurityContext context, String policyId, Integer workflowItemId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return beneficiaryChangeFormService.getBeneficiaryChangeForm(policyId, workflowItemId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityBeneficiaryChangeFormRESTService#getBeneficiaryChange(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public BeneficiaryChangeDTO getBeneficiaryChange(SecurityContext context, String policyId, Integer workflowItemId, String productCd, String lang) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return beneficiaryChangeFormService.getBeneficiaryChange(policyId, workflowItemId,productCd, lang);
	}

}
