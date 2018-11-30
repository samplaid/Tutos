package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.WorkflowGroupEntity;


public interface WorkflowGroupRepository extends JpaRepository<WorkflowGroupEntity, Integer> {

	@Query(value = "SELECT ug.* FROM WORKFLOW_USER_GROUPS ug INNER JOIN WORKFLOW_USER_GROUP_RELATIONSHIPS ugr ON ugr.fk_user_groupsurg_id = ug.urg_id INNER JOIN WORKFLOW_USERS u ON ugr.FK_USERSUSR_ID = u.USR_ID AND u.USR_ID = :usrId AND ugr.STATUS = 1", nativeQuery = true)
	List<WorkflowGroupEntity> findWorkflowGroupsByWorkflowUser(@Param("usrId") String usrId);

}
