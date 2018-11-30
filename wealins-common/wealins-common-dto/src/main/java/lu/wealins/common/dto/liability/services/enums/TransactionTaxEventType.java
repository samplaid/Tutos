package lu.wealins.common.dto.liability.services.enums;

public enum TransactionTaxEventType {
	PRIM(2), SURR(4), DECE(17), WITH(15), MATU(21);

	private int value;

	private TransactionTaxEventType(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
