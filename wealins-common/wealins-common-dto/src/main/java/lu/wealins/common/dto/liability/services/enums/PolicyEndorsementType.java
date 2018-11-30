package lu.wealins.common.dto.liability.services.enums;

public enum PolicyEndorsementType {

	SENDING_RULE("P8"), MAIL_TO_AGENT("P21"), BROKER_CONTRACT_REF("P22"), ADMIN_FEE("14");

	private String type;

	private PolicyEndorsementType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
