package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.webia.services.StepCommentDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.webia.services.core.persistence.entity.StepCommentEntity;
import lu.wealins.webia.services.core.service.WorkflowUserService;

@Mapper(componentModel = "spring", uses = {StepMapper.class})
public abstract class StepCommentMapper {
	
	@Autowired
	private WorkflowUserService workflowUserService;

	public abstract StepCommentDTO asStepCommentDTO(StepCommentEntity in);

	public abstract Collection<StepCommentDTO> asStepCommentDTOs(Collection<StepCommentEntity> in);

	@AfterMapping
	protected StepCommentDTO afterMapping(StepCommentEntity in, @MappingTarget StepCommentDTO target) {
		WorkflowUserDTO workflowUser = workflowUserService.getWorkflowUserWithLogin(in.getCreationUser());
		if (workflowUser != null){
			target.setCreationUser(workflowUser.getName0() + " (" + in.getCreationUser() +")");
		}
		return target;
	}

}
