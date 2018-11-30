package lu.wealins.liability.services.core.business;

import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;

public interface PolicyValuationHoldingService {

	List<PolicyValuationHoldingDTO> getPolicyValuationHoldings(String policy, Date date);

	List<PolicyValuationHoldingDTO> getPolicyAfterValuation(String policy, Long tran);

}
