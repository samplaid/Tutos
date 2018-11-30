package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionTaxSendingDTO {
	
	private static final long serialVersionUID = 3727647942475650686L;

	private Long id;
	
	private Long transactionTaxId;
	

	private String sendingDest;


	private String sendingTitle;


	private String sendingFirstName;


	private String sendingLastName;


	private String sendingAddressLine1;
	

	private String sendingAddressLine2;
	

	private String sendingAddressLine3;
	

	private String sendingAddressLine4;


	private String sendingPostCode;


	private String sendingTown;


	private String sendingCountry;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the transactionTaxId
	 */
	public Long getTransactionTaxId() {
		return transactionTaxId;
	}


	/**
	 * @param transactionTaxId
	 *            the transactionTaxId to set
	 */
	public void setTransactionTaxId(Long transactionTaxId) {
		this.transactionTaxId = transactionTaxId;
	}


	/**
	 * @return the sendingDest
	 */
	public String getSendingDest() {
		return sendingDest;
	}


	/**
	 * @param sendingDest the sendingDest to set
	 */
	public void setSendingDest(String sendingDest) {
		this.sendingDest = sendingDest;
	}


	/**
	 * @return the sendingTitle
	 */
	public String getSendingTitle() {
		return sendingTitle;
	}


	/**
	 * @param sendingTitle the sendingTitle to set
	 */
	public void setSendingTitle(String sendingTitle) {
		this.sendingTitle = sendingTitle;
	}


	/**
	 * @return the sendingFirstName
	 */
	public String getSendingFirstName() {
		return sendingFirstName;
	}


	/**
	 * @param sendingFirstName the sendingFirstName to set
	 */
	public void setSendingFirstName(String sendingFirstName) {
		this.sendingFirstName = sendingFirstName;
	}


	/**
	 * @return the sendingLastName
	 */
	public String getSendingLastName() {
		return sendingLastName;
	}


	/**
	 * @param sendingLastName the sendingLastName to set
	 */
	public void setSendingLastName(String sendingLastName) {
		this.sendingLastName = sendingLastName;
	}


	/**
	 * @return the sendingAddressLine1
	 */
	public String getSendingAddressLine1() {
		return sendingAddressLine1;
	}


	/**
	 * @param sendingAddressLine1 the sendingAddressLine1 to set
	 */
	public void setSendingAddressLine1(String sendingAddressLine1) {
		this.sendingAddressLine1 = sendingAddressLine1;
	}


	/**
	 * @return the sendingAddressLine2
	 */
	public String getSendingAddressLine2() {
		return sendingAddressLine2;
	}


	/**
	 * @param sendingAddressLine2 the sendingAddressLine2 to set
	 */
	public void setSendingAddressLine2(String sendingAddressLine2) {
		this.sendingAddressLine2 = sendingAddressLine2;
	}


	/**
	 * @return the sendingAddressLine3
	 */
	public String getSendingAddressLine3() {
		return sendingAddressLine3;
	}


	/**
	 * @param sendingAddressLine3 the sendingAddressLine3 to set
	 */
	public void setSendingAddressLine3(String sendingAddressLine3) {
		this.sendingAddressLine3 = sendingAddressLine3;
	}


	/**
	 * @return the sendingAddressLine4
	 */
	public String getSendingAddressLine4() {
		return sendingAddressLine4;
	}


	/**
	 * @param sendingAddressLine4 the sendingAddressLine4 to set
	 */
	public void setSendingAddressLine4(String sendingAddressLine4) {
		this.sendingAddressLine4 = sendingAddressLine4;
	}


	/**
	 * @return the sendingPostCode
	 */
	public String getSendingPostCode() {
		return sendingPostCode;
	}


	/**
	 * @param sendingPostCode the sendingPostCode to set
	 */
	public void setSendingPostCode(String sendingPostCode) {
		this.sendingPostCode = sendingPostCode;
	}


	/**
	 * @return the sendingTown
	 */
	public String getSendingTown() {
		return sendingTown;
	}


	/**
	 * @param sendingTown the sendingTown to set
	 */
	public void setSendingTown(String sendingTown) {
		this.sendingTown = sendingTown;
	}


	/**
	 * @return the sendingCountry
	 */
	public String getSendingCountry() {
		return sendingCountry;
	}


	/**
	 * @param sendingCountry the sendingCountry to set
	 */
	public void setSendingCountry(String sendingCountry) {
		this.sendingCountry = sendingCountry;
	}


}
