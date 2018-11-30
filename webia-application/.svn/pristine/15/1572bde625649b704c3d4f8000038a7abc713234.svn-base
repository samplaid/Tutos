package lu.wealins.webia.core.service.validations.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.validations.EncashmentFundFormValidationService;

@Service
public class EncashmentFundFormValidationServiceImpl implements EncashmentFundFormValidationService {

	@Autowired
	private LiabilityFundService fundService;

	@Override
	public void validateEncashmentFundForms(Collection<FundFormDTO> fundForms, Date paymentDt, List<String> errors) {

		for (FundFormDTO fundForm : fundForms) {
			FundLiteDTO fund = fundForm.getFund();
			if (fund != null && fundService.isFIDorFAS(fund)) {
				if (fundForm.getIsCashFundAccount() == null && !BigDecimal.ZERO.equals(fundForm.getSplit())) {
					errors.add("Encashment account is mandatory for the fund " + fundForm.getFundId() + ".");
				}

				Boolean canAddFIDorFASFundValuationAmount = fundService.canAddFIDorFASFundValuationAmount(fund.getFdsId(), paymentDt, (short) 1);
				Boolean isAmountNotPresent = fundForm.getValuationAmt() == null;

				if (canAddFIDorFASFundValuationAmount && isAmountNotPresent) {
					errors.add("Valuation amount is mandatory for the fund " + fundForm.getFundId() + ".");
				}
			}
		}

	}
}