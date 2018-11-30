package lu.wealins.liability.services.core.persistence.repository; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.ProductLineEntity;

public interface ProductLineRepository extends JpaRepository<ProductLineEntity, String> {
	
	@Query("SELECT pl FROM ProductLineEntity pl WHERE pl.status = 1 ORDER BY pl.name ASC")
	List<ProductLineEntity> findActiveProductLines();

	@Query("SELECT pl FROM ProductLineEntity pl WHERE pl.status = 1 AND pl.product.prdId = :prdId ORDER BY pl.name ASC")
	List<ProductLineEntity> findActiveProductLinesByProductId(@Param("prdId") Integer prdId);

	@Query("SELECT pl FROM ProductLineEntity pl JOIN pl.productValues pv WHERE pl.status = 1 AND pl.product.status <> 2 AND pl.primaryCoverage = 1 AND pv.control IN :controls AND pv.alphaValue <> '' AND pl.product.prdId = :prdId")
	List<ProductLineEntity> findByProductAndControls(@Param("prdId") String prdId, @Param("controls") List<String> controls);

	@Query("SELECT pc.productLine FROM PolicyCoverageEntity pc WHERE pc.policyId = :polId and coverage = :coverage")
	String findByPolIdAndCoverage(@Param("polId") String polId, @Param("coverage") Integer coverage);

}
