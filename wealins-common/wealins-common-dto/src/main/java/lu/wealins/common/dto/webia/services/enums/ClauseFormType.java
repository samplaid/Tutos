package lu.wealins.common.dto.webia.services.enums;

public enum ClauseFormType {

	LIFE("L"), DEATH("D");

	private String type;

	private ClauseFormType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
