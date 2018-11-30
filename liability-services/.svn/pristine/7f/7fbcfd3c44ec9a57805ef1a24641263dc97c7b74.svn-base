package lu.wealins.liability.services.core.persistence.repository; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.PostingSetEntity; 
public interface PostingSetRepository extends JpaRepository<PostingSetEntity, Long> {
	
	@Modifying
	@Query("UPDATE PostingSetEntity pst SET pst.comStatus = :comStatus " +
			"WHERE pst.pstId = :pstId")
	public void updateComStatus(@Param("pstId") long pstId, @Param("comStatus") Integer comStatus);
	
	@Modifying
	@Query("UPDATE PostingSetEntity pst SET pst.reportStatus = :reportStatus " +
			"WHERE pst.pstId = :pstId")
	public void updateReportStatus(@Param("pstId") long pstId, @Param("reportStatus") Integer reportStatus);
	
} 
