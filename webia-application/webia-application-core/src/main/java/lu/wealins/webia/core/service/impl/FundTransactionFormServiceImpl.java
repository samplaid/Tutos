package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.webia.services.AmountDTO;
import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;
import lu.wealins.common.dto.webia.services.enums.TransactionType;
import lu.wealins.webia.core.service.FundFormTransactionService;

@Service
public class FundTransactionFormServiceImpl extends AbstractFundFormService<FundTransactionFormDTO> implements FundFormTransactionService {

	private static final BigDecimal HUNDRED = new BigDecimal(100);

	@Override
	public Collection<FundTransactionFormDTO> getFundForms(String policyId) {
		Collection<FundLiteDTO> investedFunds = fundService.getInvestedFunds(policyId);
		return investedFunds.stream().map(this::toFundForm).collect(Collectors.toList());
	}

	private FundTransactionFormDTO toFundForm(FundLiteDTO fund) {
		FundTransactionFormDTO fundForm = new FundTransactionFormDTO();
		fundForm.setFund(fund);
		fundForm.setFundTp(fund.getFundSubType());
		fundForm.setFundId(fund.getFdsId());
		return fundForm;
	}

	@Override
	public AmountDTO getTransactionFundAmount(TransactionFormDTO transactionForm, FundTransactionFormDTO fundTransactionForm, PolicyValuationHoldingDTO policyValuationHolding) {
		if (TransactionType.RAT.equals(transactionForm.getTransactionType())) {
			return new AmountDTO(policyValuationHolding.getHoldingValueFundCurreny(), policyValuationHolding.getFundCurrency());
		}
		if (BooleanUtils.isFalse(transactionForm.getSpecificAmountByFund())) {
			boolean isOnlyOneFund = transactionForm.getFundTransactionForms().size() == 1;

			if (isOnlyOneFund) {
				Assert.isTrue(transactionForm.getFundTransactionForms().iterator().next().getFundId().equals(fundTransactionForm.getFundId()), "Fund ids are not the same!");

				return new AmountDTO(transactionForm.getAmount(), transactionForm.getCurrency());
			}
			throw new IllegalStateException("Impossible to predict the amount split by fund.");
		}

		BigDecimal fundAmount = null;
		BigDecimal fundTransactionFormAmount = fundTransactionForm.getAmount();
		FundTransactionInputType inputType = fundTransactionForm.getInputType();

		if (inputType == null) {
			return null;
		}

		switch (inputType) {
		case GROSS_AMOUNT:
			fundAmount = fundTransactionFormAmount;
			break;
		case UNITS:
			BigDecimal price = policyValuationHolding.getPrice();
			BigDecimal units = fundTransactionForm.getUnits();

			if (price != null && units != null) {
				fundAmount = price.multiply(units);
			}
			break;
		case ALL_FUND:
			fundAmount = policyValuationHolding.getHoldingValueFundCurreny();
			break;
		case PERCENTAGE:
			if (policyValuationHolding.getHoldingValueFundCurreny() != null && fundTransactionForm.getPercentage() != null) {
				fundAmount = policyValuationHolding.getHoldingValueFundCurreny().multiply(fundTransactionForm.getPercentage()).divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP).abs();
			}
			break;
		default:
			throw new UnsupportedOperationException(String.format("Unknown fund transaction input type : %s", inputType));
		}

		return new AmountDTO(fundAmount, policyValuationHolding.getFundCurrency());
	}

}
