package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyCvgLiveEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyCvgLiveDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyCvgLiveMapper {
	@Mappings({
			@Mapping(source = "policyCoverage.pocId", target = "pocId")
	})
	PolicyCvgLiveDTO toPolicyCvgLiveDTO(PolicyCvgLiveEntity in);

	List<PolicyCvgLiveDTO> toPolicyCvgLiveDTOs(List<PolicyCvgLiveEntity> in);
}
