package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.webia.core.service.LiabilityPolicyAgentShareService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityPolicyAgentShareServiceImpl implements LiabilityPolicyAgentShareService {

	private static final String LIABILITY_LOAD_POLICY_AGENT_SHARE = "liability/policyagentshare/";
	private static final String LIABILITY_BROKER_ENTRY_FEES = "brokerEntryFees";
	private static final String LIABILITY_LAST_OPERATION_BROKER_ENTRY_FEES = "lastBrokerEntryFees";
	private static final String LIABILITY_LAST_OPERATION_BROKER_ADMIN_FEES = "lastBrokerAdminFees";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public PolicyAgentShareDTO getBrokerEntryFees(String polId) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("polId", polId);

		return restClientUtils.get(LIABILITY_LOAD_POLICY_AGENT_SHARE, LIABILITY_BROKER_ENTRY_FEES, params, PolicyAgentShareDTO.class);

	}

	@Override
	public PolicyAgentShareDTO getLastOperationBrokerEntryFees(String polId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("polId", polId);

		return restClientUtils.get(LIABILITY_LOAD_POLICY_AGENT_SHARE, LIABILITY_LAST_OPERATION_BROKER_ENTRY_FEES, params, PolicyAgentShareDTO.class);
	}

	@Override
	public PolicyAgentShareDTO getLastOperationBrokerAdminFees(String polId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("polId", polId);

		return restClientUtils.get(LIABILITY_LOAD_POLICY_AGENT_SHARE, LIABILITY_LAST_OPERATION_BROKER_ADMIN_FEES, params, PolicyAgentShareDTO.class);
	}

}
