package lu.wealins.common.dto.liability.services.enums;

public enum PolicyStatus {

	IN_FORCE(1), TERMINATED(2), PENDING(3), NEW_BUSINESS_ENTRY(4), NOT_PROCEEDED_WITH(5), PENDING_TERMINATION(6);

	private int status;

	private PolicyStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
