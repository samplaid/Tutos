package lu.wealins.common.dto.webia.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document id reservation request
 * 
 * @author SKG
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentIdReservationRequest {

	/**
	 * (Mandatory) The Class of the document
	 */
	private String documentClass;

	/**
	 * (Optional) The id of the agent (for commissions)
	 */
	private String agentId;

	/**
	 * (Optional) The effective date of the document
	 */
	private Date effectiveDate;

	/**
	 * (Optional) The type of the commission type
	 */
	private String commissionType;

	/**
	 * (Optional) The title of the fileNet document
	 */
	private String documentTitle;

	/**
	 * (Optional) The mime type of the fileNet document
	 */
	private String mimeType;

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public String getDocumentClass() {
		return documentClass;
	}

	public void setDocumentClass(String documentClass) {
		this.documentClass = documentClass;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
