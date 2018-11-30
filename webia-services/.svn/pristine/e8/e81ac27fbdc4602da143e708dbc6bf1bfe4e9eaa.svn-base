package lu.wealins.webia.services.core.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.ClientFormDTO;
import lu.wealins.common.dto.webia.services.InsuredFormDTO;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;

@Mapper(componentModel = "spring")
public interface ClientFormMapper {
	ClientFormEntity asClientFormEntity(ClientFormDTO in);

	Collection<ClientFormEntity> asClientFormEntities(Collection<ClientFormDTO> in);

	ClientFormEntity asClientFormEntity(BeneficiaryFormDTO in);

	ClientFormEntity asClientFormEntity(InsuredFormDTO in);

	ClientFormEntity asClientFormEntity(PolicyHolderFormDTO in);

	ClientFormDTO asClientFormDTO(ClientFormEntity in);

	Collection<ClientFormDTO> asClientFormDTOs(Collection<ClientFormEntity> in);

	default Collection<ClientFormEntity> asClientFormEntities(PolicyHolderFormDTO policyHolder) {

		Collection<ClientFormEntity> clients = new ArrayList<>();

		CollectionUtils.addIgnoreNull(clients, asClientFormEntity(policyHolder));

		if (BooleanUtils.isTrue(policyHolder.getUsufructuary())) {
			ClientFormEntity relation = asClientFormEntity(policyHolder);
			relation.setClientRelationTp(ClientRelationType.USUFRUCTUARY.getValue());
			clients.add(relation);
		}
		if (BooleanUtils.isTrue(policyHolder.getBareOwner())) {
			ClientFormEntity relation = asClientFormEntity(policyHolder);
			relation.setClientRelationTp(ClientRelationType.BARE_OWNER.getValue());
			clients.add(relation);
		}
		if (BooleanUtils.isTrue(policyHolder.getDeathSuccessor())) {
			ClientFormEntity relation = asClientFormEntity(policyHolder);
			relation.setClientRelationTp(ClientRelationType.SUCCESSION_DEATH.getValue());
			clients.add(relation);
		}
		if (BooleanUtils.isTrue(policyHolder.getLifeSuccessor())) {
			ClientFormEntity relation = asClientFormEntity(policyHolder);
			relation.setClientRelationTp(ClientRelationType.SUCCESSION_LIFE.getValue());
			clients.add(relation);
		}
		return clients;
	}

	default Collection<ClientFormEntity> asClientFormEntities(InsuredFormDTO insured) {

		Collection<ClientFormEntity> clients = new ArrayList<>();

		CollectionUtils.addIgnoreNull(clients, asClientFormEntity(insured));

		if (BooleanUtils.isTrue(insured.getEconomicBeneficiary())) {
			ClientFormEntity relation = asClientFormEntity(insured);
			relation.setClientRelationTp(ClientRelationType.ECONOMICAL_BENEFICIARY.getValue());
			clients.add(relation);
		}

		return clients;
	}

	default Collection<ClientFormEntity> asClientFormEntities(BeneficiaryFormDTO beneficiary) {

		Collection<ClientFormEntity> clients = new ArrayList<>();

		CollectionUtils.addIgnoreNull(clients, asClientFormEntity(beneficiary));

		if (BooleanUtils.isTrue(beneficiary.getUsufructuary())) {
			ClientFormEntity relation = asClientFormEntity(beneficiary);
			relation.setClientRelationTp(ClientRelationType.BENEFICIARY_USUFRUCTUARY.getValue());
			clients.add(relation);
		}
		if (BooleanUtils.isTrue(beneficiary.getBareOwner())) {
			ClientFormEntity relation = asClientFormEntity(beneficiary);
			relation.setClientRelationTp(ClientRelationType.BENEFICIARY_BARE_OWNER.getValue());
			clients.add(relation);
		}

		if (BooleanUtils.isTrue(beneficiary.getIrrevocable())) {
			ClientFormEntity relation = asClientFormEntity(beneficiary);
			relation.setClientRelationTp(ClientRelationType.IRREVOCABLE_BEN.getValue());
			clients.add(relation);
		}
		if (BooleanUtils.isTrue(beneficiary.getSeparatePropertyNoRights())) {
			ClientFormEntity relation = asClientFormEntity(beneficiary);
			relation.setClientRelationTp(ClientRelationType.SEPARATE_PROPERTY_NO_RIGHTS.getValue());
			clients.add(relation);
		}
		if (BooleanUtils.isTrue(beneficiary.getSeparatePropertyRights())) {
			ClientFormEntity relation = asClientFormEntity(beneficiary);
			relation.setClientRelationTp(ClientRelationType.SEPARATE_PROPERTY_RIGHTS.getValue());
			clients.add(relation);
		}
		if (BooleanUtils.isTrue(beneficiary.getAcceptant())) {
			ClientFormEntity relation = asClientFormEntity(beneficiary);
			relation.setClientRelationTp(ClientRelationType.ACCEPTANT.getValue());
			clients.add(relation);
		}

		return clients;
	}

}
