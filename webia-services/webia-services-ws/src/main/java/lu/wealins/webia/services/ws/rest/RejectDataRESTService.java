package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.RejectDataDTO;

@Path("rejectData")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface RejectDataRESTService {

	/**
	 * Get the reject data according its workflow item id.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @return The reject data collection.
	 */
	@GET
	@Path("load")
	Collection<RejectDataDTO> getRejectData(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId);

	/**
	 * Get the reject data according its workflow item id and their step Id.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @param stepId The step Id.
	 * @return The reject data collection.
	 */
	@GET
	@Path("loadByStepId")
	Collection<RejectDataDTO> getRejectData(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId, @QueryParam("stepId") Integer stepId);

	/**
	 * save the reject data.
	 * 
	 * @param context The security context.
	 * @param rejectData The reject data.
	 * @return The updated reject data.
	 */
	@POST
	@Path("save")
	@Consumes(MediaType.APPLICATION_JSON)
	RejectDataDTO save(@Context SecurityContext context, RejectDataDTO rejectData);

}
