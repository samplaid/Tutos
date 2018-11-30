package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.BatchInjectionControlResponse;
import lu.wealins.common.dto.liability.services.PriceInjectionControlRequest;

@Path("batch/priceInjection")
@Produces(MediaType.APPLICATION_JSON)
public interface PriceInjectionRESTService {

	@POST
	@Path("check")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("rest-user")
	BatchInjectionControlResponse checkPriceInjection(@Context SecurityContext context, PriceInjectionControlRequest request);

}
