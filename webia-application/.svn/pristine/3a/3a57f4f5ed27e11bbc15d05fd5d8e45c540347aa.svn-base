package lu.wealins.webia.core.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemAllActionsDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.WorkflowTriggerActionDTO;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.impl.exception.StepException;

@Component
public class LiabilityWorkflowServiceImpl implements LiabilityWorkflowService {

	private static final String LIABILITY_WORKFLOW_ITEM_ACTIONS = "liability/workflow";
	private static final String LIABILITY_GENERAL_INFORMATION = "liability/workflow/generalInformation/";
	private static final String LIABILITY_WORKFLOW_ITEM = "liability/workflow/workflowItem/";
	private static final String LIABILITY_WORFLOW_SAVE_METADATA_STEP = "liability/workflow/saveMetadata";
	private static final String NEXT = "NEXT";
	private static final String PREVIOUS = "PREVIOUS";
	private static final String ABORT = "ABORT";
	private static final String RECREATE_WORKFLOW = "NEW_INPUT";
	private static final String LIABILITY_WORKFLOW_SUBMIT_STEP = "liability/workflow/submit";
	private static final String UNDERSCORE = "_";
	private static final String SPACE = " ";

	private final Logger log = LoggerFactory.getLogger(LiabilityWorkflowServiceImpl.class);

	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private WebiaWorkflowUserService workflowUserService;
	@Autowired
	private MetadataService metadataService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#abort(java.lang.Integer, java.lang.String)
	 */
	@Override
	public String abort(Integer workflowItemId, String usrId) {
		return submitWorkflowAction(workflowItemId, usrId, ABORT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#previous(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public String previous(Integer workflowItemId, String stepWorkflow, String usrId) {
		Assert.notNull(stepWorkflow);

		// Example : PREVIOUS_SUBSCRIPTION_AWAITING_VALUATION
		WorkflowTriggerActionDTO workflowTriggerAction = createAction(PREVIOUS, getWorkflowName(workflowItemId, usrId), stepWorkflow, workflowItemId, usrId);

		return restClientUtils.post(LIABILITY_WORKFLOW_SUBMIT_STEP, workflowTriggerAction, String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#next(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public String next(Integer workflowItemId, String stepWorkflow, String usrId) {
		Assert.notNull(stepWorkflow);

		// Example : NEXT_SUBSCRIPTION_AWAITING_VALUATION
		WorkflowTriggerActionDTO workflowTriggerAction = createAction(NEXT, getWorkflowName(workflowItemId, usrId), stepWorkflow, workflowItemId, usrId);

		return restClientUtils.post(LIABILITY_WORKFLOW_SUBMIT_STEP, workflowTriggerAction, String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#saveMetada(lu.wealins.common.dto.liability.services.WorkflowItemDataDTO)
	 */
	@Override
	public void saveMetada(WorkflowItemDataDTO workflowItemDataDTO) {
		if (!CollectionUtils.isEmpty(workflowItemDataDTO.getMetadata())) {
			restClientUtils.post(LIABILITY_WORFLOW_SAVE_METADATA_STEP, workflowItemDataDTO, String.class);
		}
	}

	@Override
	public void saveMetada(Long workflowItemId, String userId, MetadataDTO... metadata) {
		saveMetada(workflowItemId, userId, Arrays.asList(metadata));
	}

	@Override
	public void saveMetada(Long workflowItemId, String userId, List<MetadataDTO> metadata) {
		WorkflowItemDataDTO workflowItemData = metadataService.createWorkflowItemData(workflowItemId, metadata, userId);

		saveMetada(workflowItemData);
	}

	private String createActionName(String workflowName, String... subActions) {
		StringBuilder builder = new StringBuilder(subActions[0]);
		builder = builder.append(UNDERSCORE).append(workflowName);
		if (subActions.length >= 2) {
			builder = builder.append(UNDERSCORE).append(subActions[1]);
		}
		return builder.toString().toUpperCase().replace(SPACE, UNDERSCORE);
	}

	private String getWorkflowName(Integer workflowItemId, String usrId) {
		WorkflowGeneralInformationDTO workflowGeneralInformationDTO = getWorkflowGeneralInformation(workflowItemId + "", usrId);
		return workflowGeneralInformationDTO.getActionOther().toUpperCase();
	}

	private WorkflowTriggerActionDTO createAction(String actionType, String workflowName, String stepWorkflow, Integer workflowItemId, String usrId) {
		WorkflowTriggerActionDTO workflowTriggerAction = new WorkflowTriggerActionDTO();

		String triggerAction = stepWorkflow != null ? createActionName(workflowName, actionType, stepWorkflow) : createActionName(workflowName, actionType);

		workflowTriggerAction.setTriggerAction(triggerAction);
		workflowTriggerAction.setWorkflowItemId(workflowItemId);
		workflowTriggerAction.setUsrId(usrId);

		log.info(triggerAction);

		return workflowTriggerAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#getWorkflowItemAllActions(java.lang.Integer, java.lang.String)
	 */
	@Override
	public WorkflowItemAllActionsDTO getWorkflowItemAllActions(Integer workitemId, String usrId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("usrId", usrId);

		return restClientUtils.get(LIABILITY_WORKFLOW_ITEM_ACTIONS, "/" + workitemId + "/allActions", params, WorkflowItemAllActionsDTO.class);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#getWorkflowItemActions(java.lang.String, java.lang.String)
	 */
	@Override
	public WorkflowItemActionsDTO getWorkflowItemActions(String workitemId, String usrId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("usrId", usrId);

		WorkflowItemActionsDTO workflowItemActions = restClientUtils.get(LIABILITY_WORKFLOW_ITEM_ACTIONS, "/" + workitemId + "/actions", params, WorkflowItemActionsDTO.class);

		enrichWithLogin(workflowItemActions);

		return workflowItemActions;
	}

	private void enrichWithLogin(WorkflowItemActionsDTO workflowItemActions) {
		workflowItemActions.getActions().forEach(a -> {
			if(a.getUser() != null) {
				String user = a.getUser();
				String login = workflowUserService.getLogin(user);
				if (login == null) {
					a.setLogin(user);
				} else {
					a.setLogin(login);
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#getWorkflowGeneralInformation(java.lang.String, java.lang.String)
	 */
	@Override
	public WorkflowGeneralInformationDTO getWorkflowGeneralInformation(String workflowItemId, String usrId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("usrId", usrId);

		return restClientUtils.get(LIABILITY_GENERAL_INFORMATION, workflowItemId, params,
				WorkflowGeneralInformationDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityWorkflowService#getWorkflowItem(java.lang.String, java.lang.String)
	 */
	@Override
	public WorkflowItemDataDTO getWorkflowItem(String workflowItemId, String usrId) {

		Assert.notNull(usrId, "userId cannot be null.");
		Assert.notNull(workflowItemId, "workflowItemId cannot be null.");

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("usrId", usrId);

		WorkflowItemDataDTO workflowItem = restClientUtils.get(LIABILITY_WORKFLOW_ITEM, workflowItemId, params,
				WorkflowItemDataDTO.class);

		if (workflowItem == null || workflowItem.getWorkflowItemTypeId() == null) {
			StepException stepException = new StepException();
			stepException.setErrors(Arrays.asList("There is no workflow item type linked to the workflow item :" + workflowItemId + "."));
			throw stepException;
		}

		return workflowItem;
	}
	
	@Override
	public String updateDueDateWorkflowItem(String workflowItemId, String dueDate ,String usrId){
		Assert.notNull(usrId, "userId cannot be null.");
		Assert.notNull(workflowItemId, "workflowItemId cannot be null.");
		
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("workflowItemId", workflowItemId);
		params.add("dueDate", dueDate);
		params.add("usrId", usrId);
		return restClientUtils.get(LIABILITY_WORKFLOW_ITEM, workflowItemId + "/dueDate", params, String.class);
	}

	@Override
	public String recreateWorkflow(Integer workflowItemId, String usrId) {
		return submitWorkflowAction(workflowItemId, usrId, RECREATE_WORKFLOW);
	}

	private String submitWorkflowAction(Integer workflowItemId, String usrId, String action) {
		Assert.notNull(workflowItemId);
		Assert.notNull(usrId);

		WorkflowTriggerActionDTO workflowTriggerAction = createAction(action, getWorkflowName(workflowItemId, usrId), null, workflowItemId, usrId);

		return restClientUtils.post(LIABILITY_WORKFLOW_SUBMIT_STEP, workflowTriggerAction, String.class);
	}

}
