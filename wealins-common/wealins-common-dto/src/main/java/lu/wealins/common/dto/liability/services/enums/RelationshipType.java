package lu.wealins.common.dto.liability.services.enums;

public enum RelationshipType {
	OWNER(1), JOINTOWNER(2), ADDITIONALOWNER(3);

	private int value;

	private RelationshipType(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
