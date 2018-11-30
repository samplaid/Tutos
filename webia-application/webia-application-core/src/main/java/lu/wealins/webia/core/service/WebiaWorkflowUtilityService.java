package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.List;

public interface WebiaWorkflowUtilityService {

	/**
	 * Get workflow item Ids according to the workflow item type name and the
	 * workflow action.
	 * 
	 * @param workflowItemTypeName
	 *            The workflow item type name.
	 * @param workflowAction
	 *            The workflow action.
	 * @param excludedStatus
	 * @return The workflow item Ids.
	 */
	Collection<Long> getWorkflowItemIds(Integer workflowItemType, Integer actionRequired, List<Integer> excludedStatus);
}
