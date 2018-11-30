package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.PolicyTransferService;
import lu.wealins.liability.services.ws.rest.PolicyTransferRESTService;
import lu.wealins.common.dto.liability.services.PolicyTransferDTO;

@Component
public class PolicyTransferRESTServiceImpl implements PolicyTransferRESTService {

	@Autowired
	PolicyTransferService policyTransferService;

	@Override
	public Collection<PolicyTransferDTO> getPolicyTransfers(SecurityContext context, String policy) {
		return policyTransferService.getPolicyTransfers(policy);
	}

}
