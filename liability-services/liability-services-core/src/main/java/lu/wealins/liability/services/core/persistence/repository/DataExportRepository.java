package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository; 
import lu.wealins.liability.services.core.persistence.entity.DataExportEntity; 
public interface DataExportRepository extends JpaRepository<DataExportEntity, Long> {} 
