package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.InsuredLiteDTO;
import lu.wealins.liability.services.core.business.InsuredService;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipMapper;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

@Service
public class InsuredServiceImpl implements InsuredService {

	@Autowired
	private CliPolRelationshipMapper cliPolRelationshipMapper;

	@Override
	public Collection<InsuredDTO> getInsureds(PolicyEntity policy) {

		return cliPolRelationshipMapper.asInsuredDTOs(policy);
	}

	@Override
	public Collection<InsuredDTO> getDeadInsureds(Collection<CliPolRelationshipEntity> cliPolRelationships) {
		return cliPolRelationshipMapper.asDeadInsuredDTOs(cliPolRelationships);
	}

	@Override
	public Collection<InsuredLiteDTO> getInsuredLites(PolicyEntity policy) {

		return cliPolRelationshipMapper.asInsuredLiteDTOs(policy);
	}

}
