package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository; 
import lu.wealins.liability.services.core.persistence.entity.BillEntity; 
public interface BillRepository extends JpaRepository<BillEntity, Long> {} 
