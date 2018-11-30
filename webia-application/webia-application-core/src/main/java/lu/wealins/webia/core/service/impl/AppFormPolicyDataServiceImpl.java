package lu.wealins.webia.core.service.impl;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.AppFormPolicyDataService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class AppFormPolicyDataServiceImpl implements AppFormPolicyDataService {

	private static final String WEBIA_APP_FORMS_POLICIES = "webia/appForm/policy";
	private static final String POLICY_ID_CANNOT_BE_NULL = " Policy id connot be null ";
	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Collection<AppFormDTO> getAppFormByPolicy(String policyId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.put("policyId", Arrays.asList(policyId));

		return restClientUtils.get(WEBIA_APP_FORMS_POLICIES, "", params, new GenericType<Collection<AppFormDTO>>() {
		});

	}

}
