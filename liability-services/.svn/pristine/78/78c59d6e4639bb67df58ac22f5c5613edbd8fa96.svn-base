package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByFundAndCoverageDTO;
import lu.wealins.liability.services.core.business.PolicyCoverageService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.ws.rest.PolicyCoverageRESTService;

@Component
public class PolicyCoverageRESTServicempl implements PolicyCoverageRESTService {
	@Autowired
	private PolicyCoverageService policyCoverageService;

	@Autowired
	private PolicyService policyService;

	@Override
	public PolicyCoverageDTO loadPolicyCoverage(SecurityContext ctx, String policyId, Integer coverage) {
		PolicyEntity policy = policyService.getPolicyEntity(policyId);
		return policyCoverageService.getCoverage(policy, coverage);
	}

	@Override
	public PolicyCoverageDTO createAdditionalPremium(SecurityContext ctx, AdditionalPremiumDTO request) {

		return policyCoverageService.createPolicyPremium(request);
	}

	@Override
	public PolicyCoverageDTO getLastPolicyCoverage(SecurityContext ctx, String policyId) {
		return policyCoverageService.getLastPolicyCoverage(policyId);
	}

	@Override
	public PolicyCoverageDTO getFirstPolicyCoverage(SecurityContext ctx, String policyId) {
		return policyCoverageService.getFirstPolicyCoverage(policyId);
	}

	@Override
	public List<String> validateNewCoverageCreation(SecurityContext ctx, String policyId) {
		return policyCoverageService.validateNewCoverageCreation(policyId);
	}

	@Override
	public Map<String, Collection<Integer>> getCoverages(SecurityContext context, String polId, List<String> fdsIds) {
		return policyCoverageService.getCoverages(polId, fdsIds);
	}

	@Override
	public Collection<UnitByCoverageDTO> getUnitByCoverages(SecurityContext context, String polId) {
		return policyCoverageService.getUnitByCoverages(polId);
	}

	public Collection<UnitByFundAndCoverageDTO> getUnitByFundsAndCoverages(SecurityContext context, String polId) {
		return policyCoverageService.getUnitByFundsAndCoverages(polId);
	}

}
