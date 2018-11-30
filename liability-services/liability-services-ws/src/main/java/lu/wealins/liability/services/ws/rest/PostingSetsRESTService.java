package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;

/**
 * 
 * @author xqv99
 *
 */
@Path("posting-sets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface PostingSetsRESTService {
	@POST
	@Path("/update-sap-status")
	public Response UpdateSapStatus(@Context SecurityContext context, Collection<PstPostingSetsDTO> request);
	
	@POST
	@Path("/update-com-status")
	public Response UpdateComStatus(@Context SecurityContext context, Collection<PstPostingSetsDTO> request);
	
	@POST
	@Path("/update-report-status")
	public Response UpdateReportStatus(@Context SecurityContext context, Collection<PstPostingSetsDTO> request);
}
