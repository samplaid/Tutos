package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.webia.services.core.persistence.entity.SapMappingEntity;

public interface SapMappingRepository extends JpaRepository<SapMappingEntity, String> {
	@Query("SELECT concat(map1.dataOut, map2.dataOut) as account FROM SapMappingEntity as map1, SapMappingEntity as map2"
			+ " WHERE map1.type ='ACCOUNT' AND map2.type ='CURRENCY'"
			+ " AND map1.dataIn= :account AND map2.dataIn=:currency")
	String findAccountNumberByTypeAccountAndCurrency(@Param("account") String account, @Param("currency") String currency);
	
	@Query("SELECT map FROM SapMappingEntity map"
			+ " WHERE map.type = :type")
	List<SapMappingEntity> findByType(@Param("type") String type);
}
