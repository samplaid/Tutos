package lu.wealins.liability.services.core.business.exceptions;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class ReportExceptionHelper {

	private static final Logger logger = LoggerFactory.getLogger(ReportExceptionHelper.class);

	private ReportExceptionHelper() {
		// helper
	}

	public static <T extends ReportException> void throwErrors(String message, Class<T> classT) {
		throwIfErrorsIsNotEmpty(Arrays.asList(message), classT);
	}

	public static <T extends ReportException> void throwWarns(String message, Class<T> classT) {
		throwIfWarnsIsNotEmpty(Arrays.asList(message), classT);
	}

	public static <T extends ReportException> void throwIfErrorsIsNotEmpty(Collection<String> errors, Class<T> classT) {
		if (CollectionUtils.isNotEmpty(errors)) {
			T reportException = createNewInstance(classT);
			reportException.setErrors(errors);

			throw reportException;
		}
	}

	public static <T extends ReportException> void throwIfWarnsIsNotEmpty(Collection<String> warns, Class<T> classT) {
		if (CollectionUtils.isNotEmpty(warns)) {
			T reportException = createNewInstance(classT);
			reportException.setWarns(warns);

			throw reportException;
		}
	}

	public static <T extends ReportException> void throwWarnsIfEmpty(String value, String message, Class<T> classT) {
		if (StringUtils.isEmpty(value)) {
			T reportException = createNewInstance(classT);
			reportException.setWarns(Arrays.asList(message));

			throw reportException;
		}
	}

	public static <T extends ReportException> void throwErrorsIfEmpty(String value, String message, Class<T> classT) {
		if (StringUtils.isEmpty(value)) {
			T reportException = createNewInstance(classT);
			reportException.setErrors(Arrays.asList(message));

			throw reportException;
		}
	}

	public static <T extends ReportException> void throwWarnsIfTrue(Boolean predicate, String message, Class<T> classT) {
		if (predicate != null && predicate.booleanValue()) {
			T reportException = createNewInstance(classT);
			reportException.setWarns(Arrays.asList(message));
			throw reportException;
		}
	}

	public static <T extends ReportException> void throwWarnsIfFalse(Boolean predicate, String message, Class<T> classT) {
		if (predicate != null) {
			throwWarnsIfTrue(!predicate, message, classT);
		}
	}

	public static <T extends ReportException> void throwWarnsIfNull(Object object, String message, Class<T> classT) {
		if (object == null) {
			T reportException = createNewInstance(classT);
			reportException.setWarns(Arrays.asList(message));
			throw reportException;
		}
	}

	public static <T extends ReportException> void throwErrorsIfTrue(Boolean predicate, String message, Class<T> classT) {
		if (predicate != null && predicate.booleanValue()) {
			T reportException = createNewInstance(classT);
			reportException.setErrors(Arrays.asList(message));
			throw reportException;
		}
	}

	public static <T extends ReportException> void throwErrorsIfFalse(Boolean predicate, String message, Class<T> classT) {
		if (predicate != null) {
			throwErrorsIfTrue(!predicate, message, classT);
		}
	}

	public static <T extends ReportException> void throwErrorsIfNull(Object object, String message, Class<T> classT) {
		if (object == null) {
			T reportException = createNewInstance(classT);
			reportException.setErrors(Arrays.asList(message));
			throw reportException;
		}
	}

	public static <T extends ReportException> void throwIfErrorsOrWarnsAreNotEmpty(Collection<String> errors, Collection<String> warns, Class<T> classT) {
		T reportException = null;
		if (CollectionUtils.isNotEmpty(errors)) {
			reportException = createNewInstance(classT);
			reportException.setErrors(errors);
		}

		if (CollectionUtils.isNotEmpty(warns)) {
			if (reportException == null) {
				reportException = createNewInstance(classT);
			}
			reportException.setWarns(warns);
		}

		if (reportException != null) {
			throw reportException;
		}
	}

	public static <T extends ReportException> void throwIfErrorsIsNotEmptyWithWarns(Collection<String> errors, Collection<String> warns, Class<T> classT) {
		T reportException = null;
		if (CollectionUtils.isNotEmpty(errors)) {
			reportException = createNewInstance(classT);
			reportException.setErrors(errors);
		}

		if (CollectionUtils.isNotEmpty(warns) && reportException != null) {
			reportException.setWarns(warns);			
		}

		if (reportException != null) {
			throw reportException;
		}
	}

	private static <T extends ReportException> T createNewInstance(Class<T> classT) {
		try {
			return classT.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Cannot instanciate " + classT.getName() + ".", e);
		}

		throw new IllegalStateException("Cannot instanciate " + classT.getName() + ".");
	}

}
