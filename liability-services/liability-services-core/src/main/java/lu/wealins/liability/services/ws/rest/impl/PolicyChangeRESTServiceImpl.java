package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.PolicyChangeService;
import lu.wealins.liability.services.ws.rest.PolicyChangeRESTService;
import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeRequest;

@Component
public class PolicyChangeRESTServiceImpl implements PolicyChangeRESTService {

	@Autowired
	private PolicyChangeService policyChangeService;

	@Override
	public PolicyChangeDTO getPolicyChange(SecurityContext context, Integer workflowItemId) {
		return policyChangeService.getPolicyChange(workflowItemId);
	}
	
	@Override
	public Collection<PolicyChangeDTO> getPolicyChanges(SecurityContext context, String policyId) {
		return policyChangeService.getPolicyChanges(policyId);
	}	

	@Override
	public void cancelByworkflowItemId(SecurityContext context, Integer workflowItemId) {
		policyChangeService.cancelByworkflowItemId(workflowItemId);
	}

	@Override
	public Boolean saveChanges(SecurityContext context, PolicyChangeRequest policyChangeRequest) {
		return policyChangeService.saveChanges(policyChangeRequest);
	}

}
