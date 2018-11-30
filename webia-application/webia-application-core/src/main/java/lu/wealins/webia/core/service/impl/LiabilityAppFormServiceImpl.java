package lu.wealins.webia.core.service.impl;

import java.text.SimpleDateFormat;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.factory.StepEnricherFactory;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.LiabilityFundTransactionService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityUpdateInputService;
import lu.wealins.webia.core.service.StepEnricher;
import lu.wealins.webia.core.service.WebiaCheckDataService;

@Service
public class LiabilityAppFormServiceImpl extends LiabilityMoneyInServiceImpl {

	private static final String LIABILITY_GENERATE_POLICY_NUM = "liability/policy/getNewNumber";
	private static final String LIABILITY_FUND = "liability/fund";
	private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	private static final String LIABILITY_ABORT_POLICY = "liability/policy/abort";
	private static final String POLICY_ID_PARAM = "policyId";

	@Autowired
	private LiabilityUpdateInputService updateInputService;

	@Autowired
	private LiabilityFundService fundService;

	@Autowired
	private LiabilityPolicyService liabilityPolicyService;

	@Autowired
	private EditingService editingService;

	@Autowired
	private LiabilityFundTransactionService fundTransactionService;

	@Autowired
	@Qualifier("stepEnricherFactory")
	private StepEnricherFactory stepEnricherFactory;

	private static final String SCORE = "SCORE";

	@Autowired
	private WebiaCheckDataService webiaCheckDataService;

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.APP_FORM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityAppFormService#updateFormData(lu.wealins.webia.ws.rest.dto.AppFormDTO, java.lang.String, java.lang.String)
	 */
	@Override
	public AppFormDTO updateFormData(AppFormDTO appForm, String stepWorkflow, String usrId) {
		Assert.notNull(appForm, APP_FORM_CANNOT_BE_NULL);
		appForm = super.updateFormData(appForm, stepWorkflow, usrId);

		switch (StepTypeDTO.getStepType(stepWorkflow)) {
		case REGISTRATION:
			fundService.addFundOnFundForm(appForm.getFundForms(), appForm.getContractCurrency());

			if (StringUtils.hasText(appForm.getProductCd()) && StringUtils.isEmpty(appForm.getPolicyId())) {

				MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
				params.add("product", appForm.getProductCd());
				String policyNumber = restClientUtils.get(LIABILITY_GENERATE_POLICY_NUM, "", params, String.class);

				appForm.setPolicyId(policyNumber);
			}

			break;
		case UPDATE_INPUT:
			updateInputService.updateInput(appForm, usrId);
			break;
		case PREMIUM_INPUT_AND_NAV:
			updateFundValuationAmount(appForm);
			break;
		default:
			break;
		}

		return appForm;
	}

	@Override
	protected void beforeCompleteFormData(AppFormDTO appForm, String stepWorkflow, String usrId) {
		switch (StepTypeDTO.getStepType(stepWorkflow)) {
		case VALIDATE_INPUT:
			liabilityPolicyService.createPolicy(appForm);
			break;
		case PREMIUM_INPUT_AND_NAV:
			liabilityPolicyService.createPolicy(appForm);
			break;
		case VALIDATE_PREMIUM_AND_NAV:
			liabilityPolicyService.createPolicyCashSuspense(appForm);
			fundService.valoriseNewFid(appForm);
			liabilityPolicyService.activatePolicy(appForm.getPolicyId(), appForm.getPaymentDt());
			fundTransactionService.executeFundTransactionValuation(appForm, usrId);
			break;
		case GENERATE_DOCUMENTATION:
			editingService.generateDocumentation(appForm);
			break;
		default:
			break;
		}
	}

	@Override
	public void abortFormData(AppFormDTO appForm) {

		super.abortFormData(appForm);

		String policyId = appForm.getPolicyId();

		if (!StringUtils.isEmpty(policyId)) {
			restClientUtils.get(LIABILITY_ABORT_POLICY, "", getAbortParams(policyId), Boolean.class);
		}
	}

	@Override
	protected WorkflowItemDataDTO getCompleteMetadata(AppFormDTO appForm, String usrId, StepTypeDTO stepType) {
		switch (stepType) {
		// FYI : REGISTRATION has been moved to the save action
		case WAITING_DISPATCH:
			return workflowItemService.createDispatchWorkflowItemData(appForm, usrId);
		case UNBLOCKED_ANALYSIS:
		case UNBLOCKED_VALIDATE_ANALYSIS:
		case ANALYSIS:
		case VALIDATE_ANALYSIS:
		case ACCOUNT_OPENING_REQUEST:
		case AWAITING_ACCOUNT_OPENING:
		case GENERATE_MANDAT_DE_GESTION:
			return workflowItemService.createAnalysisWorkflowItemData(appForm, usrId);
		default:
			return new WorkflowItemDataDTO();
		}
	}

	private MultivaluedMap<String, Object> getAbortParams(String policyId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID_PARAM, policyId);
		return params;
	}

	private void updateFundValuationAmount(AppFormDTO appForm) {
		if (appForm != null) {
			appForm.getFundForms().forEach(fundForm -> {

				if (fundForm.getFund() != null && fundService.isFIDorFAS(fundForm.getFund()) &&
						BooleanUtils.isTrue(fundForm.getFund().getIsValorized()) &&
						appForm.getPaymentDt() != null) {

					MultivaluedMap<String, Object> queryMappings = new MultivaluedHashMap<>();
					queryMappings.add("fdsId", fundForm.getFundId());
					queryMappings.add("valuationDate", new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD).format(appForm.getPaymentDt()));
					queryMappings.add("priceType", 1);
					Boolean addOn = restClientUtils.get(LIABILITY_FUND, "/canAddFIDorFASFundValuationAmount", queryMappings, Boolean.class);

					if (BooleanUtils.isNotTrue(addOn)) {
						fundForm.setValuationAmt(null);
					}
					fundForm.setAddOnValuableAmount(addOn);
				}
			});
		}
	}

	@Override
	public AppFormDTO enrichFormData(AppFormDTO appForm, String stepWorkflow, String userId) {
		Assert.notNull(appForm, APP_FORM_CANNOT_BE_NULL);

		StepEnricher stepEnricher = stepEnricherFactory.getInstance(stepWorkflow);
		return stepEnricher.enrichAppForm(appForm);
	}

	@Override
	protected boolean isCps1Step(StepTypeDTO stepType) {
		return StepTypeDTO.CPS1_GROUP.contains(stepType);
	}

	@Override
	protected WorkflowItemDataDTO createCommonMetadata(AppFormDTO formData, String userId, String policyId) {
		return workflowItemService.createCommonMetadata(formData, userId);
	}

	@Override
	protected StepTypeDTO getFirstStep() {
		return StepTypeDTO.REGISTRATION;
	}

	@Override
	public AppFormDTO completeFormData(AppFormDTO appForm, String stepWorkflow, String usrId) {
		if (StepTypeDTO.VALIDATE_INPUT == StepTypeDTO.getStepType(stepWorkflow)) {
			CheckDataDTO checkData = webiaCheckDataService.getCheckData(appForm.getWorkflowItemId(), SCORE);
			if (checkData != null && checkData.getDataValueNumber() != null) {
				appForm.setScore(checkData.getDataValueNumber().intValue());
			}
		}

		return super.completeFormData(appForm, stepWorkflow, usrId);
	}

}
