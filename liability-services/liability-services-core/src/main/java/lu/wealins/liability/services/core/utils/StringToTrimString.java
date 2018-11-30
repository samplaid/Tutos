package lu.wealins.liability.services.core.utils;

import org.springframework.stereotype.Component;

@Component
public class StringToTrimString {

	public String asTrimString(String str) {
		if (str == null) {
			return null;
		}

		return str.trim();
	}
}
