package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.WorkflowGroupDTO;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityClientService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.StepEnricher;
import lu.wealins.webia.core.service.WebiaAppFormService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaWorkflowGroupService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.ws.rest.impl.exception.ReportExceptionHelper;
import lu.wealins.webia.ws.rest.impl.exception.StepException;

@Service
public class LiabilityAdditionalPremiumServiceImpl extends LiabilityMoneyInServiceImpl {

	private static final String SUPPORT_GROUP_APPLI_PARAM = "SALES_SUPPORT_ID_GROUP";

	@Autowired
	private WebiaApplicationParameterService appliParamService;
	@Autowired
	private WebiaWorkflowUserService webiaWorkflowUserService;

	@Autowired
	private WebiaWorkflowGroupService groupService;

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private LiabilityClientService clientService;

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private AbstractFundFormService<FundFormDTO> fundFormService;

	@Autowired
	private WebiaAppFormService appFormService;

	@Autowired
	private LiabilityFundTransactionService fundTransactionService;

	@Autowired
	private EditingService editingService;

	@Autowired
	@Qualifier("webiaStepEnricher")
	private StepEnricher webiaStepEnricher;

	@Autowired
	private LiabilityPolicyCoverageService liabilityPolicyCoverage;

	@Override
	protected void beforeCompleteFormData(AppFormDTO appForm, String stepWorkflow, String usrId) {
		switch (StepTypeDTO.getStepType(stepWorkflow)) {
		case VALIDATE_ADDITIONAL_PREMIUM:

			if (appForm.getCoverage() == null) {
				policyService.createPolicyCashSuspense(appForm);

				PolicyCoverageDTO coverageDto = liabilityPolicyCoverage.createAdditionalPremium(appForm);
				appForm.setCoverage(coverageDto.getCoverage());

				appFormService.updateCoverage(appForm.getFormId(), appForm.getCoverage());
			}

			Collection<String> errors = policyService.getPolicyIncompleteDetails(appForm.getPolicyId());
			ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, null, StepException.class);

			fundService.valoriseNewFid(appForm);
			policyService.updatePolicyAdditionalPremium(appForm.getPolicyId(), appForm.getPaymentDt());
			fundTransactionService.executeFundTransactionValuation(appForm, usrId);
			break;
		case GENERATE_DOCUMENTATION:
			editingService.createWorkflowDocumentRequest(Long.valueOf(appForm.getWorkflowItemId()), DocumentType.ADD_PREMIUM, false, false);
			break;
		default:
			break;
		}

	}
	
	@Override
	public AppFormDTO updateFormData(AppFormDTO formData, String stepWorkflow, String userId) {
		Assert.notNull(formData, APP_FORM_CANNOT_BE_NULL);

		formData = super.updateFormData(formData, stepWorkflow, userId);

		switch (StepTypeDTO.getStepType(stepWorkflow)) {
		case REGISTRATION:
			fundService.addFundOnFundForm(getFidFasFunds(formData), formData.getContractCurrency());

			break;
		case NEW:
			formData.setClientName(clientService.getClientNames(formData.getPolicyId()));

			if (StringUtils.isEmpty(formData.getFirstCpsUser()) && !isSalesSupport(userId)) {
				WorkflowUserDTO user = webiaWorkflowUserService.getAssignedUser(formData.getWorkflowItemId().toString(), userId);
				formData.setFirstCpsUser(user.getUsrId());
			}

			break;
		default:
			break;
		}

		return formData;
	}

	@Override
	protected StepTypeDTO getFirstStep() {
		return StepTypeDTO.NEW;
	}

	private Collection<FundFormDTO> getFidFasFunds(AppFormDTO formData) {
		return formData.getFundForms()
				.stream()
				.filter(fundForm -> fundService.isFIDorFAS(fundForm.getFund()))
				.collect(Collectors.toList());
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.ADDITIONAL_PREMIUM;
	}

	@Override
	protected WorkflowItemDataDTO getCompleteMetadata(AppFormDTO appForm, String usrId, StepTypeDTO stepType) {
		switch (stepType) {
		case NEW:
			return createNewWorkflowMetadata(usrId, appForm.getWorkflowItemId());
		case WAITING_DISPATCH:
			return workflowItemService.createDispatchWorkflowItemData(appForm, usrId);
		case AWAITING_VALUATION:
		case CHECK_DOCUMENTATION:
		case VALIDATE_ADDITIONAL_PREMIUM:
			return createExternalFundsWorkflowMetadata(usrId, appForm);
		default:
			// TODO : check when we should put this metadata
			return workflowItemService.createAnalysisWorkflowItemData(appForm, usrId);
		}
	}

	private WorkflowItemDataDTO createNewWorkflowMetadata(String usrId, Integer workflowItemId) {
		MetadataDTO saleSupportMetadata = metadataService.createMetadata(Metadata.IS_SALES_SUPPORT.getMetadata(), isSalesSupport(usrId) + "");

		return metadataService.createWorkflowItemData(workflowItemId.longValue(), usrId, saleSupportMetadata);
	}

	private WorkflowItemDataDTO createExternalFundsWorkflowMetadata(String usrId, AppFormDTO appForm) {
		String hasFEorFIC = Boolean.valueOf(fundFormService.hasFEorFIC(appForm.getFundForms())).toString();

		MetadataDTO externalFundMetadata = metadataService.createMetadata(Metadata.FE_FIC.getMetadata(), hasFEorFIC);

		return metadataService.createWorkflowItemData(appForm.getWorkflowItemId().longValue(), usrId, externalFundMetadata);
	}

	private boolean isSalesSupport(String usrId) {
		Integer supportGroupId = appliParamService.getIntegerValue(SUPPORT_GROUP_APPLI_PARAM);
		Collection<WorkflowGroupDTO> currentGroups = groupService.getWorkflowGroupsByUser(usrId);

		return isIdInGroups(supportGroupId, currentGroups);
	}

	private boolean isIdInGroups(Integer supportGroupId, Collection<WorkflowGroupDTO> currentGroups) {
		return currentGroups
				.stream()
				.map(WorkflowGroupDTO::getUrgId)
				.anyMatch(supportGroupId::equals);
	}

	@Override
	public AppFormDTO enrichFormData(AppFormDTO appForm, String stepWorkflow, String userId) {
		Assert.notNull(appForm, APP_FORM_CANNOT_BE_NULL);

		return webiaStepEnricher.enrichAppForm(appForm);
	}

	@Override
	public void abortFormData(AppFormDTO appForm) {
		super.abortFormData(appForm);

		if (appForm.getCoverage() != null) {
			policyService.abortCoverage(appForm.getPolicyId(), appForm.getCoverage());
		}
	}

	@Override
	protected boolean isCps1Step(StepTypeDTO stepType) {
		return StepTypeDTO.ADDITIONAL_PREMIUM_CPS1_GROUP.contains(stepType);
	}
}
