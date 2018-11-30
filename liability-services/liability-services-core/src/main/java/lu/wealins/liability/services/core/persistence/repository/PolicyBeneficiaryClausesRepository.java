package lu.wealins.liability.services.core.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.liability.services.core.persistence.entity.PolicyBeneficiaryClauseEntity;

public interface PolicyBeneficiaryClausesRepository extends JpaRepository<PolicyBeneficiaryClauseEntity, Long> {

	List<PolicyBeneficiaryClauseEntity> findAllByPolicyAndTypeIgnoreCaseAndStatusOrderByRankAsc(String policy, String type, int status);

	@Query("SELECT max(pbc.pbcId) FROM PolicyBeneficiaryClauseEntity pbc")
	Long findMaxPbcId();

	@Query("SELECT pbc FROM PolicyBeneficiaryClauseEntity pbc WHERE pbc.modifyProcess = :modifyProcess")
	Collection<PolicyBeneficiaryClauseEntity> findAllByModifyProcess(@Param("modifyProcess") String modifyProcess);

	@Transactional
	@Modifying
	@Query("DELETE from PolicyBeneficiaryClauseEntity pbc WHERE pbc.modifyProcess = :modifyProcess AND pbc.status = :status")
	void deleteWithModifyProcess(@Param("modifyProcess") String modifyProcess, @Param("status") Integer status);

}
