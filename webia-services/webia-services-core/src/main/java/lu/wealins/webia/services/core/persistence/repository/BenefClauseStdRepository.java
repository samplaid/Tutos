package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.BenefClauseStdEntity;

public interface BenefClauseStdRepository extends JpaRepository<BenefClauseStdEntity, Integer> {

	List<BenefClauseStdEntity> findByProductCdOrderBySortNumberAsc(String productCd);

	List<BenefClauseStdEntity> findByProductCdAndLangCdOrderBySortNumberAsc(String productCd, String langCd);

	List<BenefClauseStdEntity> findByBenefClauseCd(String benefClauseCd);

}
