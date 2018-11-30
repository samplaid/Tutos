package lu.wealins.liability.services.core.business.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.ws.namespaces.axis2._enum.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowTriggerActionDTO;
import lu.wealins.liability.services.core.business.AuditService;
import lu.wealins.liability.services.core.business.WorkflowItemService;
import lu.wealins.liability.services.core.business.exceptions.AuditServiceException;
import lu.wealins.common.dto.liability.services.enums.WorkflowStatus;
import uk.co.liss.webservice.audit.request.xsd.SaveAuditRequest;
import uk.co.liss.webservice.audit.service.AuditServiceException_Exception;
import uk.co.liss.webservice.audit.service.AuditServicePortType;
import uk.co.liss.webservice.core.domain.xsd.AuditData;

@Service
public class AuditServiceImpl implements AuditService {

	private static final String WORKFLOW_TRIGGER_ACTION_CANNOT_BE_NULL = "Workflow trigger action cannot be null.";
	private static final String USR_ID_CANNOT_BE_NULL = "User id cannot be null.";
	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String WD = "WD";
	private static final String PAGE_CODE = "1";
	private static final List<String> POSSIBLE_ACTIONS_ON_ENDED = Arrays.asList("NEW_INPUT_SUBSCRIPTION", "NEW_INPUT_ADDITIONAL_PREMIUM");

	@Autowired
	private AuditServicePortType AUDIT_SERVICE;

	@Autowired
	private WorkflowItemService workflowItemService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AuditService#submit(lu.wealins.common.dto.liability.services.WorkflowTriggerActionDTO)
	 */
	@Override
	public void submit(WorkflowTriggerActionDTO workflowTriggerAction) {
		Assert.notNull(workflowTriggerAction, WORKFLOW_TRIGGER_ACTION_CANNOT_BE_NULL);
		String usrId = workflowTriggerAction.getUsrId();
		Assert.notNull(usrId, USR_ID_CANNOT_BE_NULL);
		Integer workflowItemId = workflowTriggerAction.getWorkflowItemId();
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		String workflowItemIdStr = workflowItemId.toString();
		checkStatusNextStep(workflowItemIdStr, usrId, workflowTriggerAction.getTriggerAction());
		SaveAuditRequest request = createSaveAuditRequest(workflowTriggerAction);

		try {
			AUDIT_SERVICE.saveAudit(request);
		} catch (AuditServiceException_Exception e) {
			throw new AuditServiceException("Cannot complete the step with the workflow item " + workflowItemIdStr + ".", e);
		}
	}

	/**
	 * Check the status before the next step.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId Ther user id.
	 * @param action
	 */
	@SuppressWarnings("boxing")
	private void checkStatusNextStep(String workflowItemId, String usrId, String action) {
		WorkflowGeneralInformationDTO generalInformation = workflowItemService.getGeneralInformation(Long.parseLong(workflowItemId), usrId);

		String status = generalInformation.getStatus();
		if (!canSubmit(workflowItemId, usrId, status, action)) {
			throw new IllegalStateException(String.format("Invalid action %s for workflow item id %s in status %s", action, workflowItemId, status));
		}
	}

	private boolean canSubmit(String workflowItemId, String usrId, String status, String action) {
		return isActive(status)
				|| (isLocked(status) && isFirstStep(workflowItemId, usrId))
				|| (isEnded(status) && isEndedAction(action));
	}

	private boolean isEndedAction(String action) {
		return POSSIBLE_ACTIONS_ON_ENDED.contains(action);
	}

	private boolean isFirstStep(String workflowItemId, String usrId) {
		return workflowItemService.isFirstStep(workflowItemId, usrId);
	}

	private boolean isLocked(String status) {
		return WorkflowStatus.LOCKED.getValue() == Integer.parseInt(status);
	}

	private boolean isActive(String status) {
		return WorkflowStatus.ACTIVE.getValue() == Integer.parseInt(status);
	}

	private boolean isEnded(String status) {
		return WorkflowStatus.ENDED.getValue() == Integer.parseInt(status);
	}

	private SaveAuditRequest createSaveAuditRequest(WorkflowTriggerActionDTO workflowTriggerAction) {
		SaveAuditRequest request = new SaveAuditRequest();

		request.setAuditData(createAuditData(workflowTriggerAction.getUsrId()));
		request.setContextData(workflowTriggerAction.getWorkflowItemId().toString());
		request.setDataType(DataType.WORKFLOW_ITEM_ID);
		request.setMethod(workflowTriggerAction.getTriggerAction());
		request.setPageCode(WD);
		request.setUserId(workflowTriggerAction.getUsrId().toString());
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AuditService#createAuditData(java.lang.String)
	 */
	@Override
	public AuditData createAuditData(String usrId) {
		AuditData auditData = new AuditData();

		auditData.setGhostUserId(usrId);
		auditData.setPageCode(PAGE_CODE);
		auditData.setUserId(usrId);

		return auditData;
	}

}
