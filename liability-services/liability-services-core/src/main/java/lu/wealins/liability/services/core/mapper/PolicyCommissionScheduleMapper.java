package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyCommissionScheduleEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyCommissionScheduleDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyCommissionScheduleMapper {

	@Mappings({
			@Mapping(source = "policyCommission.pcmId", target = "pcmId")
	})
	PolicyCommissionScheduleDTO asPolicyCommissionScheduleDTO(PolicyCommissionScheduleEntity in);

	List<PolicyCommissionScheduleDTO> asPolicyCommissionScheduleDTOs(List<PolicyCommissionScheduleEntity> in);

}
