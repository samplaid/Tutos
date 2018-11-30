package lu.wealins.webia.services.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.webia.services.core.persistence.entity.WorkflowUserEntity;

public interface WorkflowUserRepository extends JpaRepository<WorkflowUserEntity, Integer> {

	WorkflowUserEntity findByUsrId(String usrId);

	WorkflowUserEntity findByLoginId(String login);

	WorkflowUserEntity findByUserToken(String userToken);
}
