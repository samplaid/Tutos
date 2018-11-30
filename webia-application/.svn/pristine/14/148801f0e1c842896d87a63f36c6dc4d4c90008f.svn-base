package lu.wealins.webia.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.mapper.BrokerChangeFormMapper;
import lu.wealins.webia.core.service.EditingService;
import lu.wealins.webia.core.service.LiabilityBrokerChangeFormService;
import lu.wealins.webia.core.service.LiabilityBrokerChangeService;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class LiabilityBrokerChangeFormServiceImpl extends LiabilityAmendmentWorkflowServiceImpl<BrokerChangeFormDTO> implements LiabilityBrokerChangeFormService {
	private static Logger logger = LoggerFactory.getLogger(LiabilityBrokerChangeFormServiceImpl.class);

	@Autowired
	private LiabilityBrokerChangeService brokerChangeService;

	@Autowired
	private BrokerChangeFormMapper brokerChangeFormMapper;

	@Autowired
	private EditingService editingService;

	@Override
	public BrokerChangeFormDTO initBrokerChangeForm(String policyId, Integer workflowItemId) {

		AmendmentRequest request = createAmendmentRequest(policyId, workflowItemId);

		BrokerChangeDTO brokerChange = brokerChangeService.initBrokerChange(request);

		return brokerChangeFormMapper.asBrokerChangeFormDTO(brokerChange);
	}

	@Override
	public BrokerChangeFormDTO completeFormData(BrokerChangeFormDTO formData, String stepWorkflow, String usrId) {
		StepTypeDTO stepType = StepTypeDTO.getStepType(stepWorkflow);
		BrokerChangeDTO brokerChange = null;

		switch (stepType) {
		case AWAITING_ACTIVATION:
			brokerChange = brokerChangeFormMapper.asBrokerChangeDTO(formData);
			brokerChange = brokerChangeService.applyChange(brokerChange);
			return brokerChangeFormMapper.asBrokerChangeFormDTO(brokerChange);
		case GENERATE_DOCUMENTATION:
			EditingRequest editingResponse = editingService.createWorkflowDocumentRequest(Long.valueOf(formData.getWorkflowItemId()), DocumentType.CHANGE_BROKER, false, false);
			logger.info("Edition request created for the worklow item id {} with editing id {}", formData.getWorkflowItemId(), editingResponse.getId());
			break;
		case AWAITING_ACTIVATION_FEES:
			brokerChange = brokerChangeFormMapper.asBrokerChangeDTO(formData);
			brokerChange = brokerChangeService.applyChangeToAdminFees(brokerChange);
			return brokerChangeFormMapper.asBrokerChangeFormDTO(brokerChange);
		default:
			break;
		}

		return super.completeFormData(formData, stepWorkflow, usrId);
	}

	@SuppressWarnings("boxing")
	@Override
	public BrokerChangeFormDTO updateFormData(BrokerChangeFormDTO formData, String stepWorkflow, String userId) {
		BrokerChangeFormDTO formDataDTO = super.updateFormData(formData, stepWorkflow, userId);
		BrokerChangeDTO brokerChange = brokerChangeFormMapper.asBrokerChangeDTO(formDataDTO);

		brokerChange = brokerChangeService.update(brokerChange);

		return brokerChangeFormMapper.asBrokerChangeFormDTO(brokerChange);
	}

	@Override
	public BrokerChangeFormDTO enrichFormData(BrokerChangeFormDTO formData, String stepWorkflow, String userId) {
		super.enrichFormData(formData, stepWorkflow, userId);

		if (formData.getPolicyId() == null) {
			return formData;
		}

		BrokerChangeDTO brokerChange = brokerChangeService.load(formData.getWorkflowItemId());

		brokerChangeFormMapper.asBrokerChangeFormDTO(brokerChange, formData);

		return formData;
	}

	@Override
	public WorkflowType getSupportedWorkflowType() {
		return WorkflowType.BROKER_CHANGE;
	}

	@Override
	protected WorkflowItemDataDTO getCompleteMetadata(BrokerChangeFormDTO form, String usrId, StepTypeDTO stepType) {
		switch (stepType) {
		case SENDING:
			return createAdmChangeMetadata(usrId, form);
		default:
			return new WorkflowItemDataDTO();
		}
	}

	private WorkflowItemDataDTO createAdmChangeMetadata(String usrId, BrokerChangeFormDTO form) {

		BrokerChangeDTO brokerChange = brokerChangeFormMapper.asBrokerChangeDTO(form);
		Boolean hasAdminFeesChanged = brokerChangeService.hasAdminFeeChanged(brokerChange);
		MetadataDTO externalFundMetadata = metadataService.createMetadata(Metadata.ADM_CHANGE.getMetadata(), hasAdminFeesChanged.toString());

		return metadataService.createWorkflowItemData(form.getWorkflowItemId().longValue(), usrId, externalFundMetadata);
	}

}
