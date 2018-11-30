package lu.wealins.webia.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionsHistoryDTO;

import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.ws.rest.LiabilityPolicyRESTService;

@Component
public class LiabilityPolicyRESTServiceImpl implements LiabilityPolicyRESTService {


	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	LiabilityFundTransactionService liabilityTransactionService;

	@Override
	public PolicyClausesDTO getClauses(SecurityContext context, String polId, String productCd, String lang) {
		return policyService.getClauses(polId,productCd, lang );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityPolicyRESTService#getRolesByPolicies(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public RolesByPoliciesDTO getRolesByPolicies(SecurityContext context, Integer clientId) {
		return policyService.getRolesByPolicies(clientId);
	}

	@Override
	public Collection<PolicyTransactionsHistoryDTO> getPoliciesHistory(SecurityContext context, String policyId) {
		return liabilityTransactionService.getPolicyTransactionsHistory(policyId);
}


}
