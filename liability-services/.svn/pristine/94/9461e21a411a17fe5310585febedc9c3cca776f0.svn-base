package lu.wealins.liability.services.core.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.AgentBankAccountDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.drm.DRMAgentDTO;
import lu.wealins.common.dto.liability.services.drm.DRMBankAccountDTO;
import lu.wealins.liability.services.core.mapper.factory.AgentFactory;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { AgentFactory.class, AssetManagerStrategyMapper.class, AgentBankAccountMapper.class, AgentHierarchyMapper.class, AgentContactMapper.class,
		StringToTrimString.class })
public interface DRMMapper {

	@Mappings({
			@Mapping(source = "phone_fax", target = "fax"),
			@Mapping(source = "phone_office", target = "telephone"),
			@Mapping(source = "phone_alternate", target = "mobile"),
			@Mapping(source = "shipping_address_city", target = "town"),
			@Mapping(source = "shipping_address_postalcode", target = "postcode"),
			@Mapping(source = "bic_code_c", target = "paymentAccountBic"),
			@Mapping(source = "shipping_addr_street_line1_c", target = "addressLine1"),
			@Mapping(source = "shipping_addr_street_line2_c", target = "addressLine2"),
			@Mapping(ignore = true, target = "email"),
			@Mapping(source = "shipping_addr_street_line3_c", target = "addressLine3"),
			@Mapping(source = "shipping_addr_street_line4_c", target = "addressLine4"),
			@Mapping(source = "onboarding_status_c", target = "crmStatus"),
			@Mapping(source = "date_of_agreement_signature_c", target = "effectiveDate"),
			@Mapping(source = "end_date_of_convention_c", target = "dateOfTermination"),
			@Mapping(ignore = true, target = "country"),
			@Mapping(ignore = true, target = "category"),
			@Mapping(source = "wealins_partner_id_c", target = "crmRefererence"),
			@Mapping(source = "lissia_id_c", target = "agtId")
	})
	@BeanMapping(resultType = AgentDTO.class)
	public abstract AgentDTO asAgentDTO(DRMAgentDTO in);

	@Mappings({
			@Mapping(source = "bk_bank_bka_bankaccounts_1_name", target = "bankName"),
			@Mapping(source = "owner", target = "accountName"),
			@Mapping(source = "bic_c", target = "bic"),
			@Mapping(source = "currency", target = "commPaymentCurrency"),
			@Mapping(source = "currency", target = "accountCurrency")
	})
	@BeanMapping(resultType = AgentBankAccountDTO.class)
	public abstract AgentBankAccountDTO asAgentBankAccountDTO(DRMBankAccountDTO in);

}
