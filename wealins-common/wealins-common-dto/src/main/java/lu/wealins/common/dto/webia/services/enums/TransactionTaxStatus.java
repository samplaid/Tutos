package lu.wealins.common.dto.webia.services.enums;

public enum TransactionTaxStatus {
	NEW(1), CALCULATED_EDITION(2), CANCELLED(3), CALCULATED_NO_EDITION(0), DATA_BACKUP(4), CALCULATED(5);

	private final Integer statusNumber;

	public Integer getStatusNumber() {
		return statusNumber;
	}

	TransactionTaxStatus(Integer statusNumber) {
		this.statusNumber = statusNumber;
	}
}