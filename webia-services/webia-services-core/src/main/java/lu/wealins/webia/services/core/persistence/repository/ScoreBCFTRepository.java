package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.ScoreBCFTEntity;
import lu.wealins.webia.services.core.persistence.entity.ScoreBCFTId;

public interface ScoreBCFTRepository extends JpaRepository<ScoreBCFTEntity, ScoreBCFTId> {

	@Query(value = "SELECT SUM(sb.score) FROM SCORE_BCFT sb INNER JOIN CHECK_WORKFLOW cw ON cw.checkcode = sb.checkcode INNER JOIN CHECK_DATA cd ON cd.check_id = cw.check_id WHERE cd.workflow_item_id = :workflowItemId AND cd.data_value_yesnona = sb.response", nativeQuery = true)
	Integer computeScore(@Param("workflowItemId") Integer workflowItemId);

	List<ScoreBCFTEntity> findByCheckCode(String checkCode);
}
