package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.AppFormEntity;

public interface AppFormRepository extends JpaRepository<AppFormEntity, Integer> {

	AppFormEntity findByFormId(Integer formId);

	AppFormEntity findByWorkflowItemId(Integer workflowItemId);

	List<AppFormEntity> findByPolicyId(String policyId);
}
