package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.StepCommentEntity;

public interface StepCommentRepository extends JpaRepository<StepCommentEntity, Long> {

	Collection<StepCommentEntity> findAllByWorkflowItemIdOrderByCreationDtDesc(Long workflowItemId);
	
}
