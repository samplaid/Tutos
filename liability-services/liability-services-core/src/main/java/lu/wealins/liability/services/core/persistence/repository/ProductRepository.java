package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

	@Query("SELECT p FROM ProductEntity p WHERE p.status = 3 ORDER BY p.name ASC")
	List<ProductEntity> findActiveProducts();

	@Query("SELECT p.nlCountry FROM ProductEntity p WHERE p.prdId = :prdId")
	String findCountryCode(@Param("prdId") String prdId);

}
