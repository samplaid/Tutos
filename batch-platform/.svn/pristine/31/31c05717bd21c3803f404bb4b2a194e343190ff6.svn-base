package lu.wealins.rest.model.alfresco;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The request for saving kids document
 * 
 * @author TEY
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveKidsDocumentRequest {

	/**
	 * The list of kids document to save
	 */
	private List<KidsDocument> kidsDocuments;

	/**
	 * @return the kidsDocuments
	 */
	public List<KidsDocument> getKidsDocuments() {
		if (kidsDocuments == null) {
			kidsDocuments = new ArrayList<>();
		}
		return kidsDocuments;
	}

	/**
	 * @param kidsDocuments the kidsDocuments to set
	 */
	public void setKidsDocuments(List<KidsDocument> kidsDocuments) {
		this.kidsDocuments = kidsDocuments;
	}
}
