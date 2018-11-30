package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.OtherClientService;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipMapper;
import lu.wealins.liability.services.core.mapper.CliPolRelationshipWithClientRoleActivationMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.OtherClientLiteDTO;

@Service
public class OtherClientServiceImpl implements OtherClientService {

	@Autowired
	private CliPolRelationshipMapper cliPolRelationshipMapper;

	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;

	@Autowired
	private CliPolRelationshipWithClientRoleActivationMapper cliPolRelationshipWithClientRoleActivationMapper;

	@Override
	public Collection<OtherClientDTO> getOtherClients(PolicyEntity policy) {
		return cliPolRelationshipMapper.asOtherClientDTOs(policy);
	}

	@Override
	public Collection<OtherClientLiteDTO> getOtherClientLites(PolicyEntity policy) {
		return cliPolRelationshipMapper.asOtherClientLiteDTOs(policy);
	}

	@Override
	public Collection<OtherClientDTO> getOtherClients(PolicyEntity policy, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return cliPolRelationshipWithClientRoleActivationMapper.asOtherClientDTOs(policy, clientRoleActivationFlag);
	}

	@Override
	public Collection<OtherClientDTO> getOtherClients(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return cliPolRelationshipWithClientRoleActivationMapper.asOtherClientDTOs(workflowItemId, clientRoleActivationFlag);
	}

	@Override
	public void updateOtherClients(Collection<OtherClientDTO> clients, String policyId, Date activeDate, String workflowItemId) {
		if (CollectionUtils.isNotEmpty(clients)) {
			clients.forEach(client -> cliPolRelationshipService.saveOtherClient(client, policyId, activeDate, workflowItemId));
		}
	}
}
