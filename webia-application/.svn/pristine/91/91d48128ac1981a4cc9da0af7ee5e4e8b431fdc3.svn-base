package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;
import lu.wealins.webia.core.service.LiabilityBrokerChangeFormService;
import lu.wealins.webia.ws.rest.LiabilityBrokerChangeFormRESTService;

@Component
public class LiabilityBrokerChangeFormRESTServiceImpl implements LiabilityBrokerChangeFormRESTService {

	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";

	@Autowired
	private LiabilityBrokerChangeFormService beneficiaryChangeFormService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.LiabilityBrokerChangeFormRESTService#initBrokerChangeForm(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public BrokerChangeFormDTO initBrokerChangeForm(SecurityContext context, String policyId, Integer workflowItemId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return beneficiaryChangeFormService.initBrokerChangeForm(policyId, workflowItemId);
	}

}