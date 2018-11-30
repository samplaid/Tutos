package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByCoverageDTO;
import lu.wealins.common.dto.liability.services.UnitByFundAndCoverageDTO;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

public interface PolicyCoverageService {

	/**
	 * Get the first policy coverage linked to a policy.
	 * 
	 * @param policyEntity The policy.
	 * @return The first policy holders.
	 */
	PolicyCoverageDTO getFirstPolicyCoverage(PolicyEntity policyEntity);

	PolicyCoverageDTO getFirstPolicyCoverage(String polId);

	/**
	 * Get a policy coverage.
	 * 
	 * @param policyEntity The policy.
	 * @param coverage The coverage.
	 * @return The coverage dto.
	 */
	PolicyCoverageDTO getCoverage(PolicyEntity policyEntity, Integer coverage);

	PolicyCoverageDTO getCoverage(String policyId, int coverage);

	/**
	 * create an additional policy premium
	 * 
	 * @param AdditionalPremiumDTO.
	 * @return the new policy coverage
	 */
	PolicyCoverageDTO createPolicyPremium(AdditionalPremiumDTO request);

	PolicyCoverageDTO getLastPolicyCoverage(String policyId);

	List<String> validateNewCoverageCreation(String policyId);

	Collection<Integer> getCoverages(String polId, String fdsId);

	Map<String, Collection<Integer>> getCoverages(String polId, Collection<String> fdsIds);

	Collection<UnitByCoverageDTO> getUnitByCoverages(String polId);

	Collection<UnitByFundAndCoverageDTO> getUnitByFundsAndCoverages(String polId);
}
