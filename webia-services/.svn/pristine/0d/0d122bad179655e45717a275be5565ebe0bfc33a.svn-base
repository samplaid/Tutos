package lu.wealins.webia.services.ws.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;

/**
 * ApplicationParameterRESTService is a REST service responsible to manipulate ApplicationParameter objects.
 * 
 * @param <T>
 *
 */
@Path("applicationParameter")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface ApplicationParameterRESTService {

	@GET
	@Path("{code}")
	ApplicationParameterDTO getApplicationParameter(@Context SecurityContext context, @PathParam("code") String code);
	
	@GET
	@Path("/list/{code}")
	List<String> getApplicationParameters(@Context SecurityContext context, @PathParam("code") String code);

	/**
	 * Get the integer value of the application parameter.
	 * 
	 * @param code The application parameter code.
	 * @return The integer value of the application parameter.
	 */
	@GET
	@Path("/intValue")
	Integer getIntegerValue(@Context SecurityContext context, @QueryParam("code") String code);

	/**
	 * Get list of integers from the application parameter code.
	 * 
	 * @param code The application parameter code.
	 * @return The list of integers.
	 */
	@GET
	@Path("/intValueList")
	List<Integer> getIntegerValues(@Context SecurityContext context, @QueryParam("code") String code);

	@GET
	@Path("/bigDecimalValue")
	BigDecimal getBigDecimalValue(@Context SecurityContext context, @QueryParam("code") String code);
}
