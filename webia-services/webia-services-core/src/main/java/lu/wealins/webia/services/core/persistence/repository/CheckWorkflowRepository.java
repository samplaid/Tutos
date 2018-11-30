package lu.wealins.webia.services.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.CheckWorkflowEntity;

public interface CheckWorkflowRepository extends JpaRepository<CheckWorkflowEntity, Integer> {

	CheckWorkflowEntity findBycheckCode(String checkCode);
}
