package lu.wealins.webia.core.service.validations.additionalpremium;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.GenericType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.common.validations.ValidationStepService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaValidationStepServiceImpl implements ValidationStepService {

	private static final String WEBIA_STEP_VALIDATE_BEFORE_SAVE = "webia/step/validateBeforeSave";
	private static final String WEBIA_STEP_VALIDATE_BEFORE_COMPLETE = "webia/step/validateBeforeCompleteStep";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		return restClientUtils.post(WEBIA_STEP_VALIDATE_BEFORE_COMPLETE, step, new GenericType<List<String>>() {
		});
	}

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		return restClientUtils.post(WEBIA_STEP_VALIDATE_BEFORE_SAVE, step, new GenericType<List<String>>() {
		});
	}

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return WorkflowType.ALL_WORKFLOW_TYPES;
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return true;
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return true;
	}

}
