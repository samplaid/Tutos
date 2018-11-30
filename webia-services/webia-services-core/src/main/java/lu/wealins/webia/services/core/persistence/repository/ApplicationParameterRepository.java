package lu.wealins.webia.services.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.ApplicationParameterEntity;

public interface ApplicationParameterRepository extends JpaRepository<ApplicationParameterEntity, String> {

	public ApplicationParameterEntity findByCode(String code);
	
}
