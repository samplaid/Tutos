/**
 * 
 */
package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A class that contains the path for commission batch upload.
 * 
 * @author oro
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchUploadRequest {

	private String filenamePattern;
	private String dirPattern;

	private String sourcePath;
	private String targetPath;

	private String contentType;
	private String documentClassId;

	/**
	 * @return the documentClassId
	 */
	public String getDocumentClassId() {
		return documentClassId;
	}

	/**
	 * @param documentClassId the documentClassId to set
	 */
	public void setDocumentClassId(String documentClassId) {
		this.documentClassId = documentClassId;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the dirPattern
	 */
	public String getDirPattern() {
		return dirPattern;
	}

	/**
	 * @param dirPattern the dirPattern to set
	 */
	public void setDirPattern(String dirPattern) {
		this.dirPattern = dirPattern;
	}

	/**
	 * @return the filenamePattern
	 */
	public String getFilenamePattern() {
		return filenamePattern;
	}

	/**
	 * @param filenamePattern the filenamePattern to set
	 */
	public void setFilenamePattern(String filenamePattern) {
		this.filenamePattern = filenamePattern;
	}


	/**
	 * @return the sourcePath
	 */
	public String getSourcePath() {
		return sourcePath;
	}
	
	/**
	 * @param sourcePath the sourcePath to set
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	
	/**
	 * @return the targetPath
	 */
	public String getTargetPath() {
		return targetPath;
	}
	
	/**
	 * @param targetPath the targetPath to set
	 */
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BatchUploadRequest [sourcePath=");
		builder.append(sourcePath);
		builder.append(", targetPath=");
		builder.append(targetPath);
		builder.append("]");
		return builder.toString();
	}

}
