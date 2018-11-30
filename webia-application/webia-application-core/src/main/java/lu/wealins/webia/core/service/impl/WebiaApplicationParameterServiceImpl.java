package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class WebiaApplicationParameterServiceImpl implements WebiaApplicationParameterService {
	private static final String BIG_DECIMAL_VALUE_ENDPOINT = "bigDecimalValue";
	private static final String INT_VALUE_ENDPOINT = "intValue";
	private static final String CODE_QUERY_PARAM = "code";
	private static final String APPLI_PARAM_CODE_NOT_NULL = "The appli param code can't be null";
	private static final String WEBIA_LOAD_APPLI_PARAM = "webia/applicationParameter/";
	private static final String WEBIA_LOAD_LIST_APPLI_PARAM = "webia/applicationParameter/list/";
	
	private static final String LOGINS_BY_PASS_STEP_ACCESS = "LOGINS_BY_PASS_STEP_ACCESS";


	@Autowired
	private RestClientUtils restClientUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getApplicationParameter(java.lang.String)
	 */
	@Override
	public ApplicationParameterDTO getApplicationParameter(String code) {
		return restClientUtils.get(WEBIA_LOAD_APPLI_PARAM, code, ApplicationParameterDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getApplicationParameters(java.lang.String)
	 */
	@Override
	public Collection<String> getApplicationParameters(String code) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		return restClientUtils.get(WEBIA_LOAD_LIST_APPLI_PARAM, code, params, new GenericType<Collection<String>>() {
			// nothing to do.
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getLoginsByPassStepAccess()
	 */
	@Override
	public Set<String> getLoginsByPassStepAccess() {
		return getApplicationParameters(LOGINS_BY_PASS_STEP_ACCESS).stream().collect(Collectors.toSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaApplicationParameterService#getIntegerValues(java.lang.String)
	 */
	@Override
	public List<Integer> getIntegerValues(String code) {
		List<Integer> intValueList = new ArrayList<>();

		if (!StringUtils.isEmpty(code)) {
			MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
			params.putSingle(CODE_QUERY_PARAM, code);
			intValueList = restClientUtils.get(WEBIA_LOAD_APPLI_PARAM, "intValueList", params, new GenericType<List<Integer>>() {
			});
		}

		return intValueList;
	}

	@Override
	public BigDecimal getBigDecimalValue(String code) {
		Assert.notNull(code, APPLI_PARAM_CODE_NOT_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.putSingle(CODE_QUERY_PARAM, code);

		return restClientUtils.get(WEBIA_LOAD_APPLI_PARAM, BIG_DECIMAL_VALUE_ENDPOINT, params, BigDecimal.class);
	}

	@Override
	public Integer getIntegerValue(String code) {
		Assert.notNull(code, APPLI_PARAM_CODE_NOT_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.putSingle(CODE_QUERY_PARAM, code);

		return restClientUtils.get(WEBIA_LOAD_APPLI_PARAM, INT_VALUE_ENDPOINT, params, Integer.class);
	}

}
