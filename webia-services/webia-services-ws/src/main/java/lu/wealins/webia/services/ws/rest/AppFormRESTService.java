package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.UpdateAppFormCoverageRequest;
import lu.wealins.common.dto.webia.services.UpdateAppFormPolicyDTO;

@Path("appForm")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface AppFormRESTService {

	@GET
	@Path("{formId}")
	AppFormDTO getAppForm(@Context SecurityContext context, @PathParam("formId") Integer formId);

	@POST
	@Path("/updateCoverage")
	Boolean updateCoverage(@Context SecurityContext context, UpdateAppFormCoverageRequest request);

	@POST
	@Path("/updatePolicy")
	AppFormDTO updatePolicy(@Context SecurityContext context, UpdateAppFormPolicyDTO request);

	@GET
	@Path("/policy")
	Collection<AppFormDTO> getAppFormByPolicy(@Context SecurityContext context,
			@QueryParam("policyId") String policyId);

	@GET
	@Path("/workFlowItemId")
	AppFormDTO getAppFormByWorkFlowItemId(@Context SecurityContext context, @QueryParam("workFlowItemId") Integer workFlowItemId);

	@GET
	@Path("/recreate")
	AppFormDTO recreate(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workFlowItemId);
}
