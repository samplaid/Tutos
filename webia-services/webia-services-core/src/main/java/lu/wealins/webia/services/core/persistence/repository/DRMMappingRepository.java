package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.DRMMappingEntity;

public interface DRMMappingRepository extends JpaRepository<DRMMappingEntity, String> {

	@Query("SELECT map FROM DRMMappingEntity map"
			+ " WHERE map.type = :type")
	List<DRMMappingEntity> findByType(@Param("type") String type);
}
