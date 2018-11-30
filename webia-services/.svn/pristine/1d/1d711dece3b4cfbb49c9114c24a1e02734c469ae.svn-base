package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.StepEntity;

public interface StepRepository extends JpaRepository<StepEntity, Integer> {

	Collection<StepEntity> findByWorkflowItemTypeId(Integer workflowItemTypeId);
	
	StepEntity findFirstByStepWorkflowIgnoreCaseOrderByStepIdAsc(String stepWorkflow);

	StepEntity findFirstByStepWorkflowIgnoreCaseAndWorkflowItemTypeIdOrderByStepIdAsc(String stepWorkflow, Integer workflowItemTypeId);
}
