package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.webia.core.service.WebiaWorkflowUtilityService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaWorkflowUtilityServiceImpl implements WebiaWorkflowUtilityService {

	private static final String EXCLUDED_STATUS = "excludedStatus";
	private static final String ACTION_REQUIRED = "actionRequired";
	private static final String WORKFLOW_ITEM_TYPE = "workflowItemType";
	private static final String WORKFLOW_ITEMS_IDS = "workflowItemsIds";
	private static final String WEBIA_WORKFLOW_LOAD = "webia/workflow/";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public Collection<Long> getWorkflowItemIds(Integer workflowItemType, Integer actionRequired, List<Integer> excludedStatus) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add(WORKFLOW_ITEM_TYPE, workflowItemType);
		params.add(ACTION_REQUIRED, actionRequired);
		excludedStatus.forEach(x -> params.add(EXCLUDED_STATUS, x));

		return restClientUtils.get(WEBIA_WORKFLOW_LOAD, WORKFLOW_ITEMS_IDS, params,
				new GenericType<Collection<Long>>() {
			// nothing to do.
		});

	}

}
