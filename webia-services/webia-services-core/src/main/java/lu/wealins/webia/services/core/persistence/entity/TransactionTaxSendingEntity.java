package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_TAX_SENDING")
public class TransactionTaxSendingEntity implements Serializable {
	
	private static final long serialVersionUID = 3727647942475650686L;

	@Id
	@Column(name = "TRN_TAX_SENDING_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "TRN_TAX_ID", nullable = false, updatable = false)
	private TransactionTaxEntity transactionTax;
	
	@Column(name = "SND_DEST")
	private String sendingDest;

	@Column(name = "SND_TITLE")
	private String sendingTitle;

	@Column(name = "SND_FIRST_NAME")
	private String sendingFirstName;

	@Column(name = "SND_LAST_NAME")
	private String sendingLastName;

	@Column(name = "SND_ADDR_LINE_1")
	private String sendingAddressLine1;
	
	@Column(name = "SND_ADDR_LINE_2")
	private String sendingAddressLine2;
	
	@Column(name = "SND_ADDR_LINE_3")
	private String sendingAddressLine3;
	
	@Column(name = "SND_ADDR_LINE_4")
	private String sendingAddressLine4;

	@Column(name = "SND_POSTCODE")
	private String sendingPostCode;

	@Column(name = "SND_TOWN")
	private String sendingTown;

	@Column(name = "SND_COUNTRY")
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
	 * @return the transactionTax
	 */
	public TransactionTaxEntity getTransactionTax() {
		return transactionTax;
	}

	/**
	 * @param transactionTax the transactionTax to set
	 */
	public void setTransactionTax(TransactionTaxEntity transactionTax) {
		this.transactionTax = transactionTax;
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
