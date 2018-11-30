package lu.wealins.liability.services.core.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.GeneralNoteDTO;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicyNoteDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyActiveStatus;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.BeneficiaryService;
import lu.wealins.liability.services.core.business.InsuredService;
import lu.wealins.liability.services.core.business.OptionDetailService;
import lu.wealins.liability.services.core.business.OtherClientService;
import lu.wealins.liability.services.core.business.PolicyAgentShareService;
import lu.wealins.liability.services.core.business.PolicyCoverageService;
import lu.wealins.liability.services.core.business.PolicyHolderService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, ProductMapper.class, PolicyAgentShareMapper.class,
		PolicyCoverageMapper.class, PolicyPremiumMapper.class, UoptDetailMapper.class, PolicyTransferMapper.class })
public abstract class PolicyMapper {

	private static final String PON_TYPE_1 = "PON_TYPE_1";

	@Autowired
	private PolicyService policyService;
	@Autowired
	protected PolicyHolderService policyHolderService;
	@Autowired
	protected InsuredService insuredService;
	@Autowired
	protected BeneficiaryService beneficiaryService;
	@Autowired
	protected OtherClientService otherClientService;
	@Autowired
	private PolicyAgentShareService policyAgentShareService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private OptionDetailService optionDetailService;
	@Autowired
	private PolicyCoverageService policyCoverageService;

	private final Logger logger = LoggerFactory.getLogger(PolicyMapper.class);

	@Autowired
	private GeneralNoteMapper noteMapper;

	@Mappings({
			@Mapping(source = "product.name", target = "productName"),
			@Mapping(source = "product.nlCountry", target = "nlCountry"),
			@Mapping(source = "product.prdId", target = "prdId")
	})
	public abstract PolicyLightDTO asPolicyLightDTO(PolicyEntity in);

	public abstract List<PolicyLightDTO> asPolicyLightDTOs(List<PolicyEntity> in);

	@Mappings({
			@Mapping(target = "mailToAgent", ignore = true)
	})
	public abstract PolicyDTO asPolicyDTO(PolicyEntity in);

	public abstract List<PolicyDTO> asPolicyDTOs(List<PolicyEntity> in);

	@AfterMapping
	public PolicyDTO afterEntityMapping(PolicyEntity policyEntity, @MappingTarget PolicyDTO policyDTO) {
		// Agents
		mapAgents(policyEntity, policyDTO);
		// General
		mapGeneralInformation(policyEntity, policyDTO);
		// Clients
		mapClients(policyEntity, policyDTO);

		return policyDTO;
	}

	protected void mapGeneralInformation(PolicyEntity policyEntity, PolicyDTO policyDTO) {
		final OptionDetailDTO status = getStatus(policyEntity);
		policyDTO.setPolicyStatus(status);
		final PolicyActiveStatus policyActiveStatus = policyService.getPolicyActiveStatus(policyEntity.getStatus(), policyEntity.getSubStatus());
		if (policyActiveStatus != null) {
			policyDTO.setActiveStatus(policyActiveStatus.getStatus());
		}
		policyDTO.setFirstPolicyCoverages(policyCoverageService.getFirstPolicyCoverage(policyEntity));
		policyDTO.setPolicyNotes(mapPolicyNotes(noteMapper.asGeneralNoteDTOs(policyEntity.getGeneralNotes())));
	}

	protected void mapAgents(PolicyEntity policyEntity, PolicyDTO policyDTO) {
		policyDTO.setBroker(policyAgentShareService.getBroker(policyEntity));
		policyDTO.setBrokerContact(policyAgentShareService.getBrokerContact(policyEntity));
		policyDTO.setSubBroker(policyAgentShareService.getSubBroker(policyEntity));
		policyDTO.setBusinessIntroducer(policyAgentShareService.getBusinessIntroducer(policyEntity));
		policyDTO.setCountryManagers(policyAgentShareService.getCountryManagers(policyEntity));
		if (StringUtils.isNotBlank(policyEntity.getMailToAgent())) {
			policyDTO.setMailToAgent(agentService.getAgentLite(policyEntity.getMailToAgent()));
		}
	}

	protected void mapClients(PolicyEntity policyEntity, PolicyDTO policyDTO) {
		policyDTO.setPolicyHolders(policyHolderService.getPolicyHolders(policyEntity));
		policyDTO.setLifeBeneficiaries(beneficiaryService.getLifeBeneficiaries(policyEntity));
		policyDTO.setDeathBeneficiaries(beneficiaryService.getDeathBeneficiaries(policyEntity));
		policyDTO.setInsureds(insuredService.getInsureds(policyEntity));
		policyDTO.setOtherClients(otherClientService.getOtherClients(policyEntity));
	}

	public OptionDetailDTO getStatus(PolicyEntity policyEntity) {
		int status = policyEntity.getStatus();
		Integer subStatus = policyEntity.getSubStatus();

		return optionDetailService.getOptionDetail(optionDetailService.getPolicyStatuses(status), subStatus);
	}

	private List<PolicyNoteDTO> mapPolicyNotes(List<GeneralNoteDTO> generalNotes) {
		List<PolicyNoteDTO> policyNotes = new ArrayList<PolicyNoteDTO>();
		if (generalNotes != null) {
			generalNotes.forEach(note -> {
				if (note != null) {
					PolicyNoteDTO poliyNote = new PolicyNoteDTO();
					poliyNote.setNote(note);
					poliyNote.setType(optionDetailService.getPolicyNote(PON_TYPE_1, note.getType()));
					policyNotes.add(poliyNote);
				}
			});
		}
		return policyNotes;
	}

	@AfterMapping
	public PolicyLightDTO afterEntityMapping(PolicyEntity policyEntity, @MappingTarget PolicyLightDTO policyLightDTO) {

		OptionDetailDTO status = getStatus(policyEntity);
		if (status == null) {
			logger.warn(policyEntity.getPolId() + " has no status.");
		}
		policyLightDTO.setPolicyStatus(status);
		if (status == null) {
			logger.warn(policyEntity.getPolId() + " has no active status.");
		}
		PolicyActiveStatus activeStatus = policyService.getPolicyActiveStatus(policyEntity.getStatus(), policyEntity.getSubStatus());
		if (activeStatus != null) {
			policyLightDTO.setActiveStatus(activeStatus.getStatus());
		}
		policyLightDTO.setFirstCoverage(policyCoverageService.getFirstPolicyCoverage(policyEntity));

		policyLightDTO.setPolicyHolders(policyHolderService.getPolicyHolderLites(policyEntity));
		policyLightDTO.setInsureds(insuredService.getInsuredLites(policyEntity));
		policyLightDTO.setLifeBeneficiaries(beneficiaryService.getLifeBeneficiaryLites(policyEntity));
		policyLightDTO.setDeathBeneficiaries(beneficiaryService.getDeathBeneficiaryLites(policyEntity));
		policyLightDTO.setOtherClients(otherClientService.getOtherClientLites(policyEntity));
		policyLightDTO.setBroker(policyAgentShareService.getBroker(policyEntity));

		return policyLightDTO;
	}

}
