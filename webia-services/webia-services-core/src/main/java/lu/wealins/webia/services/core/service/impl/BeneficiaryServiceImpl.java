package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.mapper.BeneficiaryMapper;
import lu.wealins.webia.services.core.mapper.ClientFormMapper;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.webia.services.core.persistence.repository.ClientFormRepository;
import lu.wealins.webia.services.core.predicates.ClientRelationTypePredicate;
import lu.wealins.webia.services.core.service.BeneficiaryService;
import lu.wealins.webia.services.core.service.ClientFormService;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	private static final ClientRelationTypePredicate BENEFICIARY_AT_MATURITY_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.BENEFICIARY_AT_MATURITY);
	private static final ClientRelationTypePredicate BENEFICIARY_AT_DEATH_PREDICATE = new ClientRelationTypePredicate(ClientRelationType.BENEFICIARY_AT_DEATH);

	@Autowired
	private BeneficiaryMapper beneficiaryMapper;

	@Autowired
	private ClientFormService clientFormService;

	@Autowired
	private ClientFormRepository clientFormRepository;

	@Autowired
	private ClientFormMapper clientFormMapper;

	@Override
	public Collection<BeneficiaryFormDTO> getLifeBeneficiaries(Collection<ClientFormEntity> clientForms) {
		return getBeneficiaries(clientForms, BENEFICIARY_AT_MATURITY_PREDICATE);
	}

	@Override
	public Collection<BeneficiaryFormDTO> getDeathBeneficiaries(Collection<ClientFormEntity> clientForms) {
		return getBeneficiaries(clientForms, BENEFICIARY_AT_DEATH_PREDICATE);
	}

	@Override
	public Collection<ClientFormEntity> updateBeneficiaries(Collection<BeneficiaryFormDTO> beneficiaries, Integer formId) {
		beneficiaries.forEach(x -> x.setFormId(formId));
		return update(beneficiaries);
	}

	private Collection<ClientFormEntity> update(Collection<BeneficiaryFormDTO> beneficiaries) {
		Collection<ClientFormEntity> beneficiariesClientForms = new ArrayList<>();

		for (BeneficiaryFormDTO beneficiary : beneficiaries) {
			Collection<ClientFormEntity> clientForms = clientFormMapper.asClientFormEntities(beneficiary);
			beneficiariesClientForms.addAll(clientFormRepository.save(clientForms));
		}

		return beneficiariesClientForms;
	}

	private Collection<BeneficiaryFormDTO> getBeneficiaries(Collection<ClientFormEntity> clientForms, ClientRelationTypePredicate predicate) {

		Collection<ClientFormEntity> beneficiarys = CollectionUtils.select(clientForms, predicate);
		if (CollectionUtils.isEmpty(beneficiarys)) {
			return new ArrayList<>();
		}

		Collection<BeneficiaryFormDTO> beneficiaryDTOs = new ArrayList<>();
		for (ClientFormEntity beneficiary : beneficiarys) {
			BeneficiaryFormDTO beneficiaryDTO = beneficiaryMapper.asBeneficiaryDTO(beneficiary);
			beneficiaryDTO.setUsufructuary(isUsufructuary(beneficiary));
			beneficiaryDTO.setBareOwner(isBareOwner(beneficiary));
			beneficiaryDTO.setIrrevocable(isIrrevocable(beneficiary));
			beneficiaryDTO.setSeparatePropertyRights(isSeparatePropertyRights(beneficiary));
			beneficiaryDTO.setSeparatePropertyNoRights(isSeparatePropertyNoRights(beneficiary));
			beneficiaryDTO.setAcceptant(isAcceptant(beneficiary));
			
			beneficiaryDTOs.add(beneficiaryDTO);
		}

		return beneficiaryDTOs;
	}

	private Boolean isBareOwner(ClientFormEntity beneficiary) {
		Integer clientId = beneficiary.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}
		return clientFormService.hasRelation(beneficiary.getFormId(), clientId, ClientRelationType.BENEFICIARY_BARE_OWNER);
	}

	private Boolean isUsufructuary(ClientFormEntity beneficiary) {
		Integer clientId = beneficiary.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}
		return clientFormService.hasRelation(beneficiary.getFormId(), clientId, ClientRelationType.BENEFICIARY_USUFRUCTUARY);
	}

	private Boolean isIrrevocable(ClientFormEntity beneficiary) {
		Integer clientId = beneficiary.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}
		return clientFormService.hasRelation(beneficiary.getFormId(), clientId, ClientRelationType.IRREVOCABLE_BEN);
	}

	private Boolean isAcceptant(ClientFormEntity beneficiary) {
		Integer clientId = beneficiary.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}
		return clientFormService.hasRelation(beneficiary.getFormId(), clientId, ClientRelationType.ACCEPTANT);
	}

	private Boolean isSeparatePropertyRights(ClientFormEntity beneficiary) {
		Integer clientId = beneficiary.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}
		return clientFormService.hasRelation(beneficiary.getFormId(), clientId, ClientRelationType.SEPARATE_PROPERTY_RIGHTS);
	}

	private Boolean isSeparatePropertyNoRights(ClientFormEntity beneficiary) {
		Integer clientId = beneficiary.getClientId();
		if (clientId == null) {
			return Boolean.FALSE;
		}
		return clientFormService.hasRelation(beneficiary.getFormId(), clientId, ClientRelationType.SEPARATE_PROPERTY_NO_RIGHTS);
	}

}
