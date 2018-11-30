package lu.wealins.webia.core.service;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.webia.ws.rest.request.EditingRequest;

public interface SepaPaymentGenerationService {

	EditingRequest generateSepaPayment(SecurityContext context, EditingRequest request);

}
