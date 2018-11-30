package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.PolicyFundHoldingDTO;

public interface PolicyFundHoldingService {

	Collection<PolicyFundHoldingDTO> getHoldingsByPolicy(String policy);
}
