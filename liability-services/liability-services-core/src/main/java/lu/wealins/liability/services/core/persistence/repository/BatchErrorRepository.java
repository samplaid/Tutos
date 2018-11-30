package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository; 
import lu.wealins.liability.services.core.persistence.entity.BatchErrorEntity; 
public interface BatchErrorRepository extends JpaRepository<BatchErrorEntity, Long> {} 
