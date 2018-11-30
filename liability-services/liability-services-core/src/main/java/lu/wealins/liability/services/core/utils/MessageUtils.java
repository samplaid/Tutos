package lu.wealins.liability.services.core.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @author xqv66
 *
 */
@Component
public class MessageUtils {

	private static final Locale DEFAULT_LOCALE = Locale.FRANCE;

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String msg, String... args) {
		return messageSource.getMessage(msg, args, DEFAULT_LOCALE);
	}

}
