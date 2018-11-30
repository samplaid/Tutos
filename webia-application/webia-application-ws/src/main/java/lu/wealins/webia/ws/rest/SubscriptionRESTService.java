package lu.wealins.webia.ws.rest;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.PolicyRecreateResponse;
import lu.wealins.common.dto.liability.services.RecreatePolicyDTO;




public interface SubscriptionRESTService {
	PolicyRecreateResponse recreatePolicy(SecurityContext context, RecreatePolicyDTO recreatePolicyDTO);
}
