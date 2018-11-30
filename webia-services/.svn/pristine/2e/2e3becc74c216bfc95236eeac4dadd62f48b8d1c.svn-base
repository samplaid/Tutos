package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.services.core.persistence.entity.SapOpenBalanceEntity;

@Mapper(componentModel = "spring")
public interface SapOpenBalanceMapper {
	@Mappings({
			@Mapping(source = "id", target = "comId"),
			@Mapping(source = "agent", target = "agentId"),
			@Mapping(source = "amountInCurrency", target = "comAmount"),
			@Mapping(source = "currency", target = "comCurrency"),
			@Mapping(source = "product", target = "productCd"),
			@Mapping(source = "policyId", target = "policyId"),
			@Mapping(source = "fund", target = "fundName"),
			@Mapping(source = "commissionType", target = "comType"),
			@Mapping(source = "accountingDate", target = "comDate"),
			@Mapping(expression = "java(org.jboss.resteasy.util.DateUtil.formatDate(in.getAccountingDate(), \"yyyyMM\"))", target = "accountingMonth")
	})
	CommissionToPayDTO asCommissionToPayDTO(SapOpenBalanceEntity in);

	List<CommissionToPayDTO> asCommissionToPayDTOs(List<SapOpenBalanceEntity> in);

}
