package lu.wealins.liability.services.core.persistence.repository; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lu.wealins.liability.services.core.persistence.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {

	@Query("SELECT c FROM CountryEntity c WHERE c.status = 1 ORDER BY c.name")
	List<CountryEntity> findActiveCountries();

}
