package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.mapper.ClientFormMapper;
import lu.wealins.webia.services.core.mapper.InsuredMapper;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.webia.services.core.persistence.repository.ClientFormRepository;
import lu.wealins.webia.services.core.predicates.ClientRelationTypePredicate;
import lu.wealins.webia.services.core.service.ClientFormService;
import lu.wealins.webia.services.core.service.InsuredService;
import lu.wealins.common.dto.webia.services.InsuredFormDTO;

@Service
public class InsuredServiceImpl implements InsuredService {

	private static final ClientRelationTypePredicate INSURED_1_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.LIFE_ASSURED);
	private static final ClientRelationTypePredicate INSURED_2_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.JNT_LIFE_ASSURED);
	private static final ClientRelationTypePredicate INSURED_3_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.ADDN_LIFE_ASSURED);

	@Autowired
	private InsuredMapper insuredMapper;

	@Autowired
	private ClientFormService clientFormService;

	@Autowired
	private ClientFormRepository clientFormRepository;

	@Autowired
	private ClientFormMapper clientFormMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.InsuredService#getInsureds(java.util.Collection)
	 */
	@Override
	public Collection<InsuredFormDTO> getInsureds(Collection<ClientFormEntity> clientForms) {
		Collection<InsuredFormDTO> insureds = new ArrayList<>();

		CollectionUtils.addAll(insureds, getInsureds(clientForms, INSURED_1_PREDICATE));
		CollectionUtils.addAll(insureds, getInsureds(clientForms, INSURED_2_PREDICATE));
		CollectionUtils.addAll(insureds, getInsureds(clientForms, INSURED_3_PREDICATE));

		return insureds;
	}

	@Override
	public Collection<ClientFormEntity> update(Collection<InsuredFormDTO> insureds, Integer formId) {

		insureds.forEach(x -> x.setFormId(formId));
		return update(insureds);
	}

	private Collection<ClientFormEntity> update(Collection<InsuredFormDTO> insureds) {
		Collection<ClientFormEntity> insuredsClientForms = new ArrayList<>();

		for (InsuredFormDTO insured : insureds) {
			Collection<ClientFormEntity> clientForms = clientFormMapper.asClientFormEntities(insured);
			insuredsClientForms.addAll(clientFormRepository.save(clientForms));
		}

		return insuredsClientForms;
	}

	private Collection<InsuredFormDTO> getInsureds(Collection<ClientFormEntity> clientForms, ClientRelationTypePredicate predicate) {

		Collection<ClientFormEntity> insureds = CollectionUtils.select(clientForms, predicate);
		if (CollectionUtils.isEmpty(insureds)) {
			return new ArrayList<>();
		}

		Collection<InsuredFormDTO> insuredDTOs = new ArrayList<>();
		for (ClientFormEntity insured : insureds) {
			InsuredFormDTO insuredDTO = insuredMapper.asInsuredDTO(insured);

			insuredDTO.setEconomicBeneficiary(isEconomicBeneficiary(insured));

			insuredDTOs.add(insuredDTO);
		}

		return insuredDTOs;
	}

	private Boolean isEconomicBeneficiary(ClientFormEntity insured) {

		Integer clientId = insured.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}

		return clientFormService.hasRelation(insured.getFormId(), clientId, ClientRelationType.ECONOMICAL_BENEFICIARY);
	}

}
