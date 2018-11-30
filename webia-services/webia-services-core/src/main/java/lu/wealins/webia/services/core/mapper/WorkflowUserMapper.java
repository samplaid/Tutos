package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.WorkflowUserEntity;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

@Mapper(componentModel = "spring")
public interface WorkflowUserMapper {

	WorkflowUserEntity asWorkflowUserEntity(WorkflowUserDTO in);

	WorkflowUserDTO asWorkflowUserDTO(WorkflowUserEntity in);

	Collection<WorkflowUserDTO> asWorkflowUserDTOs(Collection<WorkflowUserEntity> in);

}
