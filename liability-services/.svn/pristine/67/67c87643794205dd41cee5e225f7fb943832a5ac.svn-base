package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.ClientLinkedPersonEntity;

public abstract interface ClientLinkedPersonRepository extends JpaRepository<ClientLinkedPersonEntity, Long> {

	/** find a Contact Person */
	@Query("select c from ClientLinkedPersonEntity c where c.controllingPerson = :id and c.status = 1")
	public List<ClientLinkedPersonEntity> findForClient(@Param("id") int id);

}
