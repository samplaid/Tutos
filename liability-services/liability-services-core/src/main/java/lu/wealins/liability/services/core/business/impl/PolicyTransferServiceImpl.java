package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.liability.services.core.business.PolicyTransferService;
import lu.wealins.liability.services.core.mapper.PolicyTransferMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyTransferEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyTransferRepository;
import lu.wealins.common.dto.liability.services.PolicyTransferDTO;

@Service
public class PolicyTransferServiceImpl implements PolicyTransferService {

	@Autowired
	private PolicyTransferRepository policyTransferRepository;

	@Autowired
	private PolicyTransferMapper policyTransferMapper;

	@Override
	public Collection<PolicyTransferDTO> getPolicyTransfers(String policy) {

		Collection<PolicyTransferEntity> entityList = policyTransferRepository.findByPolicy(policy);

		return policyTransferMapper.asPolicyTransferDTOCollection(entityList);
	}

	@Override
	public Collection<PolicyTransferEntity> update(Collection<PolicyTransferDTO> policyTransfers) {
		Collection<PolicyTransferEntity> policyTransferEntities = new ArrayList<>();
		for (PolicyTransferDTO policyTransferDTO : policyTransfers) {
			PolicyTransferEntity policyTransferEntity = policyTransferMapper.asPolicyTransferEntity(policyTransferDTO);

			PolicyTransferEntity policyTransferFromDB = policyTransferRepository.findByPolicyAndCoverageAndFromPolicy(policyTransferEntity.getPolicy(), policyTransferEntity.getCoverage(),
					policyTransferEntity.getFromPolicy());

			if (policyTransferFromDB != null) {
				policyTransferFromDB.setFromPolicyEffectDt(policyTransferEntity.getFromPolicyEffectDt());
			} else {
				policyTransferFromDB = policyTransferEntity;
			}

			policyTransferFromDB = policyTransferRepository.save(policyTransferFromDB);

			// Update id in the DTO
			policyTransferDTO.setPolicyTransferId(policyTransferFromDB.getPolicyTransferId());
			policyTransferEntities.add(policyTransferFromDB);
		}

		return policyTransferEntities;
	}

	@Override
	public void deleteByPolicyAndCoverage(String policy, int coverage) {
		for (PolicyTransferEntity policyTransferEntity : policyTransferRepository.findByPolicyAndCoverage(policy, coverage)) {
			policyTransferRepository.delete(policyTransferEntity);
		}
	}

}
