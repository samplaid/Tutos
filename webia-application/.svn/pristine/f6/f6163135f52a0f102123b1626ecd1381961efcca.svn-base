package lu.wealins.webia.core.service.validations.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.webia.core.service.validations.FundTransactionFormValidationService;

@Service
public class FundTransactionFormValidationServiceImpl implements FundTransactionFormValidationService {

	private static final String FUND_TRANSACTION_FORMS_MUST_BE_NOT_NULL = "Fund transaction forms must be not null.";
	private static final String ERRORS_MUST_BE_NOT_NULL = "Errors must be not null.";

	@Override
	public void validateValuationAmounts(Collection<FundTransactionFormDTO> fundTransactionForms, List<String> errors) {
		Assert.notNull(fundTransactionForms, FUND_TRANSACTION_FORMS_MUST_BE_NOT_NULL);
		Assert.notNull(errors, ERRORS_MUST_BE_NOT_NULL);

		Collection<FundTransactionFormDTO> fidOrFasFundTransactions = fundTransactionForms.stream()
				.filter(ft -> FundSubType.FID.name().equals(ft.getFund().getFundSubType()) || FundSubType.FAS.name().equals(ft.getFund().getFundSubType()))
				.collect(Collectors.toList());

		for (FundTransactionFormDTO fundTransactionForm : fidOrFasFundTransactions) {
			if (fundTransactionForm.getValuationAmt() == null) {
				errors.add("Valuation amount is mandatory for the fund " + fundTransactionForm.getFundId() + ".");
			}
		}

	}

}
