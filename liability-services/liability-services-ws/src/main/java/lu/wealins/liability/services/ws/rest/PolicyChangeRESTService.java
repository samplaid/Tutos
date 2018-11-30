package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeRequest;

@Path("/policyChange")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface PolicyChangeRESTService {

	@GET
	PolicyChangeDTO getPolicyChange(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId);

	@GET
	@Path("/all")
	Collection<PolicyChangeDTO> getPolicyChanges(@Context SecurityContext context, @QueryParam("policyId") String policyId);
	
	@GET()
	@Path("/cancelByworkflowItemId")
	void cancelByworkflowItemId(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId);
	
	@POST
	@Path("/saveChanges")
	Boolean saveChanges(@Context SecurityContext context, PolicyChangeRequest policyChangeRequest);

}
