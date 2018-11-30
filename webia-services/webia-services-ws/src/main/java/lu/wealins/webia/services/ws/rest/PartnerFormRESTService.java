package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormSearchRequest;

@Path("partnerForm")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface PartnerFormRESTService {

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	public PageResult<PartnerFormDTO> search(@Context SecurityContext context, PartnerFormSearchRequest partnerFormSearchRequest);

}
