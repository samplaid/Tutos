package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.services.core.service.FundFormService;
import lu.wealins.webia.services.core.service.validations.appform.InvestmentFundValidationService;

@Service
public class InvestmentFundValidationServiceImpl implements InvestmentFundValidationService {

	private static final String INVESTMENT_REQUIRED = "The investment is mandatory.";
	private static final String INVESTMENT_PARTS_SUM_EQ_100 = "The sum of the investment parts should be equal to 100%.";

	@Autowired
	private FundFormService fundFormService;

	@Override
	public void validateFundPartEq100(AppFormDTO form, List<String> errors) {
		Assert.notNull(errors);

		if (CollectionUtils.isNotEmpty(form.getFundForms())) {
			// Rule2: the sum of the fund parts should be equal to 100%
			Supplier<Stream<BigDecimal>> parts = () -> form.getFundForms().stream().map(f -> f.getSplit()).filter(part -> part != null);
			if (parts.get().count() > 0 && parts.get().reduce(BigDecimal.ZERO, (f1, f2) -> f1.add(f2))
					.compareTo(new BigDecimal("100")) != 0) {
				errors.add(INVESTMENT_PARTS_SUM_EQ_100);
			}
		}
	}

	@Override
	public void validateFundNotNull(AppFormDTO form, List<String> errors) {
		Assert.notNull(errors);

		if (CollectionUtils.isEmpty(form.getFundForms())) {
			errors.add(INVESTMENT_REQUIRED);
		}
	}

	@Override
	public void validateFundPartNotNull(AppFormDTO form, List<String> errors) {
		Assert.notNull(errors);

		if (CollectionUtils.isNotEmpty(form.getFundForms())) {
			form.getFundForms().stream().forEach(fund -> {
				if (fund.getSplit() == null) {
					errors.add("The investment part of the fund " + fund.getFundId() + " is mandatory.");
				}
			});
		}
	}

	@Override
	public void validateFidAndFasHaveAmoutValuation(AppFormDTO form, List<String> errors) {
		Assert.notNull(errors);

		form.getFundForms().stream().forEach(fund -> {
			if (fundFormService.isFidOrFas(fund) && fund.getValuationAmt() == null) {
				errors.add("The amount valuation of the fund " + fund.getFundId() + " is mandatory.");
			}
		});

	}

}
