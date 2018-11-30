package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.CheckDataEntity;

public interface CheckDataRepository extends JpaRepository<CheckDataEntity, Integer> {

	CheckDataEntity findByWorkflowItemIdAndCheckId(Integer workflowItemId, Integer checkId);

	@Query("SELECT cd FROM CheckDataEntity cd JOIN cd.checkWorkflow cw WHERE cd.workflowItemId = :workflowItemId and cw.checkCode IN :checkCodes")
	List<CheckDataEntity> findByWorkflowItemIdAndCheckCodeIn(@Param("workflowItemId") Integer workflowItemId, @Param("checkCodes") List<String> checkCodes);

	@Query("SELECT cd FROM CheckDataEntity cd JOIN cd.checkWorkflow cw WHERE cd.workflowItemId = :workflowItemId and cw.checkCode = :checkCode")
	CheckDataEntity findByWorkflowItemIdAndCheckCode(@Param("workflowItemId") Integer workflowItemId, @Param("checkCode") String checkCode);

}
