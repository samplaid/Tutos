package lu.wealins.batch.injection.lissia;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lu.wealins.batch.injection.AbstractInjectionFilenameValidation;
import lu.wealins.rest.model.LissiaFilesInjectionControlRequest;

/**
 * @author pur
 *
 */
@Component
public class LissiaFilesInjectionFilenameValidation extends AbstractInjectionFilenameValidation<LissiaFilesInjectionControlRequest> {

	private static final String LIABILITY_SERVICES_LISSIA_FILES_INJECTION_CONTROL = "webia/batch/lissiaFilesInjection/check";

	@Value("${lissiaFilesInjectionSuccessPath:}")
	private String lissiaFilesInjectionSuccessPath;

	@Value("${lissiaFilesInjectionFailurePath:}")
	private String lissiaFilesInjectionFailurePath;

	@Override
	public String getInjectionControlUrl() {
		return LIABILITY_SERVICES_LISSIA_FILES_INJECTION_CONTROL;
	}

	@Override
	public String getInjectionFailurePath() {
		return lissiaFilesInjectionFailurePath;
	}

	@Override
	public String getInjectionSuccessPath() {
		return lissiaFilesInjectionSuccessPath;
	}

	/*
	 * 1(non-Javadoc)
	 * 
	 * @see lu.wealins.batch.injection.lissia.AbstractInjectionFilenameValidation#buildInjectionControlRequest(java.lang.File)
	 * 
	 * Control if file is already process
	 */
	@Override
	public LissiaFilesInjectionControlRequest buildInjectionControlRequest(File input) {
		LissiaFilesInjectionControlRequest request = new LissiaFilesInjectionControlRequest();
		request.setFileName(input.getName());
		return request;
	}
}