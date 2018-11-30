package lu.wealins.webia.core.service.impl;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyTransferDTO;
import lu.wealins.webia.core.service.LiabilityPolicyTransferService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityPolicyTransferServiceImpl implements LiabilityPolicyTransferService {

	private static final String LIABILITY_TRANSFERS = "liability/policytransfer";
	private static final String EMPTY_STRING = "";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Collection<PolicyTransferDTO> getPolicyTransfers(String policy) {

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.put("policy", Arrays.asList(policy));

		return restClientUtils.get(LIABILITY_TRANSFERS, EMPTY_STRING, queryParams,
				new GenericType<Collection<PolicyTransferDTO>>() {
				});
	}

}
