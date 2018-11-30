package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.SasMappingEntity;
import lu.wealins.webia.services.core.persistence.entity.SasMappingEntityId;

public interface SasMappingRepository extends JpaRepository<SasMappingEntity, SasMappingEntityId> {

	/**
	 * Filter by EVENT_TYPE
	 */
	public static final String EVENT_TYPE = "EVENT_TYPE";

	@Query("select sme from SasMappingEntity sme where sme.type = :eventType")
	List<SasMappingEntity> findByType(@Param("eventType") String eventType);
}
