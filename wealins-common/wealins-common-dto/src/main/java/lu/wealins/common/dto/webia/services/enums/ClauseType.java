package lu.wealins.common.dto.webia.services.enums;

public enum ClauseType {
	FREE("Free"), STANDARD("Standard");

	private final String value;

	private ClauseType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
