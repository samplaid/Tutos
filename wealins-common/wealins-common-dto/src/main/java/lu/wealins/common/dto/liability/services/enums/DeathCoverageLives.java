package lu.wealins.common.dto.liability.services.enums;

public enum DeathCoverageLives {
	SINGLE(1),
	JOINT_FIRST_DEATH(2),
	JOINT_SECOND_DEATH(3),
	MULTIPLE(4);
	
	private final Integer code;

	private DeathCoverageLives(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}	
}
