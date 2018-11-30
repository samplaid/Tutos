package lu.wealins.webia.services.core.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.webia.services.EncashmentFundFormDTO;
import lu.wealins.common.dto.webia.services.FindSapEncashmentsResponse;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingResponse;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsRequest;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsResponse;
import lu.wealins.webia.services.core.persistence.entity.EncashmentFundFormEntity;
import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;

public interface EncashmentFundFormService {

	Collection<EncashmentFundFormDTO> getEncashmentFundForms(Integer formId);

	Collection<EncashmentFundFormDTO> getEncashmentFundForms(Integer formId, String fundId);

	void updateEncashmentFundForms(Collection<EncashmentFundFormDTO> encashmentFundForms, Integer formId, String fundId, BigDecimal splitAmt, String contractCurrency, Date paymentDt);

	EncashmentFundFormEntity save(EncashmentFundFormDTO encashmentFundForm);
	
	FindSapEncashmentsResponse findSapEncashments();

	SaveEncashmentsInSapAccountingResponse saveInSapAccounting(SaveEncashmentsInSapAccountingRequest request);

	UpdateEncashmentsResponse updateEncashments(UpdateEncashmentsRequest request);

	/**
	 * Delete or cancel the EncashmentFundFormEntity currently in state NEW or NEW_POSTED of the provided {@link FundFormDTO}.
	 * 
	 * @param fundForm the fund form
	 * @return the {@link Collection} of {@link EncashmentFundFormEntity} of the provided {@link FundFormEntity} after the process
	 */
	void deleteOrCancel(FundFormEntity fundForm);

	Collection<EncashmentFundFormDTO> getActiveEncashmentFundForm(Collection<EncashmentFundFormDTO> encashmentFundForm);

	void deleteOrCancel(FundFormDTO updatedFundForm);

}
