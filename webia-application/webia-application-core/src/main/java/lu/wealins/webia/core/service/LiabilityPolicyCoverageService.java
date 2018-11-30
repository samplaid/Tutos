package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByFundAndCoverageDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface LiabilityPolicyCoverageService {

	/**
	 * Create an additional premium
	 * 
	 * @param AdditionalPremiumDTO.
	 * @return PolicyPremiumDTO.
	 */
	PolicyCoverageDTO createAdditionalPremium(AppFormDTO appForm);

	PolicyCoverageDTO getLastPolicyCoverage(String policyId);

	PolicyCoverageDTO getFirstPolicyCoverage(String policyId);

	List<String> validateCreationNewCoverage(String policyId);

	Map<String, Collection<Integer>> getCoverages(String polId, Collection<String> fdsIds);

	Collection<UnitByCoverageDTO> getUnitByCoverages(String polId);

	Collection<UnitByFundAndCoverageDTO> getUnitByFundsAndCoverages(String polId);

}
