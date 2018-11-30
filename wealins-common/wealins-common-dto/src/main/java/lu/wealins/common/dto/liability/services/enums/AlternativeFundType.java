package lu.wealins.common.dto.liability.services.enums;

public enum AlternativeFundType {
	AFNO, AFYES, FFAFY10, FFAFY20, FFAFNO, CSAINO, CSAIYES;

	public static final AlternativeFundType[] SPECIFIC_RISK = new AlternativeFundType[] { AFYES, FFAFY10, FFAFY20, CSAIYES };

	public static final AlternativeFundType[] FID_ALTERNATIVE_FUND = new AlternativeFundType[] { AFYES, CSAIYES };
	
	/**
	 * @return TRUE if the event is a cancelling event
	 */
	public boolean hasSpecificRisk() {
		for (AlternativeFundType e : SPECIFIC_RISK) {
			if (e == this) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return True if the type is an alternative fund for the FID
	 */
	public boolean isFIDAlternativeFund() {
		for (AlternativeFundType e : FID_ALTERNATIVE_FUND) {
			if (e == this) {
				return true;
			}
		}
		return false;
	}
}
