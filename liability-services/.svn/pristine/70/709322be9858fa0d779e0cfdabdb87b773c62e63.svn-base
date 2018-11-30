package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity;

public interface UoptDetailRepository extends JpaRepository<UoptDetailEntity, String> {

	@Query("select u from UoptDetailEntity u where u.uoptDefinition.udfId = :udfId ORDER BY description ASC")
	public List<UoptDetailEntity> findByUoptDefinitionUdfId(@Param("udfId") int udfId);

	@Query("select u from UoptDetailEntity u where u.keyValue = :keyValue")
	public UoptDetailEntity findByUoptKeyValue(@Param("keyValue") String keyValue);

}
