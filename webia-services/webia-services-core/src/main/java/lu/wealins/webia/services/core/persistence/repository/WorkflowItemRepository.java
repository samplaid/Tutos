package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.wealins.webia.services.core.persistence.entity.WorkflowItemEntity;

@Repository
public interface WorkflowItemRepository extends JpaRepository<WorkflowItemEntity, Long> {

	@Query(value = "SELECT wi FROM WorkflowItemEntity wi WHERE wi.workflowItemType = :workflowItemType AND wi.actionRequired = :actionRequired AND wi.status NOT IN (:excludedStatus)")
	Collection<WorkflowItemEntity> findByWorkflowItemType(@Param("workflowItemType") Integer workflowItemType, @Param("actionRequired") Integer actionRequired,
			@Param("excludedStatus") List<Integer> excludedStatus);

}
