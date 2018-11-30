package lu.wealins.common.dto.liability.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.enums.WorkflowType;

public enum PolicyChangeType {
	CBN, CAP;

	private static final Map<WorkflowType, PolicyChangeType> WORKFLOW_TYPE_TO_POLICY_CHANGE_TYPE_MAP = new HashMap<WorkflowType, PolicyChangeType>();

	static {
		WORKFLOW_TYPE_TO_POLICY_CHANGE_TYPE_MAP.put(WorkflowType.BENEFICIARY_CHANGE, CBN);
		WORKFLOW_TYPE_TO_POLICY_CHANGE_TYPE_MAP.put(WorkflowType.BROKER_CHANGE, CAP);
	}

	public static PolicyChangeType from(WorkflowType workflowType) {
		PolicyChangeType policyChangeType = WORKFLOW_TYPE_TO_POLICY_CHANGE_TYPE_MAP.get(workflowType);

		Assert.notNull(policyChangeType, "Unknown matching for " + workflowType.name() + ".");

		return policyChangeType;
	}
}
