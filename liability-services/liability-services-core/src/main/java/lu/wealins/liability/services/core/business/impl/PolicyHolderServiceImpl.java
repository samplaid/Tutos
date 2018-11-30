package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.PolicyHolderService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyValuationService;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipMapper;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

@Service
public class PolicyHolderServiceImpl implements PolicyHolderService {

	@Autowired
	private CliPolRelationshipMapper cliPolRelationshipMapper;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private PolicyValuationService policyValuationService;

	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyHolderService#getPolicyHolders(lu.wealins.liability.services.core.persistence.entity.PolicyEntity)
	 */
	@Override
	public Collection<PolicyHolderDTO> getPolicyHolders(PolicyEntity policy) {
		List<PolicyHolderDTO> policyHolders = cliPolRelationshipMapper.asPolicyHolderDTOs(policy).stream().collect(Collectors.toList());
		policyHolders.sort((left, right) -> left.getDisplayNumber() - right.getDisplayNumber());
		return policyHolders;
	}

	@Override
	public Collection<PolicyHolderDTO> getDeadHolders(Collection<CliPolRelationshipEntity> cliPolRelationships) {
		return cliPolRelationshipMapper.asDeadPolicyHolderDTOs(cliPolRelationships);
	}

	@Override
	public Collection<PolicyHolderDTO> getPolicyHolders(String workflowItemId) {
		return cliPolRelationshipMapper.asPolicyHolderDTOs(workflowItemId);
	}

	@Override
	public Collection<PolicyHolderLiteDTO> getPolicyHolderLites(PolicyEntity policy) {
		return cliPolRelationshipMapper.asPolicyHolderLiteDTOs(policy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyHolderService#getPortfolioAmount(java.lang.Integer, java.util.Date, java.lang.String)
	 */
	@Override
	public BigDecimal getPortfolioAmount(Integer polycHolder, Date date, String currency) {
		Set<PolicyDTO> policies = policyService.getPoliciesForHolder(polycHolder);

		BigDecimal portfolio = BigDecimal.ZERO;

		for (PolicyDTO policy : policies) {
			PolicyValuationDTO policyValuation = policyValuationService.getPolicyValuation(policy.getPolId(), date, currency);
			if (policyValuation != null) {
				BigDecimal totalOtherCurrency = policyValuation.getTotalOtherCurrency();
				if (totalOtherCurrency != null) {
					portfolio = portfolio.add(totalOtherCurrency);
				}
			}
		}
		return portfolio;
	}

	public void updatePolicyHolders(Collection<PolicyHolderDTO> policyHolders, String policyId, Date activeDate, String workflowItemId) {
		policyHolders.forEach(policyHolder -> cliPolRelationshipService.savePolicyHolder(policyHolder, policyId, activeDate, workflowItemId));
	}

}
