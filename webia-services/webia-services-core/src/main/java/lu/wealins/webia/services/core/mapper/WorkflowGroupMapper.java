package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.WorkflowGroupEntity;
import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;


@Mapper(componentModel = "spring", uses = { WorkflowUserMapper.class })
public interface WorkflowGroupMapper {

	WorkflowGroupEntity asWorkflowGroupEntity(WorkflowGroupDTO in);

	WorkflowGroupDTO asWorkflowGroupDTO(WorkflowGroupEntity in);

	Collection<WorkflowGroupDTO> asWorkflowGroupDTOs(Collection<WorkflowGroupEntity> in);

}
