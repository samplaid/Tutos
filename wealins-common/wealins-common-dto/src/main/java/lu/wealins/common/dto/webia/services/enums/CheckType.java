package lu.wealins.common.dto.webia.services.enums;

public enum CheckType {

	YES_NO("YesNo"), YES_NO_NA("YesNoNa"), TEXT("Text"), DATE("Date"), NUMBER("Number"), AMOUNT("Amount"), LIST("List");

	private String type;

	private CheckType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
