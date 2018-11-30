package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.webia.core.service.CheckStepService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class CheckStepServiceImpl implements CheckStepService {

	private static final String YES = "YES";
	private static final String NO = "NO";

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private RestClientUtils restClientUtils;

	private static final String CHECK_STEPS_ENDPOINT = "webia/step/checkSteps";

	@Override
	public Collection<CheckStepDTO> getCheckSteps(String userId, Integer workflowItemId, String stepWorkflow) {
		String stepName = getTargetStep(userId, workflowItemId, stepWorkflow);

		WorkflowItemDataDTO workflowItem = workflowService.getWorkflowItem(workflowItemId.toString(), userId);

		// Load the screen data and checks
		Collection<CheckStepDTO> checkSteps = getCheckStepsByStepAndWorkflow(workflowItemId, stepName, workflowItem.getWorkflowItemTypeId());

		return checkSteps;
	}

	@Override
	public CheckStepDTO getCheckStep(Collection<CheckStepDTO> checkSteps, Metadata metadata) {
		return checkSteps.stream().filter(x -> metadata.getMetadata().equals(x.getMetadata())).findFirst().orElse(null);
	}

	private boolean isNo(CheckStepDTO checkStep) {
		CheckDataDTO checkData = getCheckData(checkStep);
		return checkData != null && NO.equals(checkData.getDataValueYesNoNa());

	}

	private boolean isYes(CheckStepDTO checkStep) {
		CheckDataDTO checkData = getCheckData(checkStep);
		return checkData != null && YES.equals(checkData.getDataValueYesNoNa());

	}

	private CheckDataDTO getCheckData(CheckStepDTO checkStep) {
		if (checkStep == null) {
			return null;
		}
		CheckWorkflowDTO check = checkStep.getCheck();
		if (check == null) {
			return null;
		}
		return check.getCheckData();
	}

	/**
	 * @return
	 */
	@Override
	public boolean isMedicalQuestionsChecked(Collection<CheckStepDTO> checkStepDtos) {
		return checkStepDtos != null && checkStepDtos.stream().filter(checkStepDto -> checkStepDto.getCheck() != null
				&& checkStepDto.getCheck().getCheckId() != null
				&& (Constantes.MEDICAL_QUESTIONNAIRE == checkStepDto.getCheck().getCheckId().intValue() || Constantes.MEDICAL_ACCEPTANCE_EXECUTED == checkStepDto.getCheck().getCheckId().intValue()))
				.map(checkStepDto -> checkStepDto.getCheck().getCheckData())
				.filter(checkData -> checkData != null)
				.anyMatch(checkData -> (checkData.getCheckId().intValue() == Constantes.MEDICAL_ACCEPTANCE_EXECUTED
						&& (Constantes.YES.equals(checkData.getDataValueYesNoNa()) || Constantes.NO.equals(checkData.getDataValueYesNoNa())))
						|| (checkData.getCheckId().intValue() == Constantes.MEDICAL_QUESTIONNAIRE && Constantes.YES.equals(checkData.getDataValueYesNoNa())));

	}

	private String getTargetStep(String userId, Integer workflowItemId, String stepWorkflow) {
		String action;
		// Override the current action, should we check the step (action) name ?
		if (StringUtils.hasText(stepWorkflow)) {
			action = stepWorkflow;
		} else {
			// Load the current, fulfilled and future actions of the workflow item.
			WorkflowItemAllActionsDTO actions = workflowService.getWorkflowItemAllActions(workflowItemId, userId);
			// Current workflow item action
			action = actions.getCurrent().getAction();
		}
		return action;
	}

	private Collection<CheckStepDTO> getCheckStepsByStepAndWorkflow(Integer workflowItemId, String stepName, Integer workflowItemTypeId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("stepWorkflow", stepName);
		params.add("workItemId", workflowItemId);
		params.add("workflowItemTypeId", workflowItemTypeId);

		return restClientUtils.get(CHECK_STEPS_ENDPOINT, "", params, new GenericType<Collection<CheckStepDTO>>() {
		});
	}

	@Override
	public boolean isNo(Collection<CheckStepDTO> checkStepDtos, Metadata metadata) {
		CheckStepDTO checkStep = getCheckStep(checkStepDtos, metadata);
		return isNo(checkStep);
	}

	@Override
	public boolean isYes(Collection<CheckStepDTO> checkStepDtos, Metadata metadata) {
		CheckStepDTO checkStep = getCheckStep(checkStepDtos, metadata);
		return isYes(checkStep);
	}
}
