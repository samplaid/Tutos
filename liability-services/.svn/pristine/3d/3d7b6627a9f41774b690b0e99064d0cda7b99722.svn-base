package lu.wealins.liability.services.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.BeneficiaryDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryLiteDTO;
import lu.wealins.common.dto.liability.services.CliPolRelationshipDTO;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLiteDTO;
import lu.wealins.common.dto.liability.services.CommonBeneficiary;
import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.InsuredLiteDTO;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.OtherClientLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderLiteDTO;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.business.OptionDetailService;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.repository.CliPolRelationshipRepository;
import lu.wealins.liability.services.core.persistence.repository.ClientRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.liability.services.core.utils.constantes.Constantes;
import lu.wealins.liability.services.core.utils.predicate.CliPolRelationshipPredicate;


@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class CliPolRelationshipMapper {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private CliPolRelationshipRepository cliPolRelationshipRepository;
	@Autowired
	private CalendarUtils calendarUtils;
	@Autowired
	protected ClientMapper clientMapper;
	@Autowired
	protected ClientService clientService;
	@Autowired
	private CliPolRelationshipService cliPolService;
	@Autowired
	private OptionDetailService optionDetailService;

	@Mappings({
			// @Mapping(ignore = true, target = "percentageSplit"), // percentageSplit split must be saved only on BENEFICIARY_AT_DEATH or BENEFICIARY_AT_MATURITY
			@Mapping(source = "cliId", target = "clientId"),
	})
	public abstract CliPolRelationshipEntity asCliPolRelationshipEntity(ClientDTO in);

	@Mappings({
			@Mapping(source = "client.cliId", target = "client"),
			@Mapping(source = "policy.polId", target = "policy")
	})
	public abstract CliPolRelationshipDTO asCliPolRelationshipDTO(CliPolRelationshipEntity cliPolRelationshipEntity);

	public abstract Collection<CliPolRelationshipDTO> asCliPolRelationshipDTOs(Collection<CliPolRelationshipEntity> cliPolRelationshipEntities);

	public Collection<CliPolRelationshipEntity> asCliPolRelationshipEntities(PolicyHolderDTO in, String policyId, Date paymentDt,
			@MappingTarget Collection<CliPolRelationshipEntity> out, boolean isNew) {

		int rank = in.getTypeNumber().intValue();
		CliPolRelationshipType cliPolRelationshipType = rank == 1 ? CliPolRelationshipType.OWNER : rank == 2 ? CliPolRelationshipType.JOINT_OWNER : CliPolRelationshipType.ADDN_OWNER;

		CliPolRelationshipEntity cliPolRelationshipEntity = asCliPolRelationshipEntity(in, cliPolRelationshipType, policyId, paymentDt, isNew);

		out.add(cliPolRelationshipEntity);

		if (BooleanUtils.isTrue(in.getUsufructuary())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.USUFRUCTUARY, policyId, paymentDt, isNew));
		}

		if (BooleanUtils.isTrue(in.getBareOwner())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.BARE_OWNER, policyId, paymentDt, isNew));
		}

		if (BooleanUtils.isTrue(in.getDeathSuccessor())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.SUCCESSION_DEATH, policyId, paymentDt, isNew));
		}

		if (BooleanUtils.isTrue(in.getLifeSuccessor())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.SUCCESSION_LIFE, policyId, paymentDt, isNew));
		}

		return out;

	}

	public CliPolRelationshipEntity asCliPolRelationshipEntity(OtherClientDTO in, String policyId, Date paymentDt, boolean isNew) {

		CliPolRelationshipEntity cliPolRelationshipEntity = asCliPolRelationshipEntity(in, CliPolRelationshipType.toCliPolRelationshipType(in.getRoleNumber()), policyId, paymentDt, isNew);
		cliPolRelationshipEntity.setPercentageSplit(in.getPercentageSplit());

		return cliPolRelationshipEntity;
	}

	public Collection<CliPolRelationshipEntity> asCliPolRelationshipEntities(BeneficiaryDTO in, CliPolRelationshipType cliPolRelationshipType, String policyId, Date activeDate,
			@MappingTarget Collection<CliPolRelationshipEntity> out, boolean isNew) {

		CliPolRelationshipEntity cliPolRelationshipEntity = asCliPolRelationshipEntity(in, cliPolRelationshipType, policyId, activeDate, isNew);
		cliPolRelationshipEntity.setPercentageSplit(in.getPercentageSplit());
		cliPolRelationshipEntity.setExEqualParts(in.getExEqualParts());

		out.add(cliPolRelationshipEntity);

		if (BooleanUtils.isTrue(in.getIrrevocable())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.IRREVOCABLE_BEN, policyId, activeDate, isNew));
		}

		if (BooleanUtils.isTrue(in.getAcceptant())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.ACCEPTANT, policyId, activeDate, isNew));
		}

		if (BooleanUtils.isTrue(in.getSeparatePropertyRights())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.SEPARATE_PROPERTY_RIGHTS, policyId, activeDate, isNew));
		}

		if (BooleanUtils.isTrue(in.getSeparatePropertyNoRights())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.SEPARATE_PROPERTY_NO_RIGHTS, policyId, activeDate, isNew));
		}

		if (BooleanUtils.isTrue(in.getUsufructuary())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY, policyId, activeDate, isNew));
		}

		if (BooleanUtils.isTrue(in.getBareOwner())) {
			out.add(asCliPolRelationshipEntity(in, CliPolRelationshipType.BENEFICIARY_BARE_OWNER, policyId, activeDate, isNew));
		}

		return out;
	}

	public CliPolRelationshipEntity asCliPolRelationshipEntity(ClientDTO in, CliPolRelationshipType cliPolRelationshipType, String policyId, Date activeDate, boolean isNew) {
		Assert.notNull(in);
		Assert.notNull(cliPolRelationshipType);
		Assert.notNull(policyId);

		CliPolRelationshipEntity cliPolRelationshipEntity = null;

		if (!isNew) {
			cliPolRelationshipEntity = cliPolRelationshipRepository.findByPolicyIdAndTypeAndTypeNumberAndCoverageAndClientId(policyId,
					cliPolRelationshipType.getValue(), in.getTypeNumber(), Integer.valueOf(0), in.getCliId().intValue());
		}

		if (cliPolRelationshipEntity == null) {
			cliPolRelationshipEntity = asCliPolRelationshipEntity(in);
			cliPolRelationshipEntity.setType(cliPolRelationshipType.getValue());
			cliPolRelationshipEntity.setPolicyId(policyId);
			cliPolRelationshipEntity.setClient(clientRepository.findOne(in.getCliId()));
			cliPolRelationshipEntity.setPolicy(policyRepository.findOne(policyId));
			cliPolRelationshipEntity.setTypeNumber(in.getTypeNumber());
			cliPolRelationshipEntity.setCoverage(Integer.valueOf(0));
			cliPolRelationshipEntity.setStatus(1);
			cliPolRelationshipEntity.setActiveDate(activeDate);
			cliPolRelationshipEntity.setCprId(generateCprId(cliPolRelationshipEntity));
		}

		cliPolRelationshipEntity.setEndDate(calendarUtils.createDefaultDate());

		return cliPolRelationshipEntity;
	}

	public abstract BeneficiaryDTO asBeneficiaryDTO(CliPolRelationshipEntity in);

	@AfterMapping
	public BeneficiaryDTO afterEntityMapping(CliPolRelationshipEntity beneficiaryRelationship, @MappingTarget BeneficiaryDTO beneficiary) {

		beneficiary.setIrrevocable(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.IRREVOCABLE_BEN));
		beneficiary.setAcceptant(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.ACCEPTANT));
		beneficiary.setSeparatePropertyRights(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.SEPARATE_PROPERTY_RIGHTS));
		beneficiary.setSeparatePropertyNoRights(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.SEPARATE_PROPERTY_NO_RIGHTS));
		beneficiary.setUsufructuary(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY));
		beneficiary.setBareOwner(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.BENEFICIARY_BARE_OWNER));

		return beneficiary;
	}

	public PolicyHolderDTO asPolicyHolderDTO(CliPolRelationshipEntity policyHolderRelationship, String workflowItemId) {
		PolicyHolderDTO policyHolder = asPolicyHolderDTO(policyHolderRelationship);

		policyHolder.setUsufructuary(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.USUFRUCTUARY, workflowItemId));
		policyHolder.setBareOwner(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.BARE_OWNER, workflowItemId));
		policyHolder.setDeathSuccessor(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.SUCCESSION_DEATH, workflowItemId));
		policyHolder.setLifeSuccessor(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.SUCCESSION_LIFE, workflowItemId));

		return policyHolder;
	}

	public abstract BeneficiaryLiteDTO asBeneficiaryLiteDTO(CliPolRelationshipEntity in);

	@AfterMapping
	public BeneficiaryLiteDTO afterEntityMapping(CliPolRelationshipEntity beneficiaryRelationship, @MappingTarget BeneficiaryLiteDTO beneficiary) {

		beneficiary.setIrrevocable(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.IRREVOCABLE_BEN));
		beneficiary.setAcceptant(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.ACCEPTANT));
		beneficiary.setSeparatePropertyRights(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.SEPARATE_PROPERTY_RIGHTS));
		beneficiary.setSeparatePropertyNoRights(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.SEPARATE_PROPERTY_NO_RIGHTS));
		beneficiary.setUsufructuary(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY));
		beneficiary.setBareOwner(hasCliPolRelationship(beneficiaryRelationship, CliPolRelationshipType.BENEFICIARY_BARE_OWNER));

		return beneficiary;
	}

	public abstract PolicyHolderDTO asPolicyHolderDTO(CliPolRelationshipEntity in);

	@AfterMapping
	public PolicyHolderDTO afterEntityMapping(CliPolRelationshipEntity policyHolderRelationship, @MappingTarget PolicyHolderDTO policyHolder) {

		policyHolder.setUsufructuary(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.USUFRUCTUARY));
		policyHolder.setBareOwner(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.BARE_OWNER));
		policyHolder.setDeathSuccessor(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.SUCCESSION_DEATH));
		policyHolder.setLifeSuccessor(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.SUCCESSION_LIFE));
		
		policyHolder.setDisplayNumber(generateDisplayNumber(policyHolderRelationship));

		return policyHolder;
	}

	public abstract PolicyHolderLiteDTO asPolicyHolderLiteDTO(CliPolRelationshipEntity in);

	@AfterMapping
	public PolicyHolderLiteDTO afterEntityMapping(CliPolRelationshipEntity policyHolderRelationship, @MappingTarget PolicyHolderLiteDTO policyHolder) {

		policyHolder.setUsufructuary(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.USUFRUCTUARY));
		policyHolder.setBareOwner(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.BARE_OWNER));
		policyHolder.setDeathSuccessor(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.SUCCESSION_DEATH));
		policyHolder.setLifeSuccessor(hasCliPolRelationship(policyHolderRelationship, CliPolRelationshipType.SUCCESSION_LIFE));

		return policyHolder;
	}

	public Collection<PolicyHolderDTO> asPolicyHolderDTOs(PolicyEntity policy) {

		Collection<PolicyHolderDTO> policyHolders = new ArrayList<>();
		Collection<CliPolRelationshipEntity> cliPolRelationships = policy.getCliPolRelationships();

		CollectionUtils.addAll(policyHolders, asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.OWNER))));
		CollectionUtils.addAll(policyHolders, asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.JOINT_OWNER))));
		CollectionUtils.addAll(policyHolders,
				clientService.distinctSortClients(asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.ADDN_OWNER)))));

		return policyHolders;
	}

	public Collection<PolicyHolderDTO> asDeadPolicyHolderDTOs(Collection<CliPolRelationshipEntity> cliPolRelationships) {

		Collection<PolicyHolderDTO> policyHolders = new ArrayList<>();

		CollectionUtils.addAll(policyHolders, asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndDeadPredicate(CliPolRelationshipType.OWNER))));
		CollectionUtils.addAll(policyHolders, asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndDeadPredicate(CliPolRelationshipType.JOINT_OWNER))));
		CollectionUtils.addAll(policyHolders,
				clientService.distinctSortClients(asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndDeadPredicate(CliPolRelationshipType.ADDN_OWNER)))));

		return policyHolders;
	}

	public Collection<PolicyHolderDTO> asPolicyHolderDTOs(String workflowItemId) {

		Collection<PolicyHolderDTO> policyHolders = new ArrayList<>();
		Collection<CliPolRelationshipEntity> cliPolRelationships = cliPolRelationshipRepository.findAllByModifyProcess(workflowItemId);

		CollectionUtils.addAll(policyHolders, asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndNotDeadPredicate(CliPolRelationshipType.OWNER)), workflowItemId));
		CollectionUtils.addAll(policyHolders, asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndNotDeadPredicate(CliPolRelationshipType.JOINT_OWNER)), workflowItemId));
		CollectionUtils.addAll(policyHolders,
				clientService.distinctSortClients(asPolicyHolderDTOs(CollectionUtils.select(cliPolRelationships, hasTypeAndNotDeadPredicate(CliPolRelationshipType.ADDN_OWNER)), workflowItemId)));

		return policyHolders;
	}

	public Collection<PolicyHolderLiteDTO> asPolicyHolderLiteDTOs(PolicyEntity policy) {

		Collection<PolicyHolderLiteDTO> policyHolders = new ArrayList<>();

		CollectionUtils.addAll(policyHolders, asPolicyHolderLiteDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.OWNER))));
		CollectionUtils.addAll(policyHolders, asPolicyHolderLiteDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.JOINT_OWNER))));
		CollectionUtils.addAll(policyHolders,
				clientService.distinctSortClients(asPolicyHolderLiteDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.ADDN_OWNER)))));

		return policyHolders;
	}

	public abstract InsuredDTO asInsuredDTO(CliPolRelationshipEntity in);

	@AfterMapping
	public InsuredDTO afterEntityMapping(CliPolRelationshipEntity insuredRelationship, @MappingTarget InsuredDTO insured) {

		insured.setEconomicBeneficiary(hasCliPolRelationship(insuredRelationship, CliPolRelationshipType.ECONOMICAL_BENEFICIARY));

		return insured;
	}

	public abstract InsuredLiteDTO asInsuredLiteDTO(CliPolRelationshipEntity in);

	@AfterMapping
	public InsuredLiteDTO afterEntityMapping(CliPolRelationshipEntity insuredRelationship, @MappingTarget InsuredLiteDTO insured) {

		insured.setEconomicBeneficiary(hasCliPolRelationship(insuredRelationship, CliPolRelationshipType.ECONOMICAL_BENEFICIARY));

		return insured;
	}

	private Collection<InsuredDTO> asInsuredDTOs(Collection<CliPolRelationshipEntity> cliPolRelationships) {
		ClientDTOMapper<InsuredDTO> mapper = x -> asInsuredDTO(x);

		return asClientDTOs(cliPolRelationships, mapper);
	}

	private Collection<InsuredLiteDTO> asInsuredLiteDTOs(Collection<CliPolRelationshipEntity> cliPolRelationships) {
		ClientLiteDTOMapper<InsuredLiteDTO> mapper = x -> asInsuredLiteDTO(x);

		return asClientLiteDTOs(cliPolRelationships, mapper);
	}

	public Collection<InsuredDTO> asInsuredDTOs(PolicyEntity policy) {

		Collection<InsuredDTO> insureds = new ArrayList<>();

		CollectionUtils.addAll(insureds, asInsuredDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.LIFE_ASSURED))));
		CollectionUtils.addAll(insureds, asInsuredDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.JNT_LIFE_ASSURED))));
		CollectionUtils.addAll(insureds,
				clientService
						.distinctSortClients(asInsuredDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.ADDN_LIFE_ASSURED)))));

		return insureds;
	}

	public Collection<InsuredDTO> asDeadInsuredDTOs(Collection<CliPolRelationshipEntity> clipolRelationships) {

		Collection<InsuredDTO> insureds = new ArrayList<>();

		CollectionUtils.addAll(insureds, asInsuredDTOs(CollectionUtils.select(clipolRelationships, hasTypeAndDeadPredicate(CliPolRelationshipType.LIFE_ASSURED))));
		CollectionUtils.addAll(insureds, asInsuredDTOs(CollectionUtils.select(clipolRelationships, hasTypeAndDeadPredicate(CliPolRelationshipType.JNT_LIFE_ASSURED))));
		CollectionUtils.addAll(insureds,
				clientService.distinctSortClients(asInsuredDTOs(CollectionUtils.select(clipolRelationships, hasTypeAndDeadPredicate(CliPolRelationshipType.ADDN_LIFE_ASSURED)))));

		return insureds;
	}

	public Collection<InsuredLiteDTO> asInsuredLiteDTOs(PolicyEntity policy) {

		Collection<InsuredLiteDTO> insureds = new ArrayList<>();

		CollectionUtils.addAll(insureds, asInsuredLiteDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.LIFE_ASSURED))));
		CollectionUtils.addAll(insureds, asInsuredLiteDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.JNT_LIFE_ASSURED))));
		CollectionUtils.addAll(insureds,
				clientService.distinctSortClients(asInsuredLiteDTOs(CollectionUtils.select(policy.getCliPolRelationships(), hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType.ADDN_LIFE_ASSURED)))));

		return insureds;
	}

	public abstract OtherClientDTO asOtherClientDTO(CliPolRelationshipEntity in);

	private Collection<OtherClientDTO> asOtherClientDTOs(Collection<CliPolRelationshipEntity> cliPolRelationships) {
		ClientDTOMapper<OtherClientDTO> mapper = x -> asOtherClientDTO(x);

		return asClientDTOs(cliPolRelationships, mapper);
	}

	public abstract OtherClientLiteDTO asOtherClientLiteDTO(CliPolRelationshipEntity in);

	@AfterMapping
	public OtherClientDTO afterEntityMapping(CliPolRelationshipEntity otherClientRelationship, @MappingTarget OtherClientDTO otherClient) {

		OptionDetailDTO roleDetail = optionDetailService.getOptionDetail(optionDetailService.getClientPolicyRelationTypes(), otherClientRelationship.getType());

		if (roleDetail != null) {
			otherClient.setRoleNumber(roleDetail.getNumber());
			otherClient.setRole(roleDetail.getDescription());
		}

		return otherClient;
	}

	private Collection<OtherClientLiteDTO> asOtherClientLiteDTOs(Collection<CliPolRelationshipEntity> cliPolRelationships) {
		ClientLiteDTOMapper<OtherClientLiteDTO> mapper = x -> asOtherClientLiteDTO(x);

		return asClientLiteDTOs(cliPolRelationships, mapper);
	}

	@AfterMapping
	public OtherClientLiteDTO afterEntityMapping(CliPolRelationshipEntity otherClientRelationship, @MappingTarget OtherClientLiteDTO otherClient) {

		OptionDetailDTO roleDetail = optionDetailService.getOptionDetail(optionDetailService.getClientPolicyRelationTypes(), otherClientRelationship.getType());

		if (roleDetail != null) {
			otherClient.setRoleNumber(roleDetail.getNumber());
			otherClient.setRole(roleDetail.getDescription());
		}

		return otherClient;
	}

	public Collection<OtherClientDTO> asOtherClientDTOs(PolicyEntity policy) {
		return asOtherClientDTOs(policy, CliPolRelationshipType.POLICY_HOLDER_RELATIONSHIP_TYPE_GROUP, CliPolRelationshipType.BENEFICIARY_RELATIONSHIP_TYPE_GROUP,
				CliPolRelationshipType.INSURED_RELATIONSHIP_TYPE_GROUP);
	}

	public Collection<OtherClientLiteDTO> asOtherClientLiteDTOs(PolicyEntity policy) {
		return asOtherClientLiteDTOs(policy, CliPolRelationshipType.POLICY_HOLDER_RELATIONSHIP_TYPE_GROUP, CliPolRelationshipType.BENEFICIARY_RELATIONSHIP_TYPE_GROUP,
				CliPolRelationshipType.INSURED_RELATIONSHIP_TYPE_GROUP);
	}

	public Collection<OtherClientDTO> asOtherClientDTOs(PolicyEntity policy, List<CliPolRelationshipType> policyHolderRelationshipTypeGroup,
			List<CliPolRelationshipType> beneficiaryRelationshipTypeGroup, List<CliPolRelationshipType> insuredRelationshipTypeGroup) {

		// cli-pol relations must be active only for policy!
		Collection<CliPolRelationshipEntity> cliPolRelationships = policy.getCliPolRelationships().stream().filter(x -> cliPolService.isActive(x)).collect(Collectors.toList());

		return asOtherClientDTOs(cliPolRelationships, policyHolderRelationshipTypeGroup, beneficiaryRelationshipTypeGroup, insuredRelationshipTypeGroup);
	}

	public Collection<OtherClientLiteDTO> asOtherClientLiteDTOs(PolicyEntity policy, List<CliPolRelationshipType> policyHolderRelationshipTypeGroup,
			List<CliPolRelationshipType> beneficiaryRelationshipTypeGroup, List<CliPolRelationshipType> insuredRelationshipTypeGroup) {

		// cli-pol relations must be active only for policy!
		Collection<CliPolRelationshipEntity> cliPolRelationships = policy.getCliPolRelationships().stream().filter(x -> cliPolService.isActive(x)).collect(Collectors.toList());

		return asOtherClientLiteDTOs(cliPolRelationships, policyHolderRelationshipTypeGroup, beneficiaryRelationshipTypeGroup, insuredRelationshipTypeGroup);
	}

	public Collection<OtherClientDTO> asOtherClientDTOs(String workflowItemId, List<CliPolRelationshipType> policyHolderRelationshipTypeGroup,
			List<CliPolRelationshipType> beneficiaryRelationshipTypeGroup, List<CliPolRelationshipType> insuredRelationshipTypeGroup) {

		return asOtherClientDTOs(cliPolRelationshipRepository.findAllByModifyProcess(workflowItemId), policyHolderRelationshipTypeGroup, beneficiaryRelationshipTypeGroup,
				insuredRelationshipTypeGroup);
	}

	public Collection<OtherClientDTO> asOtherClientDTOs(Collection<CliPolRelationshipEntity> cliPolRelationshipEntities, List<CliPolRelationshipType> policyHolderRelationshipTypeGroup,
			List<CliPolRelationshipType> beneficiaryRelationshipTypeGroup, List<CliPolRelationshipType> insuredRelationshipTypeGroup) {
		List<CliPolRelationshipEntity> cliPolRelations = cliPolService.getOtherCliPolRelationships(cliPolRelationshipEntities, policyHolderRelationshipTypeGroup, beneficiaryRelationshipTypeGroup,
				insuredRelationshipTypeGroup);

		return clientService.sortClients(asOtherClientDTOs(cliPolRelations));
	}

	public Collection<OtherClientLiteDTO> asOtherClientLiteDTOs(Collection<CliPolRelationshipEntity> cliPolRelationshipEntities, List<CliPolRelationshipType> policyHolderRelationshipTypeGroup,
			List<CliPolRelationshipType> beneficiaryRelationshipTypeGroup, List<CliPolRelationshipType> insuredRelationshipTypeGroup) {
		List<CliPolRelationshipEntity> cliPolRelations = cliPolService.getOtherCliPolRelationships(cliPolRelationshipEntities, policyHolderRelationshipTypeGroup, beneficiaryRelationshipTypeGroup,
				insuredRelationshipTypeGroup);

		return clientService.sortClients(asOtherClientLiteDTOs(cliPolRelations));
	}


	private CliPolRelationshipPredicate hasTypeAndActiveAndNotDeadPredicate(CliPolRelationshipType type) {
		return (cliPolRelationship) -> hasType(type, cliPolRelationship)
				&& cliPolService.isActive(cliPolRelationship) && !clientService.isDead(cliPolRelationship.getClient());
	}

	private CliPolRelationshipPredicate hasTypeAndDeadPredicate(CliPolRelationshipType type) {
		return (cliPolRelationship) -> hasType(type, cliPolRelationship) && clientService.isDead(cliPolRelationship.getClient());
	}

	public boolean hasType(CliPolRelationshipType type, CliPolRelationshipEntity cliPolRelationship) {
		return type.getValue() == cliPolRelationship.getType()
				&& cliPolRelationship.getClientId() != Constantes.FAKE_CLIENT_ID;
	}

	private CliPolRelationshipPredicate hasTypeAndNotDeadPredicate(CliPolRelationshipType type) {
		return (cliPolRelationship) -> hasType(type, cliPolRelationship) && !clientService.isDead(cliPolRelationship.getClient());
	}

	private Collection<PolicyHolderDTO> asPolicyHolderDTOs(Collection<CliPolRelationshipEntity> policyHoldersRelationship, String workflowItempId) {
		ClientDTOMapper<PolicyHolderDTO> mapper = x -> asPolicyHolderDTO(x, workflowItempId);

		return asClientDTOs(policyHoldersRelationship, mapper);
	}

	private Collection<PolicyHolderDTO> asPolicyHolderDTOs(Collection<CliPolRelationshipEntity> policyHoldersRelationship) {
		ClientDTOMapper<PolicyHolderDTO> mapper = x -> asPolicyHolderDTO(x);

		return asClientDTOs(policyHoldersRelationship, mapper);
	}

	private Collection<PolicyHolderLiteDTO> asPolicyHolderLiteDTOs(Collection<CliPolRelationshipEntity> policyHoldersRelationship) {
		ClientLiteDTOMapper<PolicyHolderLiteDTO> mapper = x -> asPolicyHolderLiteDTO(x);

		return asClientLiteDTOs(policyHoldersRelationship, mapper);
	}

	@FunctionalInterface
	public interface ClientDTOMapper<T extends ClientDTO> {
		T mapClientDTO(CliPolRelationshipEntity cliPolRelationshipEntity);
	}

	private <T extends ClientDTO> Collection<T> asClientDTOs(Collection<CliPolRelationshipEntity> cliPolRelationshipEntities, ClientDTOMapper<T> mapper) {
		if (CollectionUtils.isEmpty(cliPolRelationshipEntities)) {
			return new ArrayList<>();
		}

		Collection<T> clients = new HashSet<>();

		for (CliPolRelationshipEntity cliPolRelationship : cliPolRelationshipEntities) {
			T client = mapper.mapClientDTO(cliPolRelationship);
			clientMapper.asClientDTO(cliPolRelationship.getClient(), client);

			clients.add(client);
		}

		return clients;
	}

	@FunctionalInterface
	public interface ClientLiteDTOMapper<T extends ClientLiteDTO> {
		T mapClientLiteDTO(CliPolRelationshipEntity cliPolRelationshipEntity);
	}
	
	private <T extends ClientLiteDTO> Collection<T> asClientLiteDTOs(Collection<CliPolRelationshipEntity> cliPolRelationshipEntities, ClientLiteDTOMapper<T> clientLiteMapper) {
		if (CollectionUtils.isEmpty(cliPolRelationshipEntities)) {
			return new ArrayList<>();
		}

		Collection<T> clients = new HashSet<>();

		for (CliPolRelationshipEntity cliPolRelationship : cliPolRelationshipEntities) {
			T client = clientLiteMapper.mapClientLiteDTO(cliPolRelationship);
			clientMapper.asClientLiteDTO(cliPolRelationship.getClient(), client);

			clients.add(client);
		}

		return clients;
	}

	public Collection<BeneficiaryDTO> asDeathBeneficiaryDTOs(PolicyEntity in) {
		return asBeneficiaryDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_AT_DEATH));
	}

	public Collection<BeneficiaryDTO> asLifeBeneficiaryDTOs(PolicyEntity in) {

		Collection<BeneficiaryDTO> lifeBeneficiaries =  new ArrayList<BeneficiaryDTO>();
		CollectionUtils.addAll(lifeBeneficiaries, asBeneficiaryDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_AT_MATURITY)));
		CollectionUtils.addAll(lifeBeneficiaries, asBeneficiaryDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_BARE_OWNER)));
		CollectionUtils.addAll(lifeBeneficiaries, asBeneficiaryDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY)));
		return lifeBeneficiaries;
	}

	public Collection<BeneficiaryLiteDTO> asDeathBeneficiaryLiteDTOs(PolicyEntity in) {
		return asBeneficiaryLiteDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_AT_DEATH));
	}

	public Collection<BeneficiaryLiteDTO> asLifeBeneficiaryLiteDTOs(PolicyEntity in) {
		return asBeneficiaryLiteDTOs(cliPolService.getActiveCliPolRelationshipEntitiesWithType(in.getCliPolRelationships(), CliPolRelationshipType.BENEFICIARY_AT_MATURITY));
	}

	private Collection<BeneficiaryDTO> asBeneficiaryDTOs(Collection<CliPolRelationshipEntity> beneficiariesRelationship) {
		ClientDTOMapper<BeneficiaryDTO> mapper = x -> asBeneficiaryDTO(x);

		Collection<BeneficiaryDTO> benficiaries = asClientDTOs(beneficiariesRelationship, mapper);

		return sortBeneficiaries(benficiaries);
	}

	private Collection<BeneficiaryLiteDTO> asBeneficiaryLiteDTOs(Collection<CliPolRelationshipEntity> benficiariesRelationship) {
		ClientLiteDTOMapper<BeneficiaryLiteDTO> mapper = x -> asBeneficiaryLiteDTO(x);

		Collection<BeneficiaryLiteDTO> benficiaries = asClientLiteDTOs(benficiariesRelationship, mapper);

		return sortBeneficiaries(benficiaries);
	}

	public Boolean hasCliPolRelationship(CliPolRelationshipEntity ownerCliPolRelationship, CliPolRelationshipType cliPolRelationshipType) {
		return hasCliPolRelationship(ownerCliPolRelationship, cliPolRelationshipType, null);
	}

	public Boolean hasCliPolRelationship(CliPolRelationshipEntity ownerCliPolRelationship, CliPolRelationshipType cliPolRelationshipType, String workflowItemId) {
		PolicyEntity policy = ownerCliPolRelationship.getPolicy();

		return Boolean.valueOf(policy.getCliPolRelationships().stream().anyMatch(x -> hasCliPolRelationship(x, cliPolRelationshipType, ownerCliPolRelationship.getClientId(), workflowItemId)));
	}

	private boolean hasCliPolRelationship(CliPolRelationshipEntity cliPolRelationship, CliPolRelationshipType type, int clientId, String workflowItemId) {
		boolean condition = cliPolService.hasType(type, cliPolRelationship)
				&& clientId == cliPolRelationship.getClientId();

		if (workflowItemId != null) {
			condition &= cliPolRelationship.getModifyProcess() != null && cliPolRelationship.getModifyProcess().trim().equals(workflowItemId);
		} else {
			condition &= cliPolService.isActive(cliPolRelationship);
		}

		return condition;
	}

	private String generateCprId(CliPolRelationshipEntity entity) {
		if (StringUtils.isNotBlank(entity.getCprId())) {
			return entity.getCprId();
		}

		Assert.notNull(entity.getPolicyId());
		Assert.isTrue(entity.getType() != 0);
		Assert.notNull(entity.getTypeNumber());
		Assert.notNull(entity.getCoverage());

		String cprId = entity.getPolicyId() + "_" + entity.getType() + "_" + (entity.getTypeNumber() == 0 ? 1 : entity.getTypeNumber()) + "_" + entity.getCoverage();

		String lastCprId = cliPolRelationshipRepository.findLastCprId(cprId);
		if (lastCprId == null) {
			return cprId;
		}

		String[] split = lastCprId.split(cprId);
		if (split.length == 0) {
			return cprId + 'A';
		}

		String letters = split[1];
		if (letters.endsWith("Z")) {
			return cprId + letters + 'A';
		}

		int indexOfLastLetter = letters.length() - 1;
		char lastLetter = letters.charAt(indexOfLastLetter);

		return cprId + letters.substring(0, indexOfLastLetter) + ((char) (lastLetter + 1));
	}

	protected <T extends CommonBeneficiary> Collection<T> sortBeneficiaries(Collection<T> benficiaries) {

		List<T> sortedBenficiaries = benficiaries.stream().distinct().collect(Collectors.toList());
		sortedBenficiaries.sort(withNullObject((benef1, benef2) -> StringUtils.compare(benef1.getDisplayName(), benef2.getDisplayName())));
		sortedBenficiaries.sort(withNullObject((benef1, benef2) -> ObjectUtils.compare(benef1.getPercentageSplit(), benef2.getPercentageSplit())));
		sortedBenficiaries.sort(withNullObject((benef1, benef2) -> ObjectUtils.compare(benef1.getTypeNumber(), benef2.getTypeNumber())));

		return sortedBenficiaries;
	}

	private Comparator<CommonBeneficiary> withNullObject(final Comparator<CommonBeneficiary> chain) {
		return (benef1, benef2) -> {
			if (benef1 == benef2) {
				return 0;
			} else if (benef1 == null) {
				return -1;
			} else if (benef2 == null) {
				return 1;
			} else {
				return chain.compare(benef1, benef2);
			}
		};
	}
	
	private Integer generateDisplayNumber(CliPolRelationshipEntity policyHolderRelationship) {
		if (policyHolderRelationship.getType() == CliPolRelationshipType.OWNER.getValue()
				|| policyHolderRelationship.getType() == CliPolRelationshipType.JOINT_OWNER.getValue()) {
			return policyHolderRelationship.getType();
		} else if (policyHolderRelationship.getType() == CliPolRelationshipType.ADDN_OWNER.getValue()) {
			return policyHolderRelationship.getType() + policyHolderRelationship.getTypeNumber() - 1;
		}
		return 0;
	}

}
