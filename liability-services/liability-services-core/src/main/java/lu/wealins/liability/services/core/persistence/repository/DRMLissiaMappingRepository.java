package lu.wealins.liability.services.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.DRMLissiaMappingEntity;
import lu.wealins.liability.services.core.persistence.entity.DRMLissiaMappingEntity.DRMLissiaMappingEntityID;

public interface DRMLissiaMappingRepository extends JpaRepository<DRMLissiaMappingEntity, DRMLissiaMappingEntityID> {
	@Query(value = "SELECT m.dataOut " + 
            "FROM DRMLissiaMappingEntity m " +
			"WHERE m.id.type = :type " +
            "AND m.id.dataIn = :dataIn " +
	        "AND status = 1")
	String findMapping(@Param("type") String type, @Param("dataIn") String dataIn);
} 
