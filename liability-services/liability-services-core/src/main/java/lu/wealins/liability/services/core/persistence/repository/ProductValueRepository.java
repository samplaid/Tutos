package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageEntity;
import lu.wealins.liability.services.core.persistence.entity.ProductValueEntity;

public interface ProductValueRepository extends JpaRepository<ProductValueEntity, String> {

	@Query("SELECT pv FROM ProductValueEntity pv WHERE pv.controlDefinition.cdfId IN :controls AND pv.fund.fdsId = :fdsId and pv.valueFrom>1")
	List<ProductValueEntity> findByFundAndControls(@Param("fdsId") String fdsId, @Param("controls") List<String> controls);

	@Query("SELECT pv FROM ProductValueEntity pv WHERE pv.policyCoverage.policy.polId = :polId AND pv.controlDefinition.cdfId IN :controls and pv.valueFrom>1")
	List<ProductValueEntity> findByPolicyAndControls(@Param("polId") String polId, @Param("controls") List<String> controls);
	
	@Query("SELECT pv FROM ProductValueEntity pv WHERE pv.policyCoverage.policy.polId = :polId AND pv.controlDefinition.cdfId IN :controls and pv.valueFrom>1 and pv.policyCoverage.coverage = :coverage")
	List<ProductValueEntity> findByPolicyAndControlsAndCoverage(@Param("polId") String polId, @Param("controls") List<String> controls, @Param("coverage") Integer coverage);

	@Query("SELECT pv FROM ProductValueEntity pv WHERE pv.productLine.prlId = :prlId AND pv.controlDefinition.cdfId IN :controls and pv.valueFrom>1")
	List<ProductValueEntity> findByProductLineAndControls(@Param("prlId") String prlId, @Param("controls") List<String> controls);

	@Query("SELECT pv FROM ProductValueEntity pv WHERE pv.alphaValue !='' AND pv.productLine.status = 1 and pv.valueFrom>1 AND pv.productLine.product.prdId = :prdId AND pv.controlDefinition.cdfId IN :controls")
	List<ProductValueEntity> findByProductAndControls(@Param("prdId") String prdId, @Param("controls") List<String> controls);

	@Query("SELECT pv FROM ProductLineEntity pl JOIN pl.productValues pv WHERE pl.status = 1 AND pl.product.status <> 2 AND pl.primaryCoverage = 1 AND pv.control IN :controls AND pv.alphaValue <> '' AND pl.product.prdId = :prdId")
	List<ProductValueEntity> findByProductAndControlsForPL(@Param("prdId") String prdId, @Param("controls") List<String> controls);

	@Query("SELECT pv FROM ProductValueEntity pv WHERE pv.policyCoverage.policy.polId = :polId AND pv.controlDefinition.cdfId IN :controls and pv.policyCoverage = :coverage")
	ProductValueEntity findByPolicyAndControlsAndCoverage(@Param("polId") String polId, @Param("controls") List<String> controls, @Param("coverage") PolicyCoverageEntity coverage);

}
