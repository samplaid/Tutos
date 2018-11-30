package lu.wealins.liability.services.core.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.FundEntity;

public interface FundRepository extends JpaRepository<FundEntity, String>, JpaSpecificationExecutor<FundEntity> {

	List<FundEntity> findByName(String name);

	List<FundEntity> findByNameLike(String pattern);

	@Query("SELECT f.fdsId FROM FundEntity f  WHERE f.fdsId = :fundId")
	String findFsIdByID(@Param("fundId") String fundId);

	@Query("SELECT f FROM FundEntity f, FundHoldingEntity fhe WHERE fhe.fund.fdsId = f.fdsId AND (fhe.units != 0 AND fhe.units IS NOT NULL) AND (f.exportedFund is NULL OR f.exportedFund =0 ) AND f.fundType = 3 AND f.status = 1 AND f.fundSubType IN :pFundSubTypes")
	List<FundEntity> findByFundSubType(@Param("pFundSubTypes") List<String> pFundSubTypes);

	@Query("SELECT f FROM FundEntity f  WHERE  f.isinCode = :isinCode AND f.currency = :currency AND f.status = 1")
	List<FundEntity> findByIsinAndCurrency(@Param("isinCode") String isinCode, @Param("currency") String currency);

	List<FundEntity> findByRiskProfile(String riskProfile);

	@Query("SELECT TRIM(f.fdsId) FROM FundEntity f  WHERE f.depositBank = :depositBank")
	Collection<String> findFdsIdsByDepositBank(@Param("depositBank") String depositBank);

	@Query("SELECT TRIM(f.fdsId) FROM FundEntity f  WHERE f.assetManager = :assetManager")
	Collection<String> findFdsIdsByAssetManager(@Param("assetManager") String assetManager);

	@Query(nativeQuery = true, name = "FundEntity.findInvestedFunds")
	Collection<FundEntity> findInvestedFunds(@Param("polId") String polId);
}
