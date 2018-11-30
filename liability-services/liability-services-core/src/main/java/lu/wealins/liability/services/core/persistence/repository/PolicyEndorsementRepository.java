package lu.wealins.liability.services.core.persistence.repository; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PolicyEndorsementEntity; 

public interface PolicyEndorsementRepository extends JpaRepository<PolicyEndorsementEntity, Long> {

	@Query("SELECT max(pe.penId) FROM PolicyEndorsementEntity pe")
	long findMaxPenId();

	@Query("SELECT max(pe.endorsementNo) FROM PolicyEndorsementEntity pe where pe.polId = :polId")
	Integer findMaxEndorsementNo(@Param("polId") String polId);

	PolicyEndorsementEntity findBycreatedProcessAndTypeAndCoverage(String createdProcess, String type, Integer coveage);

}
