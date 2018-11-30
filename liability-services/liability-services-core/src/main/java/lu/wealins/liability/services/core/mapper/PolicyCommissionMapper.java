package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyCommissionEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyCommissionDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, PolicyCommissionScheduleMapper.class })
public interface PolicyCommissionMapper {

	@Mappings({
			@Mapping(source = "policy.polId", target = "polId")
	})
	PolicyCommissionDTO asPolicyCommissionDTO(PolicyCommissionEntity in);

	List<PolicyCommissionDTO> asPolicyCommissionDTOs(List<PolicyCommissionEntity> in);

}
