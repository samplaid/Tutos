/**
 * 
 */
package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.FundFormDTO;

@Path("fundForm")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface FundFormRESTService {

	/**
	 * Save the fund form.
	 * 
	 * @param context the security context
	 * @param fundForm the fund form
	 * @return a new fund form
	 */
	@POST
	@Path("save")
	FundFormDTO save(@Context SecurityContext context, FundFormDTO fundForm);
}
