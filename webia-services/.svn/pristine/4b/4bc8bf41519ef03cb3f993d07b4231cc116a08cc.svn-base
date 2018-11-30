package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.CheckStepEntity;

public interface CheckStepRepository extends JpaRepository<CheckStepEntity, Integer> {

	@Query("SELECT cs FROM CheckStepEntity cs LEFT JOIN cs.label WHERE cs.step.stepId = :stepId ORDER BY cs.label.labelOrder, cs.checkOrder ASC")
	List<CheckStepEntity> findByStepId(@Param("stepId") Integer stepId);

	@Query("SELECT cs FROM CheckStepEntity cs JOIN cs.check cw WHERE cs.step.stepId = :stepId AND cw.checkCode IN :checkCodes")
	List<CheckStepEntity> findByStepIdAndCheckCodes(@Param("stepId") Integer stepId, @Param("checkCodes") List<String> checkCodes);
	
	@Query("SELECT cs FROM CheckStepEntity cs JOIN cs.check cw WHERE cw.checkCode = :checkCode")
	List<CheckStepEntity> findByCheckCode(@Param("checkCode") String checkCode);	
	
	@Query("SELECT cs FROM CheckStepEntity cs JOIN cs.check cw WHERE cw.checkCode IN :checkCodes and cs.step.workflowItemTypeId = :workflowItemTypeId")
	List<CheckStepEntity> findByCheckCodesAndWorkflowItemTypeId(@Param("checkCodes") List<String> checkCodes, @Param("workflowItemTypeId") Integer workflowItemTypeId);

	List<CheckStepEntity> findByStepWorkflowItemTypeId(Integer workflowItemTypeId);

}
