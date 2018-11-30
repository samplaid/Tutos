package lu.wealins.webia.services.ws.rest;

import java.util.List;
import java.util.Map;

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

import lu.wealins.common.dto.webia.services.CheckDataContainerDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;

@Path("checkData")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CheckDataRESTService {

	/**
	 * Get the check data according its workflow item id and their check codes.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @param checkCode The check codes.
	 * @return The check data.
	 */
	@GET
	@Path("loadMap")
	Map<String, CheckDataDTO> getCheckData(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId, @QueryParam("checkCodes") List<String> checkCodes);

	@GET
	@Path("load")
	CheckDataDTO getCheckData(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId, @QueryParam("checkCode") String checkCode);

	/**
	 * Get the check data according its workflow item id and their check codes.
	 * 
	 * @param context The security context.
	 * @param workflowItemId The workflow item id.
	 * @param checkCode The check codes.
	 * @return The check data.
	 */
	@GET
	@Path("loadByPolicyId")
	CheckDataDTO getCheckData(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("checkId") Integer checkId);

	/**
	 * Update the check data.
	 * 
	 * @param context The security context.
	 * @param checkData The check data.
	 * @return The updated check data.
	 */
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	CheckDataDTO update(@Context SecurityContext context, CheckDataDTO checkData);

	@POST
	@Path("updateAll")
	@Consumes(MediaType.APPLICATION_JSON)
	CheckDataContainerDTO updateAll(@Context SecurityContext context, CheckDataContainerDTO checkData);

	@GET
	@Path("acceptanceDecision")
	CheckDataDTO getAcceptanceDecision(@QueryParam("workflowItemId") Integer workflowItemId);

}
