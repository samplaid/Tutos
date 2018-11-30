package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransactionTaxDetailsRequest;
import lu.wealins.webia.core.service.TaxBatchService;
import lu.wealins.webia.ws.rest.TaxBatchRESTService;

@Component
public class TaxBatchRESTServiceImpl implements TaxBatchRESTService {

	@Autowired
	private TaxBatchService taxBatchService;

	@Override
	public Response createTransactionTaxDetails(SecurityContext context) {
		taxBatchService.createTransactionTaxDetails();
		return Response.ok().build();
	}

	@Override
	public Response createTransactionTaxDetails(SecurityContext context, TransactionTaxDetailsRequest request) {
		taxBatchService.createTransactionTaxDetails(request);
		return null;
	}

}
