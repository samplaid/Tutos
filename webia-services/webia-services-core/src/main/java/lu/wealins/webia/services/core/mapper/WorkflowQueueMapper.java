package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.WorkflowQueueEntity;
import lu.wealins.common.dto.webia.services.WorkflowQueueDTO;

@Mapper(componentModel = "spring", uses = { WorkflowUserMapper.class, WorkflowGroupMapper.class })
public interface WorkflowQueueMapper {

	WorkflowQueueDTO asWorkflowQueueDTO(WorkflowQueueEntity in);

	Collection<WorkflowQueueDTO> asWorkflowQueueDTOs(Collection<WorkflowQueueEntity> in);
}
