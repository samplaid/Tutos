package lu.wealins.webia.core.service.impl;

import java.io.File;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.EditingRequestStatus;
import lu.wealins.webia.core.service.DocumentGenerationService;
import lu.wealins.webia.core.service.SynchronousDocumentService;
import lu.wealins.webia.ws.rest.impl.exception.SynchronousEditingException;
import lu.wealins.webia.ws.rest.request.EditingRequest;

@Service
public class SynchronousDocumentServiceImpl implements SynchronousDocumentService {

	@Value("${editiqueMachineAlias:}")
	private String editiqueMachineAlias;

	@Autowired
	private DocumentGenerationService documentGenerationService;

	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousDocumentServiceImpl.class);

	@Override
	public File createDocumentSynchronously(SecurityContext context, EditingRequest editingRequest) {

		EditingRequest generationResponse = createEditingSynchronously(context, editingRequest);

		return getEditingFile(generationResponse);
	}

	@Override
	public EditingRequest createEditingSynchronously(SecurityContext context, EditingRequest editingRequest) {
		Assert.notNull(editingRequest, "The editing request can't be null");

		EditingRequest generationResponse = documentGenerationService.generateDocument(context, editingRequest);

		if (generationResponse.getStatus() != EditingRequestStatus.GENERATED) {
			throw new SynchronousEditingException(
					String.format("An error occured during the generation of the pdf for the editing request %s , final status is %s", generationResponse.getId(), generationResponse.getStatus()));
		}

		LOGGER.info("The report was generated with editing id {} and output stream {}", generationResponse.getId(), generationResponse.getOutputStreamPath());

		return generationResponse;
	}

	@Override
	public File getEditingFile(EditingRequest generationResponse) {
		File file = loadOutputFile(generationResponse);

		if(!file.exists()) {
			throw new SynchronousEditingException(String.format("No file found at %s", file.getAbsolutePath()));
		}

		return file;
	}

	private File loadOutputFile(EditingRequest request) {
		String aliasTruncated = request.getOutputStreamPath().replaceFirst(editiqueMachineAlias, "");
		String path = FilenameUtils.separatorsToSystem(aliasTruncated);
		return new File(path);
	}
}
