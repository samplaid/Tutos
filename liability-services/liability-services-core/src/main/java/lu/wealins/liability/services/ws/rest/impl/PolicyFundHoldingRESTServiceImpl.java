package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.PolicyFundHoldingDTO;
import lu.wealins.liability.services.core.business.PolicyFundHoldingService;
import lu.wealins.liability.services.ws.rest.PolicyFundHoldingRESTService;

@Component
public class PolicyFundHoldingRESTServiceImpl implements PolicyFundHoldingRESTService {

	@Autowired
	PolicyFundHoldingService policyFundHoldingService;

	@Override
	public Collection<PolicyFundHoldingDTO> getHoldingsByPolicy(SecurityContext context, String policy) {
		return policyFundHoldingService.getHoldingsByPolicy(policy);
	}

}
