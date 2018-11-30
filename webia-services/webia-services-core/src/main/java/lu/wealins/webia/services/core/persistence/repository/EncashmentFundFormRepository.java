package lu.wealins.webia.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.common.dto.webia.services.enums.EncashmentFormFundStatus;
import lu.wealins.webia.services.core.persistence.entity.EncashmentFundFormEntity;

public interface EncashmentFundFormRepository extends JpaRepository<EncashmentFundFormEntity, Integer> {

	List<EncashmentFundFormEntity> findByFormId(Integer formId);

	List<EncashmentFundFormEntity> findByFormIdAndFundId(Integer formId, String fundId);

	// @Query("SELECT e FROM EncashmentFundFormEntity e WHERE e.cashStatus = lu.wealins.common.dto.webia.services.enums.EncashmentFormFundStatus.NEW OR e.cashStatus =
	// lu.wealins.common.dto.webia.services.enums.EncashmentFormFundStatus.CANCEL")
	// List<EncashmentFundFormEntity> findSapEncashments();

	List<EncashmentFundFormEntity> findByCashStatusIn(List<EncashmentFormFundStatus> status);

}
