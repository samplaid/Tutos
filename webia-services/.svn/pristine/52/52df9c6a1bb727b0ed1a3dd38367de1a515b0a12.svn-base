package lu.wealins.webia.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.SapAccountingDTO;

/**
 * ExtractRESTService is a REST service responsible to extract objects or data.
 * 
 * @param <T>
 *
 */
@Path("sapAccountings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface SapAccountingRESTService {

	/**
	 * Extract and create file of Sap Accounting
	 * 
	 * @param context The security context.
	 * @param origin of the datas.
	 * @param fundId of the datas.
	 * @return List of Sap Accounting.
	 */
	@GET
	@Path("/getByOriginId/{originId}")
	public List<SapAccountingDTO> getSapAccountingByOriginId(@Context SecurityContext context, @PathParam("originId") Long originId, @QueryParam("fundId") String fundId);

}
