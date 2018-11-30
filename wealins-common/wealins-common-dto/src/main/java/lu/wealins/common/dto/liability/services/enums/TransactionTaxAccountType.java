package lu.wealins.common.dto.liability.services.enums;

public enum TransactionTaxAccountType {
	CASHSUSP(2), SURR(4), DETHBEN(17), WDNPEN(15), MATBEN(21);

	private int value;

	private TransactionTaxAccountType(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
