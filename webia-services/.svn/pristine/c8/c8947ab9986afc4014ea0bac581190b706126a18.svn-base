package lu.wealins.webia.services.ws.rest.impl;

import java.util.stream.Collectors;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TaxBatchResponse;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsRequest;
import lu.wealins.webia.services.core.service.TaxBatchService;
import lu.wealins.webia.services.ws.rest.TaxBatchRESTService;

@Component
public class TaxBatchRESTServiceImpl implements TaxBatchRESTService {

	@Autowired
	private TaxBatchService taxBatchService;

	@Override
	public TaxBatchResponse createTransactionTaxDetails(SecurityContext context) {
		TaxBatchResponse response = new TaxBatchResponse();
		response.setTransactionTaxIds(taxBatchService.createTransactionTaxDetails());
		return response;
	}

	@Override
	public TaxBatchResponse createTransactionTaxDetails(SecurityContext context, TransactionTaxDetailsRequest request) {
		TaxBatchResponse response = new TaxBatchResponse();
		taxBatchService.createTransactionTaxDetails(request.getTransactionTax(), request.getFrenchTaxable() == null?false:request.getFrenchTaxable());
		response.setTransactionTaxIds(request.getTransactionTax().stream()
				.map(transactionTax -> transactionTax.getTransactionTaxId()).collect(Collectors.toList()));
		return response;
	}
}

