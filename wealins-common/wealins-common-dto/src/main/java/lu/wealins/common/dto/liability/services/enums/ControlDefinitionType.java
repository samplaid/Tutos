package lu.wealins.common.dto.liability.services.enums;

public enum ControlDefinitionType {

	POLICY_FEE("POLFEE"), CONTRACT_MANAGEMENT_FEE("C12RAT"), C13RAT("C13RAT"), SWITCH_FEE("C19RAT"), FUND_MANAGEMENT_FEE("FCORAT"), SURRENDER_FEE("SURPEN"), WITHDRAWAL_FEE("C15PEN"), LIVES(
			"NBVLVS"), DEATH_COVERAGE(
			"DTHCAL"), MAXIMUM_PREMIUM("MAXPRM"), MINIMUM_PREMIUM(
					"MINPRM"), DTHFC2("DTHFC2"), DTHFAC("DTHFAC"), DTHFC3("DTHFC3"), MULTIPLIER("MULTIPLIER"), PFEBAS("PFEBAS"), ADMIN_FEE_PAYMENT_METHOD("C12PRA"), ADMIN_FEE_PERIODICITY("C12IVL");

	private String value;

	ControlDefinitionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
