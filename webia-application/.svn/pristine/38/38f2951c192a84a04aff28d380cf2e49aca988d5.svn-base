package lu.wealins.webia.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.OperationDTO;

@Path("/operation")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface OperationRESTService {

	@GET
	@Path("/forPartner")
	@Consumes(MediaType.APPLICATION_JSON)
	Collection<OperationDTO> getPartnerOpeningOperations(@Context SecurityContext context,  @QueryParam("partnerId") String partnerId, @QueryParam("partnerCategory") String partnerCategory);

	@GET
	@Path("/forPolicy")
	@Consumes(MediaType.APPLICATION_JSON)
	Collection<OperationDTO> getPolicyOpeningOperations(@Context SecurityContext context, @QueryParam("policyId") String policyId);

}
