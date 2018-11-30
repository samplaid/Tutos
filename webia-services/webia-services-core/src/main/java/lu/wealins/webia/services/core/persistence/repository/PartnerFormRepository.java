package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;

public interface PartnerFormRepository extends JpaRepository<PartnerFormEntity, Integer>, JpaSpecificationExecutor<PartnerFormEntity> {

	List<PartnerFormEntity> findByFormId(Integer formId);
}
