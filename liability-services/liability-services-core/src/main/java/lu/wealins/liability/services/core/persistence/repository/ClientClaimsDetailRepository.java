package lu.wealins.liability.services.core.persistence.repository; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lu.wealins.liability.services.core.persistence.entity.ClientClaimsDetailEntity;

public interface ClientClaimsDetailRepository extends JpaRepository<ClientClaimsDetailEntity, Integer> {
	
	@Query("SELECT ccd FROM ClientClaimsDetailEntity ccd WHERE ccd.client.cliId = ?1")
	List<ClientClaimsDetailEntity> findByClientId(Integer clientId);
	
}
