package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyFundHoldingEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyFundHoldingDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyFundHoldingMapper {

	@Mappings({
			@Mapping(source = "policy.polId", target = "polId"),
			@Mapping(source = "fund.fdsId", target = "fdsId")
	})
	PolicyFundHoldingDTO asPolicyFundHoldingDTO(PolicyFundHoldingEntity in);

	List<PolicyFundHoldingDTO> asPolicyFundHoldingDTOs(List<PolicyFundHoldingEntity> in);

}
