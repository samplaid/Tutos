package lu.wealins.common.dto.liability.services.enums;

public enum PolicyEventType {

	SENDING_RULE("PENP8", PolicyEndorsementType.SENDING_RULE), MAIL_TO_AGENT("PENP21", PolicyEndorsementType.MAIL_TO_AGENT), BROKER_CONTRACT_REF("PENP22",
			PolicyEndorsementType.BROKER_CONTRACT_REF), ADMIN_FEE("E14_C12RAT", PolicyEndorsementType.ADMIN_FEE);

	private String type;
	private PolicyEndorsementType endorsementType;

	private PolicyEventType(String type, PolicyEndorsementType endorsementType) {
		this.type = type;
		this.endorsementType = endorsementType;
	}

	public String getType() {
		return type;
	}

	public PolicyEndorsementType getEndorsementType() {
		return endorsementType;
	}
}
