package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.WebiaStepService;
import lu.wealins.webia.core.service.WebiaStepServiceByOperation;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaStepServiceImpl implements WebiaStepService {

	private static final String WEBIA_STEP_LOAD = "webia/step";
	private static final String WEBIA_STEP_UPDATE = "webia/step/update";
	private static final String WEBIA_STEP_COMPLETE = "webia/step/complete";
	private static final String WEBIA_STEP_ABORT = "webia/step/abort";

	@Autowired
	private RestClientUtils restClientUtils;

	private Map<WorkflowType, WebiaStepServiceByOperation> servicesByOperation;

	@Autowired
	public void setServicesMap(List<WebiaStepServiceByOperation> services) {
		servicesByOperation = services.stream().collect(Collectors.toMap(WebiaStepServiceByOperation::getSupportedWorkflowType, Function.identity()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaStepService#getStep(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public StepDTO getStep(Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("stepWorkflow", stepWorkflow);
		params.add("workItemId", workflowItemId);
		params.add("workflowItemTypeId", workflowItemTypeId);

		StepDTO step = restClientUtils.get(WEBIA_STEP_LOAD, "/", params, StepDTO.class);

		return step;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaStepService#update(lu.wealins.webia.ws.rest.dto.StepDTO)
	 */
	@Override
	public StepDTO update(StepDTO step) {
		StepDTO stepDTO = getService(step).preUpdate(step);

		stepDTO = restClientUtils.post(WEBIA_STEP_UPDATE, stepDTO, StepDTO.class);
		
		return getService(step).update(stepDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaStepService#complete(lu.wealins.webia.ws.rest.dto.StepDTO)
	 */
	@Override
	public StepDTO complete(StepDTO step) {
		StepDTO stepDTO = restClientUtils.post(WEBIA_STEP_COMPLETE, step, StepDTO.class);

		return getService(step).complete(stepDTO);

	}
	
	@Override
	public StepDTO postComplete(StepDTO step) {
		return getService(step).postComplete(step);

	}

	@Override
	public StepDTO abort(StepDTO step) {
		if (step.getFormData().getFormId() == null) {
			return step;
		}
		return restClientUtils.post(WEBIA_STEP_ABORT, step, StepDTO.class);
	}

	@SuppressWarnings("rawtypes")
	private WebiaStepServiceByOperation getService(StepDTO step) {
		WorkflowType enumType = WorkflowType.getType(step.getWorkflowItemTypeId());
		WebiaStepServiceByOperation service = servicesByOperation.get(enumType);
		if (service == null) {
			throw new UnsupportedOperationException("Workflow type " + enumType.getValue() + " not supported.");
		}
		return service;
	}

	@Override
	public Collection<StepLightDTO> getStepsByWorkflowItemTypeId(Integer workflowItemTypeId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workflowItemTypeId", workflowItemTypeId);
		return restClientUtils.get(WEBIA_STEP_LOAD, "/stepsByWorkflowItemTypeId", params, new GenericType<Collection<StepLightDTO>>(){});
	}
}
