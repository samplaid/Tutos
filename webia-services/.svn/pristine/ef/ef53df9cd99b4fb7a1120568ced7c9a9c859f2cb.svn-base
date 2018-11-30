package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.webia.services.core.persistence.entity.CheckStepEntity;
import lu.wealins.common.dto.webia.services.CheckStepDTO;

@Mapper(componentModel = "spring", uses = { LabelMapper.class, CheckWorkflowMapper.class, StepMapper.class})
public interface CheckStepMapper {

	CheckStepDTO asCheckStepDTO(CheckStepEntity in);

	@Mappings({
		@Mapping(target = "step", ignore=true),
	})
	CheckStepEntity asCheckStepEntity(CheckStepDTO in);

	Collection<CheckStepDTO> asCheckStepDTOs(Collection<CheckStepEntity> in);

}
