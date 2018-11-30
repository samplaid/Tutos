package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@RolesAllowed("rest-user")
@Path("/coverLetter")
@Produces(MediaType.APPLICATION_JSON)
public interface CoverLetterEditingRESTService {

	@GET
	@Path("/mandate")
	String mandate(
			@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("fundId") String fundId);

}
