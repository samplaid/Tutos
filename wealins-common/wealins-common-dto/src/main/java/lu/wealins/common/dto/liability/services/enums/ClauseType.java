package lu.wealins.common.dto.liability.services.enums;

public enum ClauseType {

	NOMINATIVE("N");

	private final String code;

	ClauseType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
