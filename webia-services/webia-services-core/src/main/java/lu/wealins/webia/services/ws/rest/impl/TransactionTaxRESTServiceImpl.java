package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.SurrenderReportResultDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxResponse;
import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;
import lu.wealins.webia.services.core.service.TransactionTaxDetailsService;
import lu.wealins.webia.services.core.service.TransactionTaxService;
import lu.wealins.webia.services.core.utils.SurrenderResultUtils;
import lu.wealins.webia.services.ws.rest.TransactionTaxRESTService;


@Component
@Transactional
public class TransactionTaxRESTServiceImpl implements TransactionTaxRESTService {

	@Autowired
	TransactionTaxService transactionTaxService;

	@Autowired
	SurrenderResultUtils surrenderResultUtils;

	@Autowired
	TransactionTaxDetailsService transactionTaxDetailsService;

	@Override
	public TransactionTaxResponse insertTransactionTax(@Context SecurityContext context, TransactionTaxResponse request) {
		TransactionTaxResponse response = new TransactionTaxResponse();
		response.setTransactionTaxList(transactionTaxService.insertTransactionTax(request.getTransactionTaxList()));
		return response;
	}

	@Override
	public List<TransactionTaxDetailsDTO> getTransactionTaxDetails(SecurityContext context, Long transactionTaxId) {
		return transactionTaxDetailsService.getByTransactionTaxId(transactionTaxId);
	}

	@Override
	public TransactionTaxDTO getTransactionTax(SecurityContext context, Long id) {
		return transactionTaxService.getTransactionTax(id);
	}

	@Override
	public TransactionTaxResponse cancelTransactionTax(SecurityContext context, TransactionTaxResponse request) {
		TransactionTaxResponse response = new TransactionTaxResponse();
		response.setTransactionTaxList(transactionTaxService.cancelTransactionTax(request.getTransactionTaxList()));
		return response;
	}


	@Override
	public List<TransactionTaxDTO> getTransactionTaxByPolicy(SecurityContext context, String policyId) {
		return transactionTaxService.getTransactionsTaxByPolicy(policyId);
	}

	@Override
	public TransactionTaxDTO getTransactionTaxByOriginId(SecurityContext context, Long id) {
		return transactionTaxService.getTransactionTaxByOriginId(id);
	}

	@Override
	public SurrenderReportResultDTO getTransactionTaxReportResultDetails(SecurityContext context, Long id) {
		return transactionTaxService.getTransactionsTaxReportResult(id);
	}


	@Override
	public TransactionTaxSendingDTO getTransactionTaxSending(SecurityContext context, Long id) {
		return transactionTaxService.getTransactionTaxSending(id);
	}

}
