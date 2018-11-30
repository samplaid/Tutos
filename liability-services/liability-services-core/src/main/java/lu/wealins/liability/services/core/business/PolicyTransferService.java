package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.liability.services.core.persistence.entity.PolicyTransferEntity;
import lu.wealins.common.dto.liability.services.PolicyTransferDTO;

public interface PolicyTransferService {

	Collection<PolicyTransferDTO> getPolicyTransfers(String policy);

	Collection<PolicyTransferEntity> update(Collection<PolicyTransferDTO> policyTransfers);

	void deleteByPolicyAndCoverage(String policy, int coverage);
}
