package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.RelaunchStepRequest;
import lu.wealins.common.dto.webia.services.RelaunchStepResponse;
import lu.wealins.webia.core.service.RelaunchStepUtilityService;
import lu.wealins.webia.ws.rest.RelaunchStepUtilityRESTService;

@Component
public class RelaunchStepUtilityRESTServiceImpl implements RelaunchStepUtilityRESTService {

	@Autowired
	private RelaunchStepUtilityService relaunchStepUtilityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.AwaitingValuationRESTService#AwaitingValuation(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.AwaitingValuationRequest)
	 */
	@Override
	public RelaunchStepResponse relaunchStep(SecurityContext context, RelaunchStepRequest relaunchStepRequest) {
		return relaunchStepUtilityService.relaunchStep(context, relaunchStepRequest);
	}

}
