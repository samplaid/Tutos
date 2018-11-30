package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;

@Path("liabilityBeneficiaryChangeForm")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface LiabilityBeneficiaryChangeFormRESTService {

	@GET
	@Path("/initBeneficiaryChange")
	BeneficiaryChangeFormDTO initBeneficiaryChangeForm(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("workflowItemId") Integer workflowItemId);

	@GET
	@Path("/getBeneficiaryChangeForm")
	BeneficiaryChangeFormDTO getBeneficiaryChangeForm(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("workflowItemId") Integer workflowItemId);

	@GET
	@Path("/getBeneficiaryChange")
	BeneficiaryChangeDTO getBeneficiaryChange(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("workflowItemId") Integer workflowItemId, @QueryParam("productCd") String productCd, @QueryParam("lang") String lang);

}
