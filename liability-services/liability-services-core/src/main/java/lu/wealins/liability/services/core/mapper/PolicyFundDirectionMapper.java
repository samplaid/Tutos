package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyFundDirectionEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyFundDirectionDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyFundDirectionMapper {

	@Mappings({
			@Mapping(source = "policy.polId", target = "polId"),
			@Mapping(source = "fund.fdsId", target = "fdsId")
	})
	PolicyFundDirectionDTO toPolicyFundDirectionDTO(PolicyFundDirectionEntity in);

	List<PolicyFundDirectionDTO> toPolicyFundDirectionDTOs(List<PolicyFundDirectionEntity> in);

}
