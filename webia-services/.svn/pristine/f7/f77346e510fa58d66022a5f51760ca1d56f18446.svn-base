package lu.wealins.webia.services.ws.rest;

import java.util.List;

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
import lu.wealins.common.dto.webia.services.AppFormEntryDTO;
import lu.wealins.common.dto.webia.services.AppFormEntryRequest;
import lu.wealins.common.dto.webia.services.PageResult;

@Path("appFormEntry")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface AppFormEntryRESTService {

	/**
	 * Get app form entries.
	 * 
	 * @param context The security context.
	 * @param page The page number.
	 * @param size Elements by page.
	 * @param fundId The fund id.
	 * @param excludedPolicies The excluded policies.
	 * @param status The status.
	 * @return The app form entries.
	 */
	@GET
	PageResult<AppFormEntryDTO> getAppFormEntries(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("fundId") String fundId,
			@QueryParam("clientId") Integer clientId,
			@QueryParam("excludedPolicies") List<String> excludedPolicies, @QueryParam("status") List<String> status);

	@GET
	@Path("appFormEntriesForBroker")
	PageResult<AppFormEntryDTO> getAppFormEntriesForBroker(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("partnerId") String partnerId,
			@QueryParam("status") List<String> status);

	@POST
	@Path("appFormEntriesForPartner")
	PageResult<AppFormEntryDTO> getAppFormEntries(@Context SecurityContext context, AppFormEntryRequest request);

	@GET
	@Path("appFormEntriesForPolicy")
	PageResult<AppFormEntryDTO> getAppFormEntriesForPolicy(@Context SecurityContext context, @QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("policyId") String policyId,
			@QueryParam("status") List<String> status);
	@GET
	@Path("appForm/{id}")
	@Deprecated
	// Use the AppFormRESTService instead of.
	AppFormDTO getAppForm(@Context SecurityContext context, @PathParam("id") int id);

}
