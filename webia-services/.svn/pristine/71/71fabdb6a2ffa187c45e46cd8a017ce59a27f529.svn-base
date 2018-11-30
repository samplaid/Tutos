package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.enums.TransactionTaxType;


@Component
public class RachatDetailsGenerator implements TransactionTaxDetailsGenerator {


	private static final int SCALE = 8;

	@Override
	public List<TransactionTaxDetailsDTO> generateTransactionTaxDetails(TransactionTaxDTO transactionTax, List<TransactionTaxDetailsDTO> previousDetails,boolean frenchTaxable) {
		BigDecimal totalSurrenderValue = calculateTotalSurrender(previousDetails, transactionTax.getPolicyValue(), transactionTax.getTransactionNetAmount());
		BigDecimal plusValue = transactionTax.getTransactionNetAmount().subtract(totalSurrenderValue);
		
		if(frenchTaxable) {
			BigDecimal correctedSurrenderValue = totalSurrenderValue.min(transactionTax.getTransactionNetAmount());
			return replicateDetails(previousDetails, correctedSurrenderValue, transactionTax, plusValue);
		}
		return replicateDetails(previousDetails, totalSurrenderValue, transactionTax, plusValue);
	}

	@Override
	public boolean supportsType(String transactionType) {
		return !TransactionTaxType.PREM.getCode().equals(transactionType);
	}

	private BigDecimal calculateTotalSurrender(List<TransactionTaxDetailsDTO> previousDetails, BigDecimal policyValue, BigDecimal surrenderValue) {
		BigDecimal sumPremiumValues = previousDetails.stream().map(TransactionTaxDetailsDTO::getPremiumValueAfter).reduce(BigDecimal::add).get();
		return calculateSurrenderPremiums(policyValue, surrenderValue, sumPremiumValues);
	}

	private List<TransactionTaxDetailsDTO> replicateDetails(List<TransactionTaxDetailsDTO> previousDetails, BigDecimal totalSurrenderValue, TransactionTaxDTO transactionTax,
			BigDecimal plusValue) {
		return previousDetails.stream().map(previousDetail -> {
			TransactionTaxDetailsDTO replicatedDetail = new TransactionTaxDetailsDTO();
			replicatedDetail.setTransactionTaxId(transactionTax.getTransactionTaxId());
			replicatedDetail.setPremiumDate(previousDetail.getPremiumDate());
			replicatedDetail.setPremiumValueBefore(previousDetail.getPremiumValueAfter());
			BigDecimal premiumValueAfter = previousDetail.getPremiumValueAfter().subtract(totalSurrenderValue.multiply(previousDetail.getSplitPercent()).divide(BigDecimal.valueOf(100)));
			replicatedDetail.setPremiumValueAfter(premiumValueAfter);
			replicatedDetail.setSplitPercent(previousDetail.getSplitPercent());
			replicatedDetail.setCapitalGainAmount(plusValue.multiply(previousDetail.getSplitPercent()).divide(BigDecimal.valueOf(100)));
			return replicatedDetail;
		}).collect(Collectors.toList());
	}

	public BigDecimal calculateSurrenderPremiums(BigDecimal policyValue, BigDecimal surrenderValue, BigDecimal sumPremiumValues) {

		if (policyValue == null || BigDecimal.ZERO.compareTo(policyValue) == 0) {
			return policyValue;
		}

		return (sumPremiumValues.multiply(surrenderValue)).divide(policyValue, SCALE, BigDecimal.ROUND_HALF_UP);
	}
}
