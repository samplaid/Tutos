package lu.wealins.common.dto.webia.services.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum WorkflowType {

	APP_FORM(7), BENEFICIARY_CHANGE(11), ADDITIONAL_PREMIUM(12), BROKER_CHANGE(13), WITHDRAWAL(14), SURRENDER(15);

	public static Set<WorkflowType> ALL_WORKFLOW_TYPES = Arrays.stream(WorkflowType.values()).collect(Collectors.toCollection(HashSet::new));

	private final Integer value;

	WorkflowType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static WorkflowType getType(Integer value) {
		for (WorkflowType type : WorkflowType.values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException(String.format("No workflow type match for : %s", value));
	}
}
