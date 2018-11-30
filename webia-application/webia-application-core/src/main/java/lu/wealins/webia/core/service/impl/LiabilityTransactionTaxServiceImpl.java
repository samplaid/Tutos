package lu.wealins.webia.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.InsertTransactionTaxRequest;
import lu.wealins.common.dto.webia.services.InsertTransactionTaxResponse;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.webia.core.service.LiabilityTransactionTaxService;
import lu.wealins.webia.core.utils.RestClientUtils;


@Service
public class LiabilityTransactionTaxServiceImpl implements LiabilityTransactionTaxService {

	private static final String INSERT_LISSIA_TRANSACTIONS_FOR_FRENCHTAX = "webia/transactiontax/insert";


	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Autowired
	private RestClientUtils restClientUtils;


	@Override
	public List<TransactionTaxDTO> insertTransactionTax(
			List<TransactionTaxDTO> transactionTaxList) {

		InsertTransactionTaxRequest request = new InsertTransactionTaxRequest();
		request.setTransactionTaxList(transactionTaxList);

		InsertTransactionTaxResponse response = restClientUtils.post(INSERT_LISSIA_TRANSACTIONS_FOR_FRENCHTAX, request,
				InsertTransactionTaxResponse.class);

		return response.getTransactionTaxList();

	}

}
