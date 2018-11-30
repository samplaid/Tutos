package lu.wealins.liability.services.ws.rest.impl;

import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.AuditService;
import lu.wealins.liability.services.core.business.WorkflowItemService;
import lu.wealins.liability.services.ws.rest.WorkflowRESTService;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.WorkflowTriggerActionDTO;

@Component
public class WorkflowRESTServiceImpl implements WorkflowRESTService {

	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String USR_ID_CANNOT_BE_NULL = "User id cannot be null.";
	private static final String DUE_DATE_CANNOT_BE_NULL = "Due date cannot be null.";

	@Autowired
	private WorkflowItemService workflowItemService;
	@Autowired
	private AuditService auditService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowRESTService#saveMetadata(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.WorkflowItemDataDTO)
	 */
	@Override
	public Response saveMetadata(SecurityContext context, WorkflowItemDataDTO workflowItemData) {

		workflowItemService.saveMetadata(workflowItemData);

		return Response.ok("Save metadata done.").build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowRESTService#getGeneralInformation(javax.ws.rs.core.SecurityContext, java.lang.Long, java.lang.String)
	 */
	@Override
	public WorkflowGeneralInformationDTO getGeneralInformation(SecurityContext context, Long workflowItemId, String usrId) {
		return workflowItemService.getGeneralInformation(workflowItemId, usrId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowRESTService#submit(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.WorkflowTriggerActionDTO)
	 */
	@Override
	public Response submit(SecurityContext context, WorkflowTriggerActionDTO workflowTriggerAction) {
		Assert.notNull(workflowTriggerAction);
		auditService.submit(workflowTriggerAction);

		return Response.ok("Submit registration done.").build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowRESTService#getAllWorkItemActions(javax.ws.rs.core.SecurityContext, java.lang.Integer, java.lang.String)
	 */
	@Override
	public WorkflowItemAllActionsDTO getAllWorkItemActions(SecurityContext context, Integer workItemId, String usrId) {

		return workflowItemService.getAllWorkItemActions(workItemId, usrId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowRESTService#getWorkItemActions(javax.ws.rs.core.SecurityContext, java.lang.Integer, java.lang.String)
	 */
	@Override
	public WorkflowItemActionsDTO getWorkItemActions(SecurityContext context, Integer workItemId, String usrId) {

		return workflowItemService.getWorkItemActions(workItemId, usrId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowRESTService#getWorkflowItem(javax.ws.rs.core.SecurityContext, java.lang.Long, java.lang.String)
	 */
	@Override
	public WorkflowItemDataDTO getWorkflowItem(SecurityContext context, Long workflowItemId, String usrId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(usrId, USR_ID_CANNOT_BE_NULL);

		return workflowItemService.getWorkflowItem(workflowItemId, usrId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.WorkflowRESTService#updateDueDateWorkflowItem(javax.ws.rs.core.SecurityContext, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public Response updateDueDateWorkflowItem(SecurityContext context, Long workflowItemId, String dueDate , String usrId){
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(usrId, USR_ID_CANNOT_BE_NULL);
		Assert.notNull(dueDate, DUE_DATE_CANNOT_BE_NULL);

		workflowItemService.updateDueDateWorkflowItem(workflowItemId, dueDate, usrId);
		
		return Response.ok("Due date updated.").build();
	}
}
