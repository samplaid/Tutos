package lu.wealins.webia.ws.rest.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The document id reservation request
 * 
 * @author SKG
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveDocumentIntoFileNetRequest {

	private String documentPath;
	private String documentClass;
	private String gedId;
	private String contentType;
	private String documentTitle;
	private String documentStatus;
	private Date effectiveDate;

	/**
	 * (Optional) The id of the agent (for commissions)
	 */
	private String agentId;

	/**
	 * (Optional) The type of the commission type
	 */
	private String commissionType;

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the documentStatus
	 */
	public String getDocumentStatus() {
		return documentStatus;
	}

	/**
	 * @param documentStatus the documentStatus to set
	 */
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	/**
	 * @return the documentTitle
	 */
	public String getDocumentTitle() {
		return documentTitle;
	}

	/**
	 * @param documentTitle the documentTitle to set
	 */
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	/**
	 * @return the documentPath
	 */
	public String getDocumentPath() {
		return documentPath;
	}

	/**
	 * @param documentPath the documentPath to set
	 */
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	/**
	 * @return the documentClass
	 */
	public String getDocumentClass() {
		return documentClass;
	}

	/**
	 * REgisters a new document class name for this instance.
	 * 
	 * @param documentClass the documentClass to set
	 */
	public void setDocumentClass(String documentClass) {
		this.documentClass = documentClass;
	}

	/**
	 * @return the gedId
	 */
	public String getGedId() {
		return gedId;
	}

	/**
	 * @param gedId the gedId to set
	 */
	public void setGedId(String gedId) {
		this.gedId = gedId;
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
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the commissionType
	 */
	public String getCommissionType() {
		return commissionType;
	}

	/**
	 * @param commissionType the commissionType to set
	 */
	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "SaveDocumentIntoFileNetRequest [documentPath=" + documentPath + ", documentClass=" + documentClass + ", gedId=" + gedId + ", contentType=" + contentType + ", documentTitle="
				+ documentTitle + ", documentStatus=" + documentStatus + ", effectiveDate=" + effectiveDate + "]";
	}

}
