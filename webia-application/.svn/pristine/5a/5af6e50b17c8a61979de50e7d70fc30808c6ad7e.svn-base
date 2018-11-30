package lu.wealins.webia.ws.rest.impl;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyRecreateResponse;
import lu.wealins.common.dto.liability.services.RecreatePolicyDTO;
import lu.wealins.webia.core.service.SubscriptionService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.ws.rest.SubscriptionRESTService;

@Component
@Path("subscription")
@RolesAllowed("rest-user")
public class SubscriptionRESTServiceImpl implements SubscriptionRESTService {

	@Autowired
	private SubscriptionService subsriptionService;

	@Autowired
	private WebiaWorkflowUserService workflowUserService;

	private static final String WORKFLOW_ITEM_NOT_NULL = "The workflow item id can't be null";
	private static final String FORM_ID_NOT_NULL = "The form id can't be null";
	private static final String DTO_NOT_NULL = "The dto can't be null";

	@Override
	@POST
	@Path("recreate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PolicyRecreateResponse recreatePolicy(@Context SecurityContext context, RecreatePolicyDTO recreatePolicyDTO) {
		Assert.notNull(recreatePolicyDTO, DTO_NOT_NULL);
		Assert.notNull(recreatePolicyDTO.getFormId(), FORM_ID_NOT_NULL);
		Assert.notNull(recreatePolicyDTO.getWorkflowItemId(), WORKFLOW_ITEM_NOT_NULL);

		String userId = workflowUserService.getUserId(context);

		return subsriptionService.recreatePolicy(recreatePolicyDTO, userId);
	}
}
