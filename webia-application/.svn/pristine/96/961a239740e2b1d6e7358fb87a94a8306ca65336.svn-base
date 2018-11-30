package lu.wealins.webia.core.service.validations.appform.impl;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.ACCOUNT_OPENING_REQUEST;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.ANALYSIS;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.AWAITING_ACCOUNT_OPENING;
import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.PREMIUM_TRANSFER_REQUEST;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.validations.ClientFormValidationService;
import lu.wealins.webia.core.service.validations.FundFormValidationService;
import lu.wealins.webia.core.service.validations.SendingRulesValidationService;
import lu.wealins.webia.core.service.validations.appform.AppFormValidationStepService;

@Service(value = "AppFormAnalysisValidationService")
public class AnalysisValidationServiceImpl extends AppFormValidationStepService {

	private static Set<WorkflowType> SUPPORTED_WORFLOW_TYPES = Stream.of(WorkflowType.APP_FORM, WorkflowType.ADDITIONAL_PREMIUM).collect(Collectors.toSet());
	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(ANALYSIS, ACCOUNT_OPENING_REQUEST, AWAITING_ACCOUNT_OPENING, PREMIUM_TRANSFER_REQUEST).collect(Collectors.toSet());

	@Autowired
	private SendingRulesValidationService sendingRulesValidationService;
	
	@Autowired
	private ClientFormValidationService clientFormValidationService;

	@Autowired
	private FundFormValidationService fundFormValidationService;
	
	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		return validateBeforeComplete(getFormData(step));
	}

	private List<String> validateBeforeComplete(AppFormDTO appForm) {
		List<String> errors = new ArrayList<>();

		sendingRulesValidationService.validate(appForm.getSendingRules(), getFirstPolicyHolderClientId(appForm), errors);
		clientFormValidationService.validatePolicyHolders(appForm.getPolicyHolders(), errors);
		clientFormValidationService.validateInsureds(appForm.getInsureds(), errors);
		fundFormValidationService.validateExternalFunds(appForm.getFundForms(), appForm.getBroker(), errors);

		return errors;
	}

	private Integer getFirstPolicyHolderClientId(AppFormDTO appForm) {
		if (CollectionUtils.isEmpty(appForm.getPolicyHolders())) {
			return null;
		}

		return appForm.getPolicyHolders().iterator().next().getClientId();
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return false;
	}

	@Override
	public Set<WorkflowType> getSupportedWorkflowTypes() {
		return SUPPORTED_WORFLOW_TYPES;
	}
}
