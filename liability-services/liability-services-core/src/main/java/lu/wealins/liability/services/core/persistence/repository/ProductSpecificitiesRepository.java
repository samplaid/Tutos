package lu.wealins.liability.services.core.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.ProductSpecificitiesEntity;

public interface ProductSpecificitiesRepository extends JpaRepository<ProductSpecificitiesEntity, String> {

	@Query("SELECT p FROM ProductSpecificitiesEntity p WHERE p.prdId = :prdId AND p.startDate < :startDate")
	List<ProductSpecificitiesEntity> findProductSpecificitiesAfterStartDate(@Param("prdId") String prdId, @Param("startDate") Date startDate);

}
