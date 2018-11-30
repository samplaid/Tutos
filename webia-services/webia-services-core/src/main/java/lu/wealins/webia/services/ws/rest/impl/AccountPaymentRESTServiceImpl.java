package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.AccountPaymentDTO;
import lu.wealins.webia.services.core.service.AccountPaymentService;
import lu.wealins.webia.services.ws.rest.AccountPaymentRESTService;

@Component
public class AccountPaymentRESTServiceImpl implements AccountPaymentRESTService {

	@Autowired
	private AccountPaymentService accountPaymentService;

	@Override
	public AccountPaymentDTO getPrimeAccountPayment(String typeAccount, String bic, String currency) {
		return accountPaymentService.getPrimeAccountPayment(typeAccount, bic, currency);
	}

	@Override
	public Collection<AccountPaymentDTO> getAccountPayments(String typeAccount, String bic, String depositAccount) {
		return accountPaymentService.getAccountPayments(typeAccount, bic, depositAccount);
	}

}
