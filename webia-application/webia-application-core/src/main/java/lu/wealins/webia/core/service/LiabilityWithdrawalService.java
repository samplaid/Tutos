package lu.wealins.webia.core.service;

import java.math.BigDecimal;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;

public interface LiabilityWithdrawalService {

	BigDecimal getTransactionAmount(TransactionFormDTO formData, String currency);
}
