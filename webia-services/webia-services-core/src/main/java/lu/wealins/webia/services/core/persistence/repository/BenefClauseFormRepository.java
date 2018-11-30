package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.BenefClauseFormEntity;

public interface BenefClauseFormRepository extends JpaRepository<BenefClauseFormEntity, Integer> {

	List<BenefClauseFormEntity> findByFormIdAndClauseFormTp(Integer formId, String clauseFormTp);
}
