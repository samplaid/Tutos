package lu.wealins.webia.services.core.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import lu.wealins.common.dto.webia.services.AbstractFundFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;

public interface FundFormService {

	Collection<FundFormEntity> getFundForms(Integer formId);

	Collection<FundFormEntity> update(Collection<FundFormDTO> fundForms, Integer formId, BigDecimal paymentAmt, String contractCurrency, Date paymentDt);

	void delete(Collection<FundFormEntity> fundForms);

	FundFormDTO update(FundFormDTO fundForm);

	<T extends AbstractFundFormDTO> boolean isFidOrFas(T fundForm);

	<T extends AbstractFundFormDTO> boolean isFeOrFic(T fundForm);

	<T extends AbstractFundFormDTO> boolean containsFeOrFic(Collection<T> fundForms);

	<T extends AbstractFundFormDTO> Collection<T> getFidOrFas(Collection<T> fundForms);

	void resetCashAccount(FundFormEntity fundForm);
}
