package lu.wealins.webia.core.service;

import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface WorkflowItemDataService {

	/**
	 * Create workflow item metadata linked to the registration.
	 * 
	 * @param subscription The registration.
	 * @param usrId The user id.
	 * @return The workflow item metadata.
	 */
	WorkflowItemDataDTO createCommonMetadata(AppFormDTO subscription, String usrId);

	WorkflowItemDataDTO createCommonMetadata(String policyNumber, Long workflowItemId, String userId);

	/**
	 * Create workflow item metadata linked to the dispatch.
	 * 
	 * @param subscription The dispatch.
	 * @param usrId The user id.
	 * @return The workflow item metadata.
	 */
	WorkflowItemDataDTO createDispatchWorkflowItemData(AppFormDTO subscription, String usrId);

	/**
	 * Create workflow item metadata linked to the analysis.
	 * 
	 * @param subscription The analysis.
	 * @param usrId The user id.
	 * @return The workflow item metadata.
	 */
	WorkflowItemDataDTO createAnalysisWorkflowItemData(AppFormDTO subscription, String usrId);

}
