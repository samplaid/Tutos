package lu.wealins.rest.model;

import lu.wealins.rest.model.alfresco.KidsDocument;

/**
 * The kids injection control response
 * 
 * @see BatchInjectionControlResponse
 * @author TEY
 *
 */
public class KidsInjectionControlResponse extends BatchInjectionControlResponse {

	/**
	 * The KIDS document
	 */
	private KidsDocument kidsDocument;

	/**
	 * @return the kidsDocument
	 */
	public KidsDocument getKidsDocument() {
		return kidsDocument;
	}

	/**
	 * @param kidsDocument the kidsDocument to set
	 */
	public void setKidsDocument(KidsDocument kidsDocument) {
		this.kidsDocument = kidsDocument;
	}
}
