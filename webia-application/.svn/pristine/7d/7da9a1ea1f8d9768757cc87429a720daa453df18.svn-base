package lu.wealins.webia.core.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.ApplyBeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.UpdateBeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.mapper.BeneficiaryChangeFormMapper;
import lu.wealins.webia.core.service.ClientRoleActivationFlagService;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityBeneficiaryChangeFormService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WorkflowItemDataService;
import lu.wealins.webia.ws.rest.request.EditingRequest;


@Service
public class LiabilityBeneficiaryChangeFormServiceImpl extends LiabilityAmendmentWorkflowServiceImpl<BeneficiaryChangeFormDTO> implements LiabilityBeneficiaryChangeFormService {

	private static final String UPDATE = "update";
	private static final String APPLY_CHANGE = "applyChange";
	private static final String CHANGE_BENEF_AVAILABLE_SUB_ROLES = "CHANGE_BENEF_AVAILABLE_SUB_ROLES";
	private static final String RULES_2_POLICYHOLDERS_NORD = "RULES_2_POLICYHOLDERS_NORD";
	private static final String LIABILITY_BENEFICIARY_CHANGE = "liability/beneficiaryChange/";

	private static Logger logger = LoggerFactory.getLogger(LiabilityBeneficiaryChangeFormServiceImpl.class);

	@Autowired
	private ClientRoleActivationFlagService clientRoleActivationFlagService;

	@Autowired
	private BeneficiaryChangeFormMapper beneficiaryChangeFormMapper;
	
	@Autowired
	private MetadataService metadataService;
	
	@Autowired
	private LiabilityWorkflowService workflowService;
	
	@Autowired
	private EditingService editingService;

	@Autowired
	private WebiaApplicationParameterService applicationParameterService;

	@Autowired
	protected WorkflowItemDataService appFormWorkflowItemService;
	
