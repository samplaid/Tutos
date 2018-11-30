package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.WorkflowQueueEntity;

public interface WorkflowQueueRepository extends JpaRepository<WorkflowQueueEntity, Integer> {

	@Query("SELECT wq.user.usrId FROM WorkflowQueueEntity wq")
	List<String> findWorkflowQueueUsrIds();

	WorkflowQueueEntity findById(Integer id);

	@Query("SELECT wq FROM WorkflowQueueEntity wq WHERE wq.user.usrId = :usrId")
	Collection<WorkflowQueueEntity> findByUserId(@Param("usrId") String usrId);
}
