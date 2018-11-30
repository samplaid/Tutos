package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.AccountPaymentDTO;

public interface WebiaAccountPaymentService {

	AccountPaymentDTO getPrimeAccountPayment(String typeAccount, String bic, String currency);

	Collection<AccountPaymentDTO> getAccountPayments(String typeAccount, String bic, String depositAccount);
}
