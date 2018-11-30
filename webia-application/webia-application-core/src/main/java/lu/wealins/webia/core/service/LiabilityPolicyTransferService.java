package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.PolicyTransferDTO;

public interface LiabilityPolicyTransferService {

	Collection<PolicyTransferDTO> getPolicyTransfers(String policy);
}
