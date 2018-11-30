package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;

public interface FundFormRepository extends JpaRepository<FundFormEntity, Integer> {

	List<FundFormEntity> findByFormId(Integer formId);

}
