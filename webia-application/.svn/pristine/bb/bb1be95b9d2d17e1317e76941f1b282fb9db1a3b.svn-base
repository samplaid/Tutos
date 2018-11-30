package lu.wealins.webia.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CheckDataContainerDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.webia.core.service.WebiaCheckDataService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Component
public class WebiaCheckDataServiceImpl implements WebiaCheckDataService {

	private static final String CHECK_CODES = "checkCodes";
	private static final String CHECK_ID = "checkId";
	private static final String POLICY_ID = "policyId";
	private static final String LOAD_MAP = "loadMap";
	private static final String LOAD = "load";
	private static final String LOAD_BY_POLICY_ID = "loadByPolicyId";
	private static final String WEBIA_CHECK_DATA = "webia/checkData/";
	private static final String WEBIA_UPDATE_ALL_CHECK_DATA = "webia/checkData/updateAll";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Map<String, CheckDataDTO> getCheckData(Integer workflowItemId, List<String> checkCodes) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workflowItemId", workflowItemId);

		checkCodes.forEach(x -> params.add(CHECK_CODES, x));

		return restClientUtils.get(WEBIA_CHECK_DATA, LOAD_MAP, params, new GenericType<Map<String, CheckDataDTO>>() {
		});
	}

	@Override
	public CheckDataDTO getCheckData(Integer workflowItemId, String checkCode) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workflowItemId", workflowItemId);
		params.add("checkCode", checkCode);

		return restClientUtils.get(WEBIA_CHECK_DATA, LOAD, params, CheckDataDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaCheckDataService#getCheckData(java.lang.String, java.util.List)
	 */
	@Override
	public CheckDataDTO getCheckData(String policyId, Integer checkId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);
		params.add(CHECK_ID, checkId);
		return restClientUtils.get(WEBIA_CHECK_DATA, LOAD_BY_POLICY_ID, params, CheckDataDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaCheckDataService#update(lu.wealins.webia.ws.rest.dto.CheckDataContainerDTO)
	 */
	@Override
	public CheckDataContainerDTO update(CheckDataContainerDTO checkData) {
		return restClientUtils.post(WEBIA_UPDATE_ALL_CHECK_DATA, checkData, CheckDataContainerDTO.class);
	}
}
