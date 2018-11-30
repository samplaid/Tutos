package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PolicyTransferDTO;

@Path("/policytransfer")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface PolicyTransferRESTService {

	@GET
	Collection<PolicyTransferDTO> getPolicyTransfers(@Context SecurityContext context,
			@QueryParam("policy") String policy);
}
