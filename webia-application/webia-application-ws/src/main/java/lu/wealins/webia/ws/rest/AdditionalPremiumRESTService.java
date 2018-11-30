package lu.wealins.webia.ws.rest;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface AdditionalPremiumRESTService {

	AppFormDTO initAdditionalPremium(SecurityContext context, String policyId, Integer workflowItemId);

	AppFormDTO recreate(SecurityContext context, Integer workflowItemId);

}
