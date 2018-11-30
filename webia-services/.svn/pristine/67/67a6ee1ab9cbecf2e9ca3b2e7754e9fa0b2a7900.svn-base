package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;

/**
 * This Web service acts as a proxy for TransactionValidationService
 *
 */
@Path("transactionValidation")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface TransactionValidationRestService {

	@POST
	@Path("validateMandatorySpecificAmount")
	public Collection<String> validateMandatorySpecificAmount(TransactionFormDTO transaction);
}
