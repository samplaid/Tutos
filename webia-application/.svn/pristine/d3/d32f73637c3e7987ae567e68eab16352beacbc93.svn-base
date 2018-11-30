package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyFundHoldingDTO;
import lu.wealins.webia.core.service.LiabilityPolicyHoldingService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityPolicyHoldingServiceImpl implements LiabilityPolicyHoldingService {

	private static final String LIABILITY_POLICY_HOLDINGS = "liability/policyfundholding/";
	private static final String ALL = "all";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Collection<PolicyFundHoldingDTO> getHoldingsByPolicy(String policy) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("policy", policy);

		return restClientUtils.get(LIABILITY_POLICY_HOLDINGS, ALL, params, new GenericType<Collection<PolicyFundHoldingDTO>>() {
		});
	}

}
