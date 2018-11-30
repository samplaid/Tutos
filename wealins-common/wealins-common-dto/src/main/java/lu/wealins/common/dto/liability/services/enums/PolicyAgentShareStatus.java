package lu.wealins.common.dto.liability.services.enums;

public enum PolicyAgentShareStatus {

	ACTIVE(1), INACTIVE(2), AMENDMENT(3);

	private int status;

	private PolicyAgentShareStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
