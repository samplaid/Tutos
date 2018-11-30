package lu.wealins.webia.services.core.service;

import java.util.Collection;
import java.util.List;

public interface WorkflowUtilityService {

	/**
	 * Get workflow item ids according to the workflow item type name and the workflow action.
	 * 
	 * @param workflowItemType The workflow item type.
	 * @param actionRequired The action required.
	 * @param excludedStatus the status of excluded workflow items
	 * @return The workflow item ids.
	 */
	Collection<Long> getWorkflowItemIds(Integer workflowItemType, Integer actionRequired, List<Integer> excludedStatus);
	/**
	 * Retrieve the workflow item based on the policy id.
	 * 
	 * @param policyId
	 *            the policy id
	 * @return Null if the policy is not found
	 */
	Long getWorkflowItemId(String policyId);

}
