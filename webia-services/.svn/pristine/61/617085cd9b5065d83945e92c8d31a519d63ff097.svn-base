package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.FindSapEncashmentsResponse;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingResponse;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsRequest;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsResponse;

/**
 * ExtractRESTService is a REST service responsible to extract objects or data.
 * 
 * @param <T>
 *
 */
@Path("encashments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface EncashmentRESTService {

	/**
	 * Extract and create file of Sap Accounting
	 * 
	 * @param context The security context.
	 * @param origin of the datas.
	 * @return List of Sap Accounting.
	 */
	@GET
	@Path("/getSapEncashments")
	public FindSapEncashmentsResponse getSapEncashments(@Context SecurityContext context);

	@POST
	@Path("/saveSapAccountings")
	public SaveEncashmentsInSapAccountingResponse saveEncashmentsInSAPAccounting(@Context SecurityContext context, SaveEncashmentsInSapAccountingRequest request);

	@POST
	@Path("/updateEncashments")
	public UpdateEncashmentsResponse updateEncashments(@Context SecurityContext context, UpdateEncashmentsRequest request);

}
