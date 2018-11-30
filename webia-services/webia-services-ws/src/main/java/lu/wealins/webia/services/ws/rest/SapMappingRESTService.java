package lu.wealins.webia.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.SapMappingDTO;

/**
 * ExtractRESTService is a REST service responsible to extract objects or data.
 * 
 * @param <T>
 *
 */
@Path("sapMapping")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface SapMappingRESTService {

	/**
	 * get sap mapping by type
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	@GET
	@Path("/type/{type}")
	public List<SapMappingDTO> getSapMapping(@Context SecurityContext context, @PathParam("type") String type);

}
