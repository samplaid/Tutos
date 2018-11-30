package lu.wealins.batch.relaunchstep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.util.Assert;

import lu.wealins.batch.simplerest.SimpleRestTaskLet;
import lu.wealins.common.dto.webia.services.RelaunchStepRequest;
import lu.wealins.common.dto.webia.services.RelaunchStepResponse;

public class RelaunchStepTaskLet extends SimpleRestTaskLet<RelaunchStepRequest, RelaunchStepResponse> {

	private static final String ACTION_OTHER = "actionOther";
	private static final String ACTION_REQUIRED = "actionRequired";
	private static final String WORKFLOW_ITEM_TYPE = "workflowItemType";
	private static final String WORKFLOW_ITEM_ID = "workflowItemId";

	private Log logger = LogFactory.getLog(RelaunchStepTaskLet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.batch.simplerest.SimpleRestTaskLet#createRequest(org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@SuppressWarnings("boxing")
	@Override
	public RelaunchStepRequest createRequest(ChunkContext chunkContext) {
		Integer workflowItemId = batchUtilityService.getIntegerJobParameter(chunkContext, WORKFLOW_ITEM_ID);

		RelaunchStepRequest request = new RelaunchStepRequest();

		if (workflowItemId != null) {
			request.setWorkflowItemId(new Long(workflowItemId));
		}

		Integer workflowItemType = batchUtilityService.getIntegerJobParameter(chunkContext, WORKFLOW_ITEM_TYPE);
		if (workflowItemType != null) {
			request.setWorkflowItemType(workflowItemType);
		}

		Integer actionRequired = batchUtilityService.getIntegerJobParameter(chunkContext, ACTION_REQUIRED);
		if (actionRequired != null) {
			request.setActionRequired(actionRequired);
		}

		String actionOther = batchUtilityService.getStringJobParameter(chunkContext, ACTION_OTHER);
		if (actionRequired != null) {
			request.setActionOther(actionOther);
		}

		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.batch.simplerest.SimpleRestTaskLet#createExitMessage(java.lang.Object)
	 */
	@Override
	public String createExitMessage(RelaunchStepResponse response) {
		Assert.notNull(response, "There is no response for relaunch step batch.");

		logger.info("Response : " + response.getMessages().toString());

		return response.getMessages().toString();
	}


}
