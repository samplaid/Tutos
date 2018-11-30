package lu.wealins.liability.services.core.business.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowActionSummaryDTO;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.liability.services.core.business.AuditService;
import lu.wealins.liability.services.core.business.WorkflowItemService;
import lu.wealins.liability.services.core.business.exceptions.WorkflowItemServiceException;
import lu.wealins.liability.services.core.mapper.WorkflowGeneralInformationMapper;
import lu.wealins.liability.services.core.mapper.WorkflowItemDataMapper;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.predicate.WorkflowItemMetadaPredicate;
import uk.co.liss.webservice.workflow.domain.xsd.WorkflowItemAction;
import uk.co.liss.webservice.workflow.request.xsd.CheckWorkflowItemStatusRequest;
import uk.co.liss.webservice.workflow.request.xsd.GetWorkflowActionSummaryRequest;
import uk.co.liss.webservice.workflow.response.xsd.CheckWorkflowItemStatusResponse;
import uk.co.liss.webservice.workflow.response.xsd.GetWorkflowActionSummaryResponse;
import uk.co.liss.webservice.workflow.service.WorkflowServiceException_Exception;
import uk.co.liss.webservice.workflow.service.WorkflowServicePortType;
import uk.co.liss.webservice.workflowitem.domain.xsd.WorkflowItemData;
import uk.co.liss.webservice.workflowitem.domain.xsd.WorkflowItemMetadata;
import uk.co.liss.webservice.workflowitem.request.xsd.FindWorkflowItemRequest;
import uk.co.liss.webservice.workflowitem.request.xsd.SaveWorkflowItemRequest;
import uk.co.liss.webservice.workflowitem.response.xsd.FindWorkflowItemResponse;
import uk.co.liss.webservice.workflowitem.service.WorkflowItemServiceException_Exception;
import uk.co.liss.webservice.workflowitem.service.WorkflowItemServicePortType;

@Service
public class WorkflowItemServiceImpl implements WorkflowItemService {

	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String USR_ID_CANNOT_BE_NULL = "User id cannot be null.";
	private static final String WORKFLOW_METADATA_NAME_CANNOT_BE_NULL = "Workflow metadata name cannot be null.";
	private static final String WORKFLOW_ITEM_CANNOT_BE_NULL = "Workflow item cannot be null.";

	@Value("${liability.ws.auditdata.credential.login}")
	private String auditUser;

	@Autowired
	WorkflowItemServicePortType WORKFLOW_ITEM_SERVICE;
	
	@Autowired
	WorkflowServicePortType WORKFLOW_SERVICE;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private WorkflowGeneralInformationMapper workflowGeneralInformationMapper;

	@Autowired
	private WorkflowItemDataMapper workflowItemDataMapper;

	@Autowired
	private CalendarUtils calendarUtils;

