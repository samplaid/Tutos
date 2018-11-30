package lu.wealins.common.dto.liability.services;

public enum PolicyChangeStatus {

	IN_FORCE(1), PENDING(2), TERMINATED(3);

	private final Integer value;

	PolicyChangeStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static PolicyChangeStatus fromNumber(Integer value) {
		for (PolicyChangeStatus enumValue : PolicyChangeStatus.values()) {
			if (enumValue.getValue().equals(value)) {
				return enumValue;
			}
		}
		throw new IllegalArgumentException(String.format("No status match for value : %s", value));
	}
}
