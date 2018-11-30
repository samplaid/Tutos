package lu.wealins.rest.model.alfresco;

import java.util.ArrayList;
import java.util.List;

/**
 * The kids document representation
 * 
 * @author TEY
 *
 */
public class KidsDocument {

	/**
	 * Is a generic kid or option kid
	 */
	private boolean isGeneric;

	/**
	 * The path of the document to save
	 */
	private String path;

	/**
	 * The list of metadata
	 */
	private List<Metadata> metadatas;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the metadatas
	 */
	public List<Metadata> getMetadatas() {
		if (metadatas == null) {
			metadatas = new ArrayList<>();
		}
		return metadatas;
	}

	/**
	 * @param metadatas the metadatas to set
	 */
	public void setMetadatas(List<Metadata> metadatas) {
		this.metadatas = metadatas;
	}

	/**
	 * @return the isGeneric
	 */
	public boolean isGeneric() {
		return isGeneric;
	}

	/**
	 * @param isGeneric the isGeneric to set
	 */
	public void setGeneric(boolean isGeneric) {
		this.isGeneric = isGeneric;
	}

}
