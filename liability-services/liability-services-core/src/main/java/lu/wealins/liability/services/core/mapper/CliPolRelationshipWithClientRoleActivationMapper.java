package lu.wealins.liability.services.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.repository.CliPolRelationshipRepository;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.liability.services.core.utils.constantes.Constantes;
import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class CliPolRelationshipWithClientRoleActivationMapper /* extends CliPolRelationshipMapper */ {

	@Autowired
	private CliPolRelationshipMapper cliPolRelationshipMapper;
	@Autowired
	private CliPolRelationshipRepository cliPolRelationshipRepository;
	@Autowired
	protected ClientService clientService;
	@Autowired
	private ClientMapper clientMapper;
	@Autowired
	private CliPolRelationshipService cliPolService;

	public Collection<BeneficiaryDTO> asDeathBeneficiaryDTOs(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		Collection<CliPolRelationshipEntity> cliPolRelationships = cliPolRelationshipRepository.findAllByModifyProcess(workflowItemId).stream()
				.filter(x -> x.getType() == CliPolRelationshipType.BENEFICIARY_AT_DEATH.getValue() && x.getClientId() != Constantes.FAKE_CLIENT_ID).collect(Collectors.toList());

		return asBeneficiaryDTOs(cliPolRelationships, clientRoleActivationFlag, workflowItemId);
	}

	public Collection<BeneficiaryDTO> asLifeBeneficiaryDTOs(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		Collection<CliPolRelationshipEntity> cliPolRelationships = cliPolRelationshipRepository.findAllByModifyProcess(workflowItemId).stream()
				.filter(x -> x.getType() == CliPolRelationshipType.BENEFICIARY_AT_MATURITY.getValue() && x.getClientId() != Constantes.FAKE_CLIENT_ID).collect(Collectors.toList());

		return asBeneficiaryDTOs(cliPolRelationships, clientRoleActivationFlag, workflowItemId);
	}

	public Collection<BeneficiaryDTO> asDeathBeneficiaryDTOs(PolicyEntity in, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return asBeneficiaryDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_AT_DEATH), clientRoleActivationFlag, null);
	}

	public Collection<BeneficiaryDTO> asLifeBeneficiaryDTOs(PolicyEntity in, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		return asBeneficiaryDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_AT_MATURITY), clientRoleActivationFlag,
				null);
	}

	private Collection<BeneficiaryDTO> asBeneficiaryDTOs(Collection<CliPolRelationshipEntity> benficiariesRelationship, ClientRoleActivationFlagDTO clientRoleActivationFlag, String workflowItemId) {
		if (CollectionUtils.isEmpty(benficiariesRelationship)) {
			return new ArrayList<>();
		}

		Collection<BeneficiaryDTO> benficiaries = new HashSet<>();

		for (CliPolRelationshipEntity cliPolRelationship : benficiariesRelationship) {
			BeneficiaryDTO beneficiary = asBeneficiaryDTOWithClientRoleActivation(cliPolRelationship, clientRoleActivationFlag, workflowItemId);
			clientMapper.asClientDTO(cliPolRelationship.getClient(), beneficiary);

			benficiaries.add(beneficiary);
		}

		return cliPolRelationshipMapper.sortBeneficiaries(benficiaries);
	}

	public BeneficiaryDTO asBeneficiaryDTOWithClientRoleActivation(CliPolRelationshipEntity beneficiaryRelationship, ClientRoleActivationFlagDTO clientRoleActivationFlag, String workflowItemId) {
		BeneficiaryDTO beneficiary = cliPolRelationshipMapper.asBeneficiaryDTO(beneficiaryRelationship);
		
		if (clientRoleActivationFlag.isActivatedIrrevocable()) {
			beneficiary.setIrrevocable(cliPolRelationshipMapper.hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.IRREVOCABLE_BEN, workflowItemId));
		}
		if(clientRoleActivationFlag.isActivatedAcceptant()) {
			beneficiary.setAcceptant(cliPolRelationshipMapper.hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.ACCEPTANT, workflowItemId));
		}
		if (clientRoleActivationFlag.isActivatedSeparatePropertyRights()) {
			beneficiary.setSeparatePropertyRights(cliPolRelationshipMapper.hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.SEPARATE_PROPERTY_RIGHTS, workflowItemId));
		}
		if (clientRoleActivationFlag.isActivatedSeparatePropertyNoRights()) {
			beneficiary.setSeparatePropertyNoRights(cliPolRelationshipMapper.hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.SEPARATE_PROPERTY_NO_RIGHTS, workflowItemId));
		}
		if (clientRoleActivationFlag.isActivatedBenefUsuFructary()) {
			beneficiary.setUsufructuary(cliPolRelationshipMapper.hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY, workflowItemId));
		}
		if (clientRoleActivationFlag.isActivatedBenefBareOwner()) {
			beneficiary.setBareOwner(cliPolRelationshipMapper.hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.BENEFICIARY_BARE_OWNER, workflowItemId));
		}

		return beneficiary;
	}

	public List<CliPolRelationshipType> getBeneficiaryCliPolRelationshipTypes(ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		List<CliPolRelationshipType> cliPolRelationshipTypes = new ArrayList<>();

		if (clientRoleActivationFlag.isActivatedIrrevocable()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.IRREVOCABLE_BEN);
		}
		if (clientRoleActivationFlag.isActivatedAcceptant()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.ACCEPTANT);
		}
		if (clientRoleActivationFlag.isActivatedSeparatePropertyRights()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.SEPARATE_PROPERTY_RIGHTS);
		}
		if (clientRoleActivationFlag.isActivatedSeparatePropertyNoRights()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.SEPARATE_PROPERTY_NO_RIGHTS);
		}
		if (clientRoleActivationFlag.isActivatedBenefUsuFructary()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY);
		}
		if (clientRoleActivationFlag.isActivatedBenefBareOwner()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.BENEFICIARY_BARE_OWNER);
		}
		if (clientRoleActivationFlag.isActivatedDeathBeneficiary()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.BENEFICIARY_AT_DEATH);
		}
		if (clientRoleActivationFlag.isActivatedLifeBeneficiary()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.BENEFICIARY_AT_MATURITY);
		}

		return cliPolRelationshipTypes;
	}

	public List<CliPolRelationshipType> getPolicyHolderCliPolRelationshipTypes(ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		List<CliPolRelationshipType> cliPolRelationshipTypes = new ArrayList<>();

		if (clientRoleActivationFlag.isActivatedSuccessionDeath()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.SUCCESSION_DEATH);
		}
		if (clientRoleActivationFlag.isActivatedSuccessionLife()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.SUCCESSION_LIFE);
		}
		if (clientRoleActivationFlag.isActivatedUsufructuary()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.USUFRUCTUARY);
		}
		if (clientRoleActivationFlag.isActivatedBareOwner()) {
			cliPolRelationshipTypes.add(CliPolRelationshipType.BARE_OWNER);
		}

		return cliPolRelationshipTypes;
	}

	public Collection<OtherClientDTO> asOtherClientDTOs(PolicyEntity policy, ClientRoleActivationFlagDTO clientRoleActivationFlag) {
		
		return cliPolRelationshipMapper.asOtherClientDTOs(policy, getPolicyHolderCliPolRelationshipTypes(clientRoleActivationFlag),
				getBeneficiaryCliPolRelationshipTypes(clientRoleActivationFlag),
				CliPolRelationshipType.INSURED_RELATIONSHIP_TYPE_GROUP);

	}

	public Collection<OtherClientDTO> asOtherClientDTOs(String workflowItemId, ClientRoleActivationFlagDTO clientRoleActivationFlag) {

		return cliPolRelationshipMapper.asOtherClientDTOs(workflowItemId, getPolicyHolderCliPolRelationshipTypes(clientRoleActivationFlag),
				getBeneficiaryCliPolRelationshipTypes(clientRoleActivationFlag),
				CliPolRelationshipType.INSURED_RELATIONSHIP_TYPE_GROUP);

	}

}
