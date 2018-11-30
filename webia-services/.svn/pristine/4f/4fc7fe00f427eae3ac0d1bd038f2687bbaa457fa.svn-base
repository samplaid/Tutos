package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.webia.services.core.components.CommissionToPayWrapper;
import lu.wealins.common.dto.webia.services.CommissionToPayWrapperDTO;

@Mapper(componentModel = "spring", uses = { CommissionToPayMapper.class })
public interface CommissionToPayWrapperMapper {
	@Mappings({
			@Mapping(source = "commissionToTypeTuple.agentId", target = "agentId"),
			@Mapping(source = "commissionToTypeTuple.currency", target = "currency"),
			@Mapping(source = "commissionToPayEntities", target = "commissionToPayEntities"),
			})
	CommissionToPayWrapperDTO asCommissionToPayWrapperDTO(CommissionToPayWrapper in);

	List<CommissionToPayWrapperDTO> asCommissionToPayWrapperDTOs(List<CommissionToPayWrapper> in);

}
