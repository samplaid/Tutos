package lu.wealins.liability.services.core.utils;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class TinUtils {

	private static final String TIN_CANNOT_BE_NULL = "tin code must not be null";
	private static final String COUNTRY_CANNOT_BE_NULL = "country code must not be null";

	private static final Pattern patternNumber9 = Pattern.compile("[0-9]{9}");
	private static final Pattern patternNumber10 = Pattern.compile("[0-9]{10}");
	private static final Pattern patternNumber11 = Pattern.compile("[0-9]{11}");
	private static final Pattern patternNumber10Or11 = Pattern.compile("[0-9]{10,11}");
	private static final Pattern patternNumber13 = Pattern.compile("[0-9]{13}");
	private static final Pattern patternFrance = Pattern.compile("^[0123][0-9]{12}");
	private static final Pattern patternItaly = Pattern.compile("[a-zA-Z]{6}[0-9]{2}[a-zA-Z]{1}[0-9]{2}[a-zA-Z]{1}[0-9]{3}[a-zA-Z]{1}");
	private static final Pattern patternUK = Pattern.compile("^[a-zA-Z]{2}[0-9]{6}[aAbBcCdD]{1}");

	// Not used yet but found here : https://ipsec.pl/data-protection/2012/european-personal-data-regexp-patterns.html
	private static final Pattern patternFinland = Pattern.compile("^[0-9]{6}[-+A][0-9]{3}[A-Z0-9]");
	private static final Pattern patternNorway = Pattern.compile("^[0-9]{2}[0,1][0-9][0-9]{2}[ ]?[0-9]{5}");
	private static final Pattern patternSweden = Pattern.compile("^[0-9]{2}[0-1][0-9][0-9]{2}[-+][0-9]{4}");
	private static final Pattern patternDenmark = Pattern.compile("^[0-9]{2}[0,1][0-9][0-9]{2}-[0-9]{4}");

	/**
	 * Check if a tin given in parameter is valid for a given country
	 * 
	 * @param tin
	 * @param country
	 * @return
	 * @throws ActorException
	 */
	public static Boolean isTinValid(final String tin, final String country) {
		Assert.notNull(tin, TIN_CANNOT_BE_NULL);
		Assert.notNull(country, COUNTRY_CANNOT_BE_NULL);

		if (country.equals("B") || country.equals("BE") || country.equals("BEL")) {
			return checkForBelgium(tin);
		} else if (country.equals("D") || country.equals("DE") || country.equals("DEU")) {
			return checkForGermany(tin);
		} else if (country.equals("F") || country.equals("FR") || country.equals("FRA")) {
			return checkForFrance(tin);
		} else if (country.equals("L") || country.equals("LU") || country.equals("LUX")) {
			return checkForLuxermbourg(tin);
		} else if (country.equals("I") || country.equals("IT") || country.equals("ITA")) {
			return checkForItaly(tin);
		} else if (country.equals("NL") || country.equals("NEL") || country.equals("NLD")) {
			return checkForNederland(tin);
		} else if (country.equals("GB") || country.equals("GBR")) {
			return checkForUK(tin);
		} else {
			return true;
		}
	}

	private static boolean checkForBelgium(final String tin) {
		return patternNumber11.matcher(tin).matches();
	}

	private static boolean checkForGermany(final String tin) {
		return patternNumber10Or11.matcher(tin).matches();
	}

	private static boolean checkForFrance(final String tin) {
		return patternFrance.matcher(tin).matches();
	}

	private static boolean checkForLuxermbourg(final String tin) {
		return patternNumber13.matcher(tin).matches();
	}

	private static boolean checkForItaly(final String tin) {
		return patternItaly.matcher(tin).matches();
	}

	private static boolean checkForNederland(final String tin) {
		if (patternNumber9.matcher(tin).matches()) {
			return checkModuloForNL(tin);
		}
		return false;
	}

	/**
	 * @param tin
	 */
	private static boolean checkModuloForNL(final String tin) {
		int startMultiplicator = 9;
		Integer n = 0;
		for (int i = 0; i < 8; i++) {
			n = n + Integer.valueOf(tin.substring(i, i + 1)) * startMultiplicator;
			startMultiplicator--;
		}
		Integer resMod = n % 11;
		if (resMod.equals(Integer.valueOf(tin.substring(8, 9)))) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean checkForUK(final String tin) {
		return (patternNumber10.matcher(tin).matches()) || (patternUK.matcher(tin).matches());
	}
}
