package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AccountPaymentDTO;
import lu.wealins.webia.services.core.mapper.AccountPaymentMapper;
import lu.wealins.webia.services.core.persistence.repository.AccountPaymentRepository;
import lu.wealins.webia.services.core.service.AccountPaymentService;

@Service
public class AccountPaymentServiceImpl implements AccountPaymentService {

	private static final String EUR = "EUR";

	@Autowired
	private AccountPaymentRepository accountPaymentRepository;

	@Autowired
	private AccountPaymentMapper accountPaymentMapper;

	@Override
	public AccountPaymentDTO getPrimeAccountPayment(String typeAccount, String bic, String currency) {
		AccountPaymentDTO accountPayment = accountPaymentMapper.asAccountPaymentDTO(accountPaymentRepository.findByTypeAccountAndBicAndCurrency(typeAccount, bic, currency));
		
		if(accountPayment != null) {
			return accountPayment;
		}
		
		accountPayment = accountPaymentMapper.asAccountPaymentDTO(accountPaymentRepository.findByCurrencyAndIsDefault(currency, Boolean.TRUE));

		if (accountPayment != null) {
			return accountPayment;
		}

		return accountPaymentMapper.asAccountPaymentDTO(accountPaymentRepository.findByCurrencyAndIsDefault(EUR, Boolean.TRUE));
	}

	public Collection<AccountPaymentDTO> getAccountPayments(String typeAccount, String bic, String depositAccount) {
		Collection<AccountPaymentDTO> accountPayments = accountPaymentMapper.asAccountPaymentDTOs(accountPaymentRepository.findByTypeAccountAndBicAndDepositAccount(typeAccount, bic, depositAccount));

		if (CollectionUtils.isNotEmpty(accountPayments)) {
			return accountPayments;
		}

		CollectionUtils.addIgnoreNull(accountPayments, accountPaymentMapper.asAccountPaymentDTO(accountPaymentRepository.findByCurrencyAndIsDefault(EUR, Boolean.TRUE)));

		return accountPayments;
	}

}
