package lu.wealins.webia.ws.rest.impl;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.AdditionalPremiumService;
import lu.wealins.webia.ws.rest.AdditionalPremiumRESTService;

@Component
@Path("additionalPremium")
@RolesAllowed("rest-user")
public class AdditionalPremiumRESTServiceImpl implements AdditionalPremiumRESTService {

	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";

	@Autowired
	private AdditionalPremiumService additionalPremiumService;

	@Override
	@GET
	@Path("/initAdditionalPremium")
	@Produces(MediaType.APPLICATION_JSON)
	public AppFormDTO initAdditionalPremium(@Context SecurityContext context, @QueryParam("policyId") String policyId, @QueryParam("workflowItemId") Integer workflowItemId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return additionalPremiumService.initAdditionalPremium(policyId, workflowItemId);
	}

	@Override
	@GET
	@Path("/recreateAdditionalPremium")
	public AppFormDTO recreate(@Context SecurityContext context, @QueryParam("workflowItemId") Integer workflowItemId) {
		return additionalPremiumService.recreate(context, workflowItemId);
	}
}
