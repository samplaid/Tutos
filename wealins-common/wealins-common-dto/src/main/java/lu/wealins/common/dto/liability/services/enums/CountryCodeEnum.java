/**
 * 
 */
package lu.wealins.common.dto.liability.services.enums;

public enum CountryCodeEnum {
	FRENCH("FRA"), PORTUGAL("PRT"), BELGIUM("BEL"), SWEDEN("SWE"), FINLAND("FIN"), NORWAY("NOR"), LUXEMBOURG("LUX");

	private final String code;

	private CountryCodeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static CountryCodeEnum toEnum(String code) {
		CountryCodeEnum result = null;

		if (code != null && code != "") {
			CountryCodeEnum[] countryCodes = values();
			for (int i = 0; i < countryCodes.length; i++) {
				if (countryCodes[i].code.equals(code)) {
					result = countryCodes[i];
					break;
				}
			}
		}

		return result;
	}

}