	private FindWorkflowItemResponse getWorkflowItemResponse(Long workflowItemId, String usrId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(usrId, USR_ID_CANNOT_BE_NULL);
		FindWorkflowItemRequest request = new FindWorkflowItemRequest();

		request.setWorkflowItemId(workflowItemId);
		request.setAuditData(auditService.createAuditData(usrId));

		try {
			return WORKFLOW_ITEM_SERVICE.findWorkflowItem(request);
		} catch (WorkflowItemServiceException_Exception e) {
			throw new WorkflowItemServiceException("Cannot retrieve the workflow " + workflowItemId + ".", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#getWorkflowItem(java.lang.Long, java.lang.String)
	 */
	@Override
	public WorkflowItemDataDTO getWorkflowItem(Long workflowItemId, String usrId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(usrId, USR_ID_CANNOT_BE_NULL);

		FindWorkflowItemResponse workflowItem = getWorkflowItemResponse(workflowItemId, usrId);

		WorkflowItemDataDTO workflowItemDataDTO = workflowItemDataMapper.asWorkflowItemDataDTO(workflowItem.getWorkflowItemData());
		if (workflowItemDataDTO != null) {
			workflowItemDataDTO.setUsrId(usrId);
		}

		return workflowItemDataDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#updateDueDateWorkflowItem(java.lang.Long, java.util.Date, java.lang.String)
	 */
	@Override
	public void updateDueDateWorkflowItem(Long workflowItemId, String dueDate , String usrId) {	
		if (dueDate == null ){
			return;
		}
		//find existing Workflow Item
		FindWorkflowItemResponse workflowItem = getWorkflowItemResponse(workflowItemId, usrId);

		if (workflowItem == null) {
			throw new WorkflowItemServiceException(WORKFLOW_ITEM_CANNOT_BE_NULL);
		}

		WorkflowItemData workflowItemData = workflowItem.getWorkflowItemData();
		
		if (workflowItemData == null) {
			throw new WorkflowItemServiceException("No existing Workflow item with id "+ workflowItemId);
		}
		
		workflowItemData.setDueDate(dueDate);

		SaveWorkflowItemRequest request = new SaveWorkflowItemRequest();

		// Set WorkflowItemTypeId to null otherwise the following exception is thrown:
		// org.apache.cxf.binding.soap.SoapFault: Both Workflow Item Id and Workflow Item Type Id supplied -- please supply only one
		workflowItemData.setWorkflowItemTypeId(null);

		request.setWorkflowItemData(workflowItemData);
		request.setAuditData(auditService.createAuditData(usrId));
		
		try {
			WORKFLOW_ITEM_SERVICE.saveWorkflowItem(request);
		} catch (WorkflowItemServiceException_Exception e) {
			throw new WorkflowItemServiceException("Cannot Update the workflow " + workflowItemId + ".", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#getGeneralInformation(java.lang.Long, java.lang.String)
	 */
	@Override
	public WorkflowGeneralInformationDTO getGeneralInformation(Long workflowItemId, String usrId) {
		FindWorkflowItemResponse workflowItem = getWorkflowItemResponse(workflowItemId, usrId);

		WorkflowGeneralInformationDTO workflowGeneralInformation = workflowGeneralInformationMapper.asWorkflowGeneralInformationDTO(workflowItem.getWorkflowItemData());

		WorkflowItemAllActionsDTO workItemActions = getAllWorkItemActions(workflowItemId.intValue(), usrId);
		if (workItemActions != null) {
			workflowGeneralInformation.setCurrentStepName(workItemActions.getCurrent().getAction());
		}

		return workflowGeneralInformation;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#saveMetadata(lu.wealins.common.dto.liability.services.WorkflowItemDataDTO)
	 */
	@Override
	public void saveMetadata(WorkflowItemDataDTO workflowItemData) {
		saveMetadata(workflowItemData.getWorkflowItemId(), workflowItemData.getMetadata(), workflowItemData.getUsrId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#saveMetadata(java.lang.Long, java.lang.String, lu.wealins.common.dto.liability.services.MetadataDTO[])
	 */
	@Override
	public void saveMetadata(Long workflowItemId, String usrId, MetadataDTO... metadata) {
		saveMetadata(workflowItemId, Arrays.asList(metadata), usrId);
	}

	private void saveMetadata(Long workflowItemId, Collection<MetadataDTO> metadata, String usrId) {
		FindWorkflowItemResponse workflowItem = getWorkflowItemResponse(workflowItemId, usrId);

		if (workflowItem == null) {
			throw new WorkflowItemServiceException(WORKFLOW_ITEM_CANNOT_BE_NULL);
		}

		WorkflowItemData workflowItemData = workflowItem.getWorkflowItemData();

		// No metadata in the step.
		if (workflowItemData == null) {
			return;
		}

		for (MetadataDTO m : metadata) {
			String name = m.getName();

			if (name == null) {
				throw new WorkflowItemServiceException(WORKFLOW_METADATA_NAME_CANNOT_BE_NULL);
			}

			WorkflowItemMetadata workflowItemMetadata = IterableUtils.find(workflowItemData.getWorkflowItemMetadatas(), new WorkflowItemMetadaPredicate(name));

			if (workflowItemMetadata == null) {
				throw new WorkflowItemServiceException("Workflow item metadata with name " + name + " was not found.");
			}

			workflowItemMetadata.setValue(m.getValue());
		}

		SaveWorkflowItemRequest request = new SaveWorkflowItemRequest();

		// Set WorkflowItemTypeId to null otherwise the following exception is thrown:
		// org.apache.cxf.binding.soap.SoapFault: Both Workflow Item Id and Workflow Item Type Id supplied -- please supply only one
		workflowItemData.setWorkflowItemTypeId(null);

		request.setWorkflowItemData(workflowItemData);
		request.setAuditData(auditService.createAuditData(usrId));

		try {
			WORKFLOW_ITEM_SERVICE.saveWorkflowItem(request);
		} catch (WorkflowItemServiceException_Exception e) {
			throw new WorkflowItemServiceException("Cannot save the workflow " + workflowItemId + ".", e);
		}
	}

	/**
	 * Get the list of the actions of the specified work item. The fulfilled actions are ordered by the action 
	 * date and the future actions is unordered. The current action contains one of the following status : 
	 * 		<ul><li>UNSET(0)</li>
				<li>ACTIVE(1)</li> 
				<li>SUSPENDED(2)</li> 
				<li>COMPLETED(3)</li> 
				<li>ENDED(4)</li>
				<li>LOCKED(5)</li>
				<li>ERROR(6)</li>
			</ul>
	 * 
	 * 
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#getAllWorkItemActions(java.lang.Integer, java.lang.String)
	 */
	@Override
	public WorkflowItemAllActionsDTO getAllWorkItemActions(Integer workItemId, String usrId) {
		
		Assert.notNull(workItemId);
		
		// Build the default response
		WorkflowItemAllActionsDTO rv = new WorkflowItemAllActionsDTO();
		
		// Web service invocation to get the list of actions
		GetWorkflowActionSummaryRequest req = new GetWorkflowActionSummaryRequest();
		req.setWorkflowItemId(workItemId);
		req.setAuditData(auditService.createAuditData(usrId));
		
		GetWorkflowActionSummaryResponse response;
		try {
			response = WORKFLOW_SERVICE.getWorkflowActionSummary(req);
			
		} catch (WorkflowServiceException_Exception e) {
			throw new WorkflowItemServiceException("Cannot get the workflow item actions with the id " + workItemId + " (Probably no queue).", e);
		}
		
		if (response != null && response.getWorkflowItemActions().size() > 0) {
			
			for (WorkflowItemAction wia : response.getWorkflowItemActions()) {
				
				if (wia == null) {
					throw new WorkflowItemServiceException("Cannot get the workflow item actions with the id " + workItemId + ".");
				}

				WorkflowActionSummaryDTO action = new WorkflowActionSummaryDTO();
				
				switch (wia.getStatus()) {
				case CURRENT:
					
					action.setAction(wia.getAction());
					action.setUser(wia.getUser());					
					rv.setCurrent(action);
					
					break;
					
				case NEXT:
					
					action.setAction(wia.getAction());
					rv.getNext().add(action);
					
					break;
					
				case PREVIOUS:
					
					action.setAction(wia.getAction());
					action.setUser(wia.getUser());
					
					if (StringUtils.hasText(wia.getDate())) {
						action.setDate(calendarUtils.createDate(wia.getDate()));
					}
					rv.getPrevious().add(action);
					
					break;
					
				default:
					break;
				
				}
			}
		}
		
		CheckWorkflowItemStatusRequest statusRequest = new CheckWorkflowItemStatusRequest();
		statusRequest.setAuditData(auditService.createAuditData(usrId));
		statusRequest.setWorkflowItemId(workItemId);
		
		// Web service invocation to get the current work item status
		try {
			CheckWorkflowItemStatusResponse checkWorkflowItemStatus = WORKFLOW_SERVICE.checkWorkflowItemStatus(statusRequest);
			
			if (checkWorkflowItemStatus != null && checkWorkflowItemStatus.getWorkflowItemStatus() != null) {
				rv.getCurrent().setWorkflowItemStatus(checkWorkflowItemStatus.getWorkflowItemStatus());
			}
			
		} catch (WorkflowServiceException_Exception e) {
			throw new WorkflowItemServiceException("Cannot get current workflow item status with " + workItemId + ".", e);
		}

		return rv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#getWorkItemActions(java.lang.Integer, java.lang.String)
	 */
	@Override
	public WorkflowItemActionsDTO getWorkItemActions(Integer workItemId, String usrId) {
		WorkflowItemAllActionsDTO workflowItemAllActions = getAllWorkItemActions(workItemId, usrId);
		WorkflowItemActionsDTO workflowItemActions = new WorkflowItemActionsDTO();
		List<WorkflowActionSummaryDTO> actions = new ArrayList<>();

		addPreviousActions(workflowItemAllActions, actions);
		addCurrentAction(workflowItemAllActions, actions);
		addNextActions(workflowItemAllActions, actions);
		workflowItemActions.setActions(actions);
		
		return workflowItemActions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.WorkflowItemService#isFirstStep(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isFirstStep(String workflowItemId, String usrId) {
		WorkflowItemActionsDTO workItemActions = getWorkItemActions(Integer.valueOf(workflowItemId), usrId);

		List<WorkflowActionSummaryDTO> actions = workItemActions.getActions();

		if (CollectionUtils.isEmpty(actions)) {
			return false;
		}

		WorkflowActionSummaryDTO firstStep = actions.iterator().next();

		WorkflowActionSummaryDTO currentStep = actions.stream().filter(x -> x.getWorkflowItemStatus() != null).findFirst().orElse(null);

		return currentStep != null && currentStep.getAction().equals(firstStep.getAction());
	}

	private void addNextActions(WorkflowItemAllActionsDTO workflowItemAllActions, List<WorkflowActionSummaryDTO> actions) {
		List<WorkflowActionSummaryDTO> nextActions = workflowItemAllActions.getNext();
		Set<String> actionNames = actions.stream().map(x -> x.getAction()).collect(Collectors.toSet());
		nextActions.removeIf(x -> actionNames.contains(x.getAction()));
		if (CollectionUtils.isEmpty(nextActions)) {
			return;
		}

		List<String> nextActionNames = nextActions.stream().map(x -> x.getAction()).collect(Collectors.toList());
		WorkflowActionSummaryDTO currentAction = workflowItemAllActions.getCurrent();
		WorkflowActionSummaryDTO lastAction = nextActions.get(nextActions.size() - 1);
		if (!(currentAction.getAction().equals(lastAction.getAction()))) {
			actions.addAll(nextActions.subList(nextActionNames.indexOf(workflowItemAllActions.getCurrent().getAction()) + 1, nextActions.size()));
		}
	}

	private void addPreviousActions(WorkflowItemAllActionsDTO workflowItemAllActions, List<WorkflowActionSummaryDTO> actions) {
		List<WorkflowActionSummaryDTO> previousActions = workflowItemAllActions.getPrevious();

		// remove successive step.
		for (int i = previousActions.size() - 1; i > 0; i--) {
			if (previousActions.get(i).getAction().equals(previousActions.get(i - 1).getAction())) {
				previousActions.remove(i);
			}
		}

		actions.addAll(previousActions);
	}

	private void addCurrentAction(WorkflowItemAllActionsDTO workflowItemAllActions, List<WorkflowActionSummaryDTO> actions) {
		WorkflowActionSummaryDTO currentAction = workflowItemAllActions.getCurrent();
		int index = actions.size() - 1;

		// Remove the last element if it is equals to the current one.
		if (!CollectionUtils.isEmpty(actions) && actions.get(index) != null && actions.get(index).getAction().equals(currentAction.getAction())) {
			actions.remove(index);
		}

		actions.add(currentAction);
	}


}
