package lu.wealins.common.dto.liability.services.enums;

public enum PricingFrequencyType {

	DAILY(4);
	
	private int value;
	
	private PricingFrequencyType(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
