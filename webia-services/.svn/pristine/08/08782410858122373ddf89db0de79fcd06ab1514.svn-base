package lu.wealins.webia.services.core.persistence.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;

public interface ClientFormRepository extends JpaRepository<ClientFormEntity, Integer> {

	Collection<ClientFormEntity> findByFormIdAndClientId(Integer formId, Integer clientId);

	Collection<ClientFormEntity> findByFormId(Integer formId);

	@Transactional
	@Modifying
	@Query("delete from ClientFormEntity c WHERE c.formId = :formId")
	void deleteWithFormId(@Param("formId") Integer formId);

}
