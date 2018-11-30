package lu.wealins.liability.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.ApplyBeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.UpdateBeneficiaryChangeRequest;

@RolesAllowed("rest-user")
@Path("/beneficiaryChange")
@Produces(MediaType.APPLICATION_JSON)
public interface BeneficiaryChangeRESTService {

	@POST
	@Path("/clientRoleView")
	BeneficiaryChangeDTO getBeneficiaryChange(@Context SecurityContext context, BeneficiaryChangeRequest beneficiaryChangeRequest);

	@POST
	@Path("/update")
	BeneficiaryChangeDTO updateBeneficiaryChange(@Context SecurityContext context, UpdateBeneficiaryChangeRequest updateBeneficiaryChangeRequest);

	@POST
	@Path("/initBeneficiaryChange/clientRoleView")
	BeneficiaryChangeDTO initBeneficiaryChange(@Context SecurityContext context, BeneficiaryChangeRequest beneficiaryChangeRequest);

	@POST
	@Path("/applyChange")
	BeneficiaryChangeDTO applyBeneficiaryChange(@Context SecurityContext context, ApplyBeneficiaryChangeRequest applyBeneficiaryChangeRequest);
	
}
