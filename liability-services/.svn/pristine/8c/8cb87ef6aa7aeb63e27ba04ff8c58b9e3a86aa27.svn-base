package lu.wealins.liability.services.core.business;

import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;

public interface PolicyValuationService {

	PolicyValuationDTO getPolicyValuation(String policy, Date date);

	PolicyValuationDTO getPolicyAfterValuation(String policy, Date date, Long transaction);

	PolicyValuationDTO getPolicyValuation(String policy, Date date, String otherCurrency);

	PolicyValuationDTO getBrokerPolicyValuation(String agtId, String otherCurrency);

	PolicyValuationDTO getHolderPolicyValuation(String cliId, String otherCurrency);

	/**
	 * Get policy valuations after transaction.
	 * 
	 * @param transactionId
	 *            the transactionId
	 * @return all valuations of policy after transaction.
	 */
	List<PolicyTransactionValuationDTO> getPolicyValuationAfterTransaction(Long transactionId);
}
