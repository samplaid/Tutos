package lu.wealins.liability.services.core.persistence.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.PolicyTransferEntity;

public interface PolicyTransferRepository extends JpaRepository<PolicyTransferEntity, Long> {

	public Collection<PolicyTransferEntity> findByPolicy(String policy);

	public Collection<PolicyTransferEntity> findByPolicyAndCoverage(String policy, Integer coverage);

	public PolicyTransferEntity findByPolicyAndCoverageAndFromPolicy(String policy, Integer coverage, String fromPolicy);

}
