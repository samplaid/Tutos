package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.mapper.ClientFormMapper;
import lu.wealins.webia.services.core.mapper.PolicyHolderMapper;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.webia.services.core.persistence.repository.ClientFormRepository;
import lu.wealins.webia.services.core.predicates.ClientRelationTypePredicate;
import lu.wealins.webia.services.core.service.ClientFormService;
import lu.wealins.webia.services.core.service.PolicyHolderService;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;

@Service
public class PolicyHolderServiceImpl implements PolicyHolderService {

	private static final ClientRelationTypePredicate POLICY_HOLDER_1_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.OWNER);
	private static final ClientRelationTypePredicate POLICY_HOLDER_2_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.JOINT_OWNER);
	private static final ClientRelationTypePredicate POLICY_HOLDER_3_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.ADDN_OWNER);

	@Autowired
	private PolicyHolderMapper policyHolderMapper;

	@Autowired
	private ClientFormMapper clientFormMapper;

	@Autowired
	private ClientFormService clientFormService;

	@Autowired
	private ClientFormRepository clientFormRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.PolicyHolderService#getPolicyHolders(java.util.Collection)
	 */
	@Override
	public Collection<PolicyHolderFormDTO> getPolicyHolders(Collection<ClientFormEntity> clientForms) {
		Collection<PolicyHolderFormDTO> policyHolders = new ArrayList<>();

		CollectionUtils.addAll(policyHolders, getPolicyHolders(clientForms, POLICY_HOLDER_1_PREDICATE));
		CollectionUtils.addAll(policyHolders, getPolicyHolders(clientForms, POLICY_HOLDER_2_PREDICATE));
		CollectionUtils.addAll(policyHolders, getPolicyHolders(clientForms, POLICY_HOLDER_3_PREDICATE));

		return policyHolders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.PolicyHolderService#update(java.util.Collection, java.lang.Integer)
	 */
	@Override
	public Collection<ClientFormEntity> update(Collection<PolicyHolderFormDTO> policyHolders, Integer formId) {
		policyHolders.forEach(x -> x.setFormId(formId));

		return update(policyHolders);
	}

	private Collection<ClientFormEntity> update(Collection<PolicyHolderFormDTO> policyHolders) {

		Collection<ClientFormEntity> policyHoldersClientForms = new ArrayList<>();

		for (PolicyHolderFormDTO policyHolder : policyHolders) {
			Collection<ClientFormEntity> clientForms = clientFormMapper.asClientFormEntities(policyHolder);
			policyHoldersClientForms.addAll(clientFormRepository.save(clientForms));
		}

		return policyHoldersClientForms;
	}

	private Collection<PolicyHolderFormDTO> getPolicyHolders(Collection<ClientFormEntity> clientForms, ClientRelationTypePredicate predicate) {

		Collection<ClientFormEntity> policyHolders = CollectionUtils.select(clientForms, predicate);
		if (CollectionUtils.isEmpty(policyHolders)) {
			return new ArrayList<>();
		}

		Collection<PolicyHolderFormDTO> policyHolderDTOs = new ArrayList<>();
		for (ClientFormEntity policyHolder : policyHolders) {
			PolicyHolderFormDTO policyHolderDTO = policyHolderMapper.asPolicyHolderDTO(policyHolder);

			policyHolderDTO.setUsufructuary(isUsufructuary(policyHolder));
			policyHolderDTO.setBareOwner(isBareOwner(policyHolder));
			policyHolderDTO.setDeathSuccessor(isDeathSuccessor(policyHolder));
			policyHolderDTO.setLifeSuccessor(isLifeSuccessor(policyHolder));

			policyHolderDTOs.add(policyHolderDTO);
		}

		return policyHolderDTOs;
	}

	private Boolean isUsufructuary(ClientFormEntity policyHolder) {

		Integer clientId = policyHolder.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}

		return clientFormService.hasRelation(policyHolder.getFormId(), clientId, ClientRelationType.USUFRUCTUARY);
	}

	private Boolean isBareOwner(ClientFormEntity policyHolder) {
		Integer clientId = policyHolder.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}

		return clientFormService.hasRelation(policyHolder.getFormId(), clientId, ClientRelationType.BARE_OWNER);
	}

	private Boolean isDeathSuccessor(ClientFormEntity policyHolder) {
		Integer clientId = policyHolder.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}

		return clientFormService.hasRelation(policyHolder.getFormId(), clientId, ClientRelationType.SUCCESSION_DEATH);
	}

	private Boolean isLifeSuccessor(ClientFormEntity policyHolder) {
		Integer clientId = policyHolder.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}

		return clientFormService.hasRelation(policyHolder.getFormId(), clientId, ClientRelationType.SUCCESSION_LIFE);
	}
}
