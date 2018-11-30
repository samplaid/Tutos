package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.PolicyFundHoldingDTO;

public interface LiabilityPolicyHoldingService {

	Collection<PolicyFundHoldingDTO> getHoldingsByPolicy(String policy);
}
