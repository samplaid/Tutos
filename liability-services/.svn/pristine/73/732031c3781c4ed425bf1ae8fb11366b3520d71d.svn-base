package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AdminFeesDTO;

@RolesAllowed("rest-user")
@Path("/accountTransaction")
@Produces(MediaType.APPLICATION_JSON)
public interface AccountTransactionRESTService {

	@GET
	@Path("/adminFees")
	Collection<AdminFeesDTO> getAdminFees(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("effectiveDate") String effectiveDate);

}
