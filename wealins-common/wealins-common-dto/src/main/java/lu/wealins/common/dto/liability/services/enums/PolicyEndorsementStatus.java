package lu.wealins.common.dto.liability.services.enums;

public enum PolicyEndorsementStatus {

	ACTIVE(1);

	private Integer status;

	private PolicyEndorsementStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

}
