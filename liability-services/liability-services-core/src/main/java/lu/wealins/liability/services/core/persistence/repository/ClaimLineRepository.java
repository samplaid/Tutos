package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository; 
import lu.wealins.liability.services.core.persistence.entity.ClaimLineEntity; 
public interface ClaimLineRepository extends JpaRepository<ClaimLineEntity, Long> {} 
