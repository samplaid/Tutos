package lu.wealins.common.dto.webia.services.enums;

public enum NLProductCountryEnum {
	BEL, DEU, DNK, ESP, FIN, FRA, GBR, ITA, LUX, NOR, PRT, SWE, XXX;

	/**
	 * Convert a country code that is composed by 3 letters to an enumeration.
	 * 
	 * @param nlCountry
	 * @return The enumeration which is related to the code
	 * @throws RuntimeException if the parameter is null or empty or no item is found that matches the given country code.
	 */
	public static NLProductCountryEnum toEnum(String nlCountry) {
		return searhEnumOrThrowIfNull(nlCountry, true);
	}

	/**
	 * Convert a country code that is composed by 3 letters to an enumeration. This method convert the code to the enumeration by ignore the sensitive case.
	 * 
	 * @param nlCountry
	 * @return The enumeration which is related to the code
	 * @throws RuntimeException if the parameter is null or empty or no item is found that matches the given country code.
	 */
	public static NLProductCountryEnum toEnumIgnoreCase(String nlCountry) {
		return searhEnumOrThrowIfNull(nlCountry, false);
	}

	public static boolean hasEnum(String nlCountry) {
		return searhEnum(nlCountry, false) != null;
	}

	/**
	 * Search an enumeration that has a name equal to the country code in parameter.
	 * 
	 * @param nlCountry a code
	 * @param sensitiveCase true if sensitive case should be used for the search.
	 * @return Null if no enumeration is found.
	 */
	private static NLProductCountryEnum searhEnum(String nlCountry, boolean sensitiveCase) {
		NLProductCountryEnum equivEnum = null;

		if (nlCountry != null && nlCountry != "") {
			for (int pos = 0; pos < values().length; pos++) {
				NLProductCountryEnum eachEnum = values()[pos];

				if (sensitiveCase) {
					equivEnum = (eachEnum.name().equals(nlCountry)) ? eachEnum : null;
				} else {
					equivEnum = (eachEnum.name().equalsIgnoreCase(nlCountry)) ? eachEnum : null;
				}
			}
		}

		return equivEnum;
	}

	/**
	 * Returns an enumeration that has a name equal to the country code in parameter. Otherwise, throws an error.
	 * 
	 * @param nlCountry the code
	 * @param sensitiveCase true if sensitive case should be used for the search.
	 * @return an enumeration
	 * @throws RuntimeException if the parameter is null or empty or no item is found that matches the given country code.
	 */
	private static NLProductCountryEnum searhEnumOrThrowIfNull(String nlCountry, boolean sensitiveCase) {
		NLProductCountryEnum equivEnum = searhEnum(nlCountry, sensitiveCase);
		if (equivEnum == null) {
			throw new RuntimeException("The country NL should not be null or empty");
		}
		return equivEnum;
	}
}
