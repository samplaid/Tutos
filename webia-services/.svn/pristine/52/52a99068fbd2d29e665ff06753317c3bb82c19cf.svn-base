/**
 * 
 */
package lu.wealins.webia.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.AcceptanceReportDTO;

/**
 * This interface provides method to allow generate the acceptance report. <br>
 * It uses the check data and check step to retrieve the data based on the workflow id.
 * 
 * @author oro
 *
 */
@Path("acceptanceReport")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface AcceptanceReportRESTService {

	/**
	 * Generates the acceptance report related to the workflow id.
	 * 
	 * @param workflowId the workflow id
	 */
	@GET
	@Path("/find")
	List<AcceptanceReportDTO> retrieveAcceptanceReport(@Context SecurityContext context, @QueryParam("workflowId") int workflowId);
}
