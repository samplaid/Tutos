package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.TransactionFormEntity;

public interface TransactionFormRepository extends JpaRepository<TransactionFormEntity, Integer> {

	TransactionFormEntity findByFormId(Integer formId);

	TransactionFormEntity findByWorkflowItemId(Integer workflowItemId);

	List<TransactionFormEntity> findByPolicyId(String policyId);

}
