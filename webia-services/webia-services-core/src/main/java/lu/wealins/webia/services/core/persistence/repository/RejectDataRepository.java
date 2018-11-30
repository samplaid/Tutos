package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.RejectDataEntity;

public interface RejectDataRepository extends JpaRepository<RejectDataEntity, Integer> {

	Collection<RejectDataEntity> findByWorkflowItemIdOrderByCreationDtDesc(Integer workflowItemId);

	Collection<RejectDataEntity> findByWorkflowItemIdAndStepIdOrderByCreationDtDesc(Integer workflowItemId, Integer stepId);

}
