package lu.wealins.webia.services.core.utils;

public class Helpers {
	public static String removeNoAlphaNumCharacter(String string) {
		if (string == null)
			return null;
		return string.replaceAll("[^A-Za-z0-9 ]", "");
	}

	public static String truncate(String string, int maxLength) {
		if (string == null)
			return null;
		return string.length() > maxLength ? string.substring(0, maxLength) : string;
	}
}
