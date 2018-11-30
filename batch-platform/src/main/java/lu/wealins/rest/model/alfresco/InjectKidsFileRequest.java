/**
 * 
 */
package lu.wealins.rest.model.alfresco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author XQT5Q
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InjectKidsFileRequest {
	
	private String path;
	
	private String fileName;
	
	private String metadatas;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(String metadatas) {
		this.metadatas = metadatas;
	}
	
	

}
