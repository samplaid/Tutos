package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.services.core.persistence.entity.AppFormEntity;
import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;
import lu.wealins.webia.services.core.service.BeneficiaryService;
import lu.wealins.webia.services.core.service.ClientFormService;
import lu.wealins.webia.services.core.service.InsuredService;
import lu.wealins.webia.services.core.service.PolicyHolderService;

@Mapper(componentModel = "spring", uses = { FundFormMapper.class, BenefClauseFormMapper.class, PolicyTransferFormMapper.class })
public abstract class AppFormMapper {

	@Autowired
	private PartnerFormMapper partnerFormMapper;

	@Autowired
	private PolicyHolderService policyHolderService;
	@Autowired
	private InsuredService insuredService;
	@Autowired
	private BeneficiaryService beneficiaryService;
	@Autowired
	private ClientFormService clientFormService;

	public abstract AppFormDTO asAppFormDTO(AppFormEntity in);

	public abstract AppFormEntity asAppFormEntity(AppFormDTO in);

	public abstract Collection<AppFormDTO> asAppFormDTOs(Collection<AppFormEntity> in);

	@AfterMapping
	protected AppFormDTO afterMapping(AppFormEntity in, @MappingTarget AppFormDTO target) {

		mapClients(in, target);

		if (in.getPartnerForms() != null) {
			mapPartnerForms(in, target);
		}

			return target;
		}

	private void mapClients(AppFormEntity in, AppFormDTO target) {
		target.setPolicyHolders(policyHolderService.getPolicyHolders(in.getClientForms()));
		target.setInsureds(insuredService.getInsureds(in.getClientForms()));
		target.setLifeBeneficiaries(beneficiaryService.getLifeBeneficiaries(in.getClientForms()));
		target.setDeathBeneficiaries(beneficiaryService.getDeathBeneficiaries(in.getClientForms()));
		target.setOtherClients(clientFormService.getOtherClients(in.getClientForms()));
	}

	private void mapPartnerForms(AppFormEntity in, AppFormDTO target) {
		for (PartnerFormEntity elem : in.getPartnerForms()) {

			if (elem.getPartnerCategory().equals(AgentCategory.BROKER.getCategory())) {
				target.setBroker(partnerFormMapper.asPartnerFormDTO(elem));

			} else if (elem.getPartnerCategory().equals(AgentCategory.SUB_BROKER.getCategory())) {
				target.setSubBroker(partnerFormMapper.asPartnerFormDTO(elem));

			} else if (elem.getPartnerCategory().equals(AgentCategory.INTRODUCER.getCategory())) {
				target.setBusinessIntroducer(partnerFormMapper.asPartnerFormDTO(elem));

			} else if (elem.getPartnerCategory().equals(AgentCategory.PERSON_CONTACT.getCategory())) {
				target.setBrokerContact(partnerFormMapper.asPartnerFormDTO(elem));

			} else if (elem.getPartnerCategory().equals(AgentCategory.WEALINS_SALES_PERSON.getCategory())) {
				target.getCountryManagers().add(partnerFormMapper.asPartnerFormDTO(elem));
			}

		}
	}

}
