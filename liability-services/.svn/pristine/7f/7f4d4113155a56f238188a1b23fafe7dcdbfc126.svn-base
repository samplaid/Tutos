package lu.wealins.liability.services.core.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.ClientEntity;

public abstract interface ClientRepository extends JpaRepository<ClientEntity, Integer>, JpaSpecificationExecutor<ClientEntity> {

	/** find a Contact Person */
	@Query("select c from ClientEntity c where c.cliId = :id and c.type=4")
	public ClientEntity findOneContactPerson(@Param("id") int id);

	@Query("SELECT c FROM ClientEntity c WHERE c.status = ?3 "
			+ " and (upper(c.name) like %?1% "
			+ "		or upper(c.firstName) like %?1% "
			+ "		or upper(c.cliId) like %?1% ) "
			+ " and ?2 = c.dateOfBirth"
			+ " order by trim(leading from c.name), c.cliId")
	public Page<ClientEntity> findByIdOrNameAndBirthDate(String search, Date birthDate, Integer status, Pageable pageable);

	@Query("SELECT c FROM ClientEntity c WHERE c.status = ?4 "
			+ " and (upper(c.name) like %?1% "
			+ "		or upper(c.firstName) like %?1% "
			+ "		or upper(c.cliId) like %?1% ) "
			+ " and ?2 = c.dateOfBirth"
			+ " and c.type = ?3"
			+ " order by trim(leading from c.name), c.cliId")
	public Page<ClientEntity> findByIdOrNameAndBirthDateAndType(String search, Date birthDate, Integer type, Integer status, Pageable pageable);

	@Query("SELECT c FROM ClientEntity c WHERE c.status = ?2 "
			+ " and (upper(c.name) like %?1% "
			+ "		or upper(c.firstName) like %?1% "
			+ "		or upper(c.cliId) like %?1% ) "
			+ " order by trim(leading from c.name), c.cliId")
	public Page<ClientEntity> findByIdOrName(String search, Integer status, Pageable pagable);

	@Query("SELECT c FROM ClientEntity c WHERE c.status = ?3 "
			+ " and (upper(c.name) like %?1% "
			+ "		or upper(c.firstName) like %?1% "
			+ "		or upper(c.cliId) like %?1% ) "
			+ " and c.type = ?2"
			+ " order by trim(leading from c.name), c.cliId")
	public Page<ClientEntity> findByIdOrNameAndType(String search, Integer type, Integer status, Pageable pagable);

	@Query("SELECT c FROM ClientEntity c WHERE c.status = ?2 "
			+ " and ?1 = c.dateOfBirth"
			+ " order by trim(leading from c.name), c.cliId")
	public Page<ClientEntity> findByBirthDate(Date birthDate, Integer status, Pageable pagable);

	@Query("SELECT c FROM ClientEntity c WHERE c.status = ?3 "
			+ " and ?1 = c.dateOfBirth"
			+ " and c.type = ?2"
			+ " order by trim(leading from c.name), c.cliId")
	public Page<ClientEntity> findByBirthDateAndType(Date birthDate, Integer type, Integer status, Pageable pagable);

	List<ClientEntity> findAll(Specification<ClientEntity> spec);

	@Query("select c from ClientEntity c where c.fiduciary IS NOT NULL and c.fiduciary != 0")
	List<ClientEntity> findAllFiduciaries();

	/**
	 * GDPR client consent is accepted when the consent start date is filled in and the consent end date is empty.
	 * 
	 * @param clientId the client id.
	 * @return true if the condition is met.
	 */
	@Query("SELECT CASE WHEN (COUNT(cliId) > 0) THEN true ELSE false END FROM ClientEntity c WHERE c.cliId = :clientId AND (c.gdprStartdate IS NOT NULL AND c.gdprStartdate > '1753-01-01') AND (c.gdprEnddate IS NULL OR c.gdprEnddate <= '1753-01-01')")
	public boolean isClientGdprConsentAccepted(@Param("clientId") Integer clientId);
	
	/** find a Person by ID */
	@Query("select c from ClientEntity c where c.cliId = :id")
	public ClientEntity findById(@Param("id") int id);
}