	@Autowired
	private LiabilityPolicyService liabilityPolicyService;

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.BENEFICIARY_CHANGE;
	}

	@SuppressWarnings("boxing")
	@Override
	public BeneficiaryChangeFormDTO updateFormData(BeneficiaryChangeFormDTO formData, String stepWorkflow, String userId) {
		BeneficiaryChangeFormDTO formDataDTO = super.updateFormData(formData, stepWorkflow, userId);
		BeneficiaryChangeDTO beneficiaryChange = beneficiaryChangeFormMapper.asBeneficiaryChangeDTO(formDataDTO);

		UpdateBeneficiaryChangeRequest request = createUpdateBeneficiaryChangeRequest(formData, beneficiaryChange);

		beneficiaryChange = restClientUtils.post(LIABILITY_BENEFICIARY_CHANGE + UPDATE, request, BeneficiaryChangeDTO.class);
		BeneficiaryChangeFormDTO updatedFormData = beneficiaryChangeFormMapper.asBeneficiaryChangeFormDTO(beneficiaryChange, formData);

		updateMetadata(updatedFormData, userId);

		return updatedFormData;
	}

	private UpdateBeneficiaryChangeRequest createUpdateBeneficiaryChangeRequest(BeneficiaryChangeFormDTO formData, BeneficiaryChangeDTO beneficiaryChange) {
		UpdateBeneficiaryChangeRequest request = new UpdateBeneficiaryChangeRequest();

		request.setBeneficiaryChange(beneficiaryChange);
		request.setClientRoleActivationFlagDTO(setupClientRoleActivationFlag(formData.getPolicyId()));

		return request;
	}

	private void updateMetadata(BeneficiaryChangeFormDTO formData, String userId) {
		
		WorkflowItemDataDTO workflowItemData = appFormWorkflowItemService.createCommonMetadata(formData.getPolicyId(), new Long(formData.getWorkflowItemId().longValue()), userId);
		if (workflowItemData != null) {
			workflowService.saveMetada(workflowItemData);
		}
	}

	@Override
	public BeneficiaryChangeFormDTO enrichFormData(BeneficiaryChangeFormDTO formData, String stepWorkflow, String userId) {
		super.enrichFormData(formData, stepWorkflow, userId);
		Integer workflowItemId = formData.getWorkflowItemId();

		String policyId = getPolicy(workflowItemId, userId);
		formData.setPolicyId(policyId);

		if (policyId == null) {
			return formData;
		}

		// probably generic... ???
		PolicyChangeDTO policyChange = policyChangeService.getPolicyChange(workflowItemId);
		if (policyChange != null) {
			formData.setChangeDate(policyChange.getDateOfChange());
		}

		BeneficiaryChangeRequest request = createBeneficiaryChangeRequest(formData.getPolicyId(), workflowItemId);

		BeneficiaryChangeDTO beneficiaryChange = restClientUtils.post(LIABILITY_BENEFICIARY_CHANGE + "clientRoleView", request, BeneficiaryChangeDTO.class);
		
		return beneficiaryChangeFormMapper.asBeneficiaryChangeFormDTO(beneficiaryChange, formData);
	}

	private String getPolicy(Integer workflowItemId, String usrId) {
		MetadataDTO metadataDTO = metadataService.getMetadata(workflowItemId + "", Metadata.POLICY_ID.getMetadata(), usrId);
		if (metadataDTO != null && !StringUtils.isEmpty(metadataDTO.getValue())) {
			return metadataDTO.getValue();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityBeneficiaryChangeFormService#initBeneficiaryChangeForm(java.lang.String)
	 */
	@Override
	public BeneficiaryChangeFormDTO initBeneficiaryChangeForm(String policyId, Integer workflowItemId) {
		BeneficiaryChangeRequest request = createBeneficiaryChangeRequest(policyId, workflowItemId);
		BeneficiaryChangeDTO beneficiaryChange = restClientUtils.post(LIABILITY_BENEFICIARY_CHANGE + "initBeneficiaryChange/clientRoleView", request, BeneficiaryChangeDTO.class);

		return beneficiaryChangeFormMapper.asBeneficiaryChangeFormDTO(beneficiaryChange);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityBeneficiaryChangeFormService#getBeneficiaryChangeForm(java.lang.String, java.lang.Integer )
	 */
	@Override
	public BeneficiaryChangeFormDTO getBeneficiaryChangeForm(String policyId, Integer workflowItemId) {
		BeneficiaryChangeRequest request = createBeneficiaryChangeRequest(policyId, workflowItemId);
		BeneficiaryChangeDTO beneficiaryChange = restClientUtils.post(LIABILITY_BENEFICIARY_CHANGE + "clientRoleView", request, BeneficiaryChangeDTO.class);

		return beneficiaryChangeFormMapper.asBeneficiaryChangeFormDTO(beneficiaryChange);
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityBeneficiaryChangeFormService#getBeneficiaryChange(java.lang.String, java.lang.Integer )
	 */
	@Override
	public BeneficiaryChangeDTO getBeneficiaryChange(String policyId, Integer workflowItemId, String productCd, String lang) {
		BeneficiaryChangeRequest request = createBeneficiaryChangeRequest(policyId, workflowItemId);
		BeneficiaryChangeDTO beneficiaryChange = restClientUtils.post(LIABILITY_BENEFICIARY_CHANGE + "clientRoleView", request, BeneficiaryChangeDTO.class);

		// translate the standard clauses
		PolicyClausesDTO clauses = new PolicyClausesDTO();
		clauses.setDeath(beneficiaryChange.getDeathBenefClauses().stream().collect(Collectors.toList()));
		clauses.setMaturity(beneficiaryChange.getLifeBenefClauses().stream().collect(Collectors.toList()));
		liabilityPolicyService.getTranslatedClauses(clauses, productCd, lang);
	
		return beneficiaryChange;
	}	
	
	private BeneficiaryChangeRequest createBeneficiaryChangeRequest(String policyId, Integer workflowItemId) {
		ClientRoleActivationFlagDTO beneficiaryRoleActivation = setupClientRoleActivationFlag(policyId);

		BeneficiaryChangeRequest request = new BeneficiaryChangeRequest();

		if (workflowItemId != null) {
			request.setWorkflowItemId(workflowItemId);
		}
		request.setPolicyId(policyId);
		request.setClientRoleActivationFlagDTO(beneficiaryRoleActivation);
		setupApplicationParams(request);

		return request;
	}

	private ClientRoleActivationFlagDTO setupClientRoleActivationFlag(String policyId) {
		ClientRoleActivationFlagDTO clientRoleActivationFlag = clientRoleActivationFlagService.solveAllRoleActivations(policyId);

		return clientRoleActivationFlag;
	}

	private void setupApplicationParams(BeneficiaryChangeRequest request) {
		// provide application parameters used by liability to process the form
		Map<String, String> applicationParams = getApplicationParams();

		request.setApplicationParams(applicationParams);
	}

	private Map<String, String> getApplicationParams() {
		String[] applicationParameterKey = new String[] { RULES_2_POLICYHOLDERS_NORD, CHANGE_BENEF_AVAILABLE_SUB_ROLES };

		Map<String, String> applicationParams = new HashMap<>();
		for (String key : applicationParameterKey) {
			applicationParams.put(key, applicationParameterService.getApplicationParameter(key).getValue());
		}
		return applicationParams;
	}


	@Override
	public BeneficiaryChangeFormDTO completeFormData(BeneficiaryChangeFormDTO formData, String stepWorkflow, String usrId) {
		StepTypeDTO stepType = StepTypeDTO.getStepType(stepWorkflow);

		if (StepTypeDTO.AWAITING_ACTIVATION == stepType) {

			BeneficiaryChangeDTO beneficiaryChange = beneficiaryChangeFormMapper.asBeneficiaryChangeDTO(formData);
			ApplyBeneficiaryChangeRequest request = createApplyBeneficiaryChangeRequest(beneficiaryChange);

			beneficiaryChange = restClientUtils.post(LIABILITY_BENEFICIARY_CHANGE + APPLY_CHANGE, request, BeneficiaryChangeDTO.class);

			EditingRequest editingResponse = editingService.createWorkflowDocumentRequest(Long.valueOf(formData.getWorkflowItemId()), DocumentType.CHANGE_BENEF, false, false);
			logger.info("Edition request created for the worklow item id {} with editing id {}", formData.getWorkflowItemId(), editingResponse.getId());

			return beneficiaryChangeFormMapper.asBeneficiaryChangeFormDTO(beneficiaryChange, formData);
		}

		super.completeFormData(formData, stepWorkflow, usrId);

		return formData;
	}

	private ApplyBeneficiaryChangeRequest createApplyBeneficiaryChangeRequest(BeneficiaryChangeDTO beneficiaryChange) {
		ApplyBeneficiaryChangeRequest request = new ApplyBeneficiaryChangeRequest();

		request.setBeneficiaryChange(beneficiaryChange);
		request.setApplicationParams(getApplicationParams());

		return request;
	}

	@Override
	public BeneficiaryChangeFormDTO previousFormData(BeneficiaryChangeFormDTO formData, String stepWorkflow, String usrId) {
		return formData;
	}
	
	@Override
	protected WorkflowItemDataDTO getCompleteMetadata(BeneficiaryChangeFormDTO appForm, String usrId, StepTypeDTO stepType) {
		return new WorkflowItemDataDTO();
	}

}
