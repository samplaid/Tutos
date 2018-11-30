package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.AccountPaymentDTO;
import lu.wealins.webia.core.service.WebiaAccountPaymentService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class WebiaAccountPaymentServiceImpl implements WebiaAccountPaymentService {

	private static final String EMPTY_STRING = "";
	private static final String WEBIA_ACCOUNT_PAYMENT = "webia/accountPayment/";
	private static final String PRIME_ACCOUNT = "primeAccount";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public AccountPaymentDTO getPrimeAccountPayment(String typeAccount, String bic, String currency) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("typeAccount", typeAccount);
		params.add("bic", bic);
		params.add("currency", currency);

		return restClientUtils.get(WEBIA_ACCOUNT_PAYMENT, PRIME_ACCOUNT, params, AccountPaymentDTO.class);

	}

	@Override
	public Collection<AccountPaymentDTO> getAccountPayments(String typeAccount, String bic, String depositAccount) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("typeAccount", typeAccount);
		params.add("bic", bic);
		params.add("depositAccount", depositAccount);

		return restClientUtils.get(WEBIA_ACCOUNT_PAYMENT, EMPTY_STRING, params, new GenericType<Collection<AccountPaymentDTO>>() {
		});

	}
}
