package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.enums.TransactionTaxType;



@Component
public class PrimeDetailsGenerator implements TransactionTaxDetailsGenerator {

	private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
	private static final int SCALE = 8;

	@Override
	public List<TransactionTaxDetailsDTO> generateTransactionTaxDetails(TransactionTaxDTO transactionTax, List<TransactionTaxDetailsDTO> previousDetails, boolean frenchTaxable) {

		List<TransactionTaxDetailsDTO> result = new ArrayList<>();
		if (!CollectionUtils.isEmpty(previousDetails)) {
			result.addAll(replicatePrevious(transactionTax, previousDetails));
		}
		result.add(newDetail(transactionTax));

		adjustSplitPercentage(result);
		return result;
	}

	@Override
	public boolean supportsType(String transactionType) {
		return TransactionTaxType.PREM.getCode().equals(transactionType);
	}

	private List<TransactionTaxDetailsDTO> replicatePrevious(TransactionTaxDTO transactionTax, List<TransactionTaxDetailsDTO> previousDetails) {
		return previousDetails.stream().map(previousDetail -> {
			TransactionTaxDetailsDTO replicatedDetail = new TransactionTaxDetailsDTO();
			replicatedDetail.setTransactionTaxId(transactionTax.getTransactionTaxId());
			replicatedDetail.setPremiumDate(previousDetail.getPremiumDate());
			replicatedDetail.setPremiumValueBefore(previousDetail.getPremiumValueAfter());
			replicatedDetail.setPremiumValueAfter(previousDetail.getPremiumValueAfter());
			return replicatedDetail;
		}).collect(Collectors.toList());
	}

	private TransactionTaxDetailsDTO newDetail(TransactionTaxDTO transactionTax) {
		TransactionTaxDetailsDTO result = new TransactionTaxDetailsDTO();
		result.setTransactionTaxId(transactionTax.getTransactionTaxId());
		result.setPremiumDate(transactionTax.getTransactionDate());
		result.setPremiumValueBefore(BigDecimal.valueOf(0));
		result.setPremiumValueAfter(transactionTax.getTransactionNetAmount());
		return result;
	}

	private void adjustSplitPercentage(List<TransactionTaxDetailsDTO> transactionTaxDetails) {
		BigDecimal sumPremiumValues = transactionTaxDetails.stream().map(TransactionTaxDetailsDTO::getPremiumValueAfter).reduce(BigDecimal::add).get();
		transactionTaxDetails.forEach(detail -> detail.setSplitPercent(calculatePercentage(detail.getPremiumValueAfter(), sumPremiumValues)));
	}

	private BigDecimal calculatePercentage(BigDecimal premiumValue, BigDecimal sumPremiumValues) {
		if (sumPremiumValues == null || BigDecimal.ZERO.compareTo(sumPremiumValues) == 0) {
			return sumPremiumValues;
		}
		return (premiumValue.multiply(HUNDRED)).divide(sumPremiumValues, SCALE, BigDecimal.ROUND_HALF_UP);
	}
}
