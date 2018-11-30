package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.common.dto.webia.services.constantes.CommissionConstant;
import lu.wealins.webia.services.core.persistence.entity.SapOpenBalanceEntity;

public interface SapOpenBalanceRepository extends JpaRepository<SapOpenBalanceEntity, Long> {
	
	/**
	 * Find all reconcilable open balance by type.
	 * 
	 * @param commissionTypes the commission type.
	 * @return a list of reconcilable open balance.
	 */
	@Query("SELECT sobe "
			+ "FROM SapOpenBalanceEntity sobe "
			+ "WHERE sobe.commissionType = :commissionType "
			+ "AND (sobe.status = '" + CommissionConstant.VALIDATED + "' OR sobe.status is NULL) "
	)
	List<SapOpenBalanceEntity> findReconcilableOpenBalanceByType(@Param("commissionType") String commissionType);
	
	
}
