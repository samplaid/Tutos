package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.CodeLabelDTO;

@Path("codeLabel")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CodeLabelRESTService {

	/**
	 * Get code labels according to the type code.
	 * 
	 * @param context The security context.
	 * @param typeCd The type code.
	 * @return the code labels.
	 */
	@GET
	@Path("{typeCd}")
	Collection<CodeLabelDTO> getCodeLabels(@Context SecurityContext context, @PathParam("typeCd") String typeCd);

	/**
	 * Get code label according to the type code and its code.
	 * 
	 * @param context The security context.
	 * @param typeCd The type code.
	 * @return the code label.
	 */
	@GET
	@Path("{typeCd}/{code}")
	CodeLabelDTO getCodeLabel(@Context SecurityContext context, @PathParam("typeCd") String typeCd, @PathParam("code") String code);
}
