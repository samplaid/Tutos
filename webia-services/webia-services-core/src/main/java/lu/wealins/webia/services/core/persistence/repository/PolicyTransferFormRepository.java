package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.BenefClauseFormEntity;
import lu.wealins.webia.services.core.persistence.entity.PolicyTransferFormEntity;

public interface PolicyTransferFormRepository extends JpaRepository<PolicyTransferFormEntity, Integer> {

	List<PolicyTransferFormEntity> findByFormId(Integer formId);
}
