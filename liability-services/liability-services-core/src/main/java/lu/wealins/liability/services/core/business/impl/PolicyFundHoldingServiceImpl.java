package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyFundHoldingDTO;
import lu.wealins.liability.services.core.business.PolicyFundHoldingService;
import lu.wealins.liability.services.core.mapper.PolicyFundHoldingMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyFundHoldingEntity;
import lu.wealins.liability.services.core.persistence.repository.PolicyFundHoldingRepository;

@Service
public class PolicyFundHoldingServiceImpl implements PolicyFundHoldingService {

	@Autowired
	PolicyFundHoldingMapper policyFundHoldingMappaer;

	@Autowired
	PolicyFundHoldingRepository policyFundHoldingRepo;

	@Override
	public Collection<PolicyFundHoldingDTO> getHoldingsByPolicy(String policy) {

		List<PolicyFundHoldingEntity> entities = policyFundHoldingRepo.findByPolicy(policy);

		return policyFundHoldingMappaer.asPolicyFundHoldingDTOs(entities);
	}

}
