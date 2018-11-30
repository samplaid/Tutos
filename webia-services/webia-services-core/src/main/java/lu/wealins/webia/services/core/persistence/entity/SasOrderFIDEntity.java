package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sas order FID entity : Map the table SAS_ORDER_FID
 * 
 * @author xqt5q
 *
 */
@Table(name = "SAS_ORDER_FID")
@Entity
public class SasOrderFIDEntity implements Serializable {

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -8798542751194224509L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SAS_ORDER_FID_ID")
	private Long id;

	@Column(name = "FTR_ID")
	private String transactionId;

	@Column(name = "FUND_CURRENCY")
	private String fundCurrency;

	@Column(name = "QUANTITY")
	private BigDecimal quantity;

	@Column(name = "PRICE")
	private BigDecimal price;
	
	@Column(name = "TRANSACTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date transactionDate;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "EVENT_TYPE")
	private String eventType;
	
	@Column(name = "FUNDS")
	private String funds;
	
	@Column(name = "PORTFOLIO_CD")
	private String portfolioCode;
	
	@Column(name = "MIR_PORTFOLIO_CD")
	private String mirrorPortfolioCode;

	@Column(name = "CREATION_DT")
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@Column(name = "SEND_DT")
	@Temporal(TemporalType.DATE)
	private Date sendDate;

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
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the fundCurrency
	 */
	public String getFundCurrency() {
		return fundCurrency;
	}

	/**
	 * @param fundCurrency the fundCurrency to set
	 */
	public void setFundCurrency(String fundCurrency) {
		this.fundCurrency = fundCurrency;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the funds
	 */
	public String getFunds() {
		return funds;
	}

	/**
	 * @param funds the funds to set
	 */
	public void setFunds(String funds) {
		this.funds = funds;
	}

	/**
	 * @return the portfolioCode
	 */
	public String getPortfolioCode() {
		return portfolioCode;
	}

	/**
	 * @param portfolioCode the portfolioCode to set
	 */
	public void setPortfolioCode(String portfolioCode) {
		this.portfolioCode = portfolioCode;
	}

	/**
	 * @return the mirrorPortfolioCode
	 */
	public String getMirrorPortfolioCode() {
		return mirrorPortfolioCode;
	}

	/**
	 * @param mirrorPortfolioCode the mirrorPortfolioCode to set
	 */
	public void setMirrorPortfolioCode(String mirrorPortfolioCode) {
		this.mirrorPortfolioCode = mirrorPortfolioCode;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	
}
