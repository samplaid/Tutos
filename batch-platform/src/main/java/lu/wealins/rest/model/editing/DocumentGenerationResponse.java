package lu.wealins.rest.model.editing;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document generation response
 * 
 * @author xqt5q
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentGenerationResponse {

	File documentFile;

	public File getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(File documentFile) {
		this.documentFile = documentFile;
	}

}
