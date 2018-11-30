package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;

public interface LiabilityPolicyValuationService {

	/**
	 * Get the policy valuation.
	 * 
	 * @param policyId The policy id.
	 * @return The policy.
	 */
	PolicyValuationDTO getPolicyValuation(String policyId, String currency, Date date);

	/**
	 * Get the policy valuation after transaction for French tax.
	 * 
	 * @param transactionId
	 *            The transaction id.
	 * @return The all valuation.
	 */
	Collection<PolicyTransactionValuationDTO> getPolicyValuationAfterTransaction(Long transactionId);

	PolicyValuationHoldingDTO getFundValuation(PolicyValuationDTO policyValuation, String fdsId);
}
