package lu.wealins.common.dto.liability.services.enums;

import java.util.Arrays;

public enum WorkflowStatus {

	ACTIVE(1), SUSPENDED(2), COMPLETED(3), ENDED(4), LOCKED(5);

	private int value;

	private WorkflowStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@SuppressWarnings("static-access")
	public static WorkflowStatus getWorkflowStatus(final int value) {
		return Arrays.stream(WorkflowStatus.ACTIVE.values()).filter(x -> x.getValue() == value).findFirst().get();
	}
}
