package lu.wealins.webia.ws.rest.impl.exception;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReportExceptionHelper {

	private static final Logger logger = LoggerFactory.getLogger(ReportExceptionHelper.class);

	private ReportExceptionHelper() {
		// helper
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

	public static <T extends ReportException> void throwIfErrorsIsNotEmpty(Collection<String> errors, Object source, Class<T> classT) {
		if (CollectionUtils.isNotEmpty(errors)) {
			T reportException = createNewInstance(classT);
			reportException.setErrors(errors);
			reportException.setSource(source);

			throw reportException;
		}
	}

	public static <T extends ReportException> void throwIfWarnsIsNotEmpty(Collection<String> warns, Object source, Class<T> classT) {
		if (CollectionUtils.isNotEmpty(warns)) {
			T reportException = createNewInstance(classT);
			reportException.setWarns(warns);
			reportException.setSource(source);

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

	private static <T extends ReportException> T createNewInstance(Class<T> classT) {
		try {
			return classT.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Cannot instanciate " + classT.getName() + ".", e);
		}

		throw new IllegalStateException("Cannot instanciate " + classT.getName() + ".");
	}

}
