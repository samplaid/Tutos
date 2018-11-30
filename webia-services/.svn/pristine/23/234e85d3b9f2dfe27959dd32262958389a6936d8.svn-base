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
import javax.persistence.Transient;

/**
 * The persistent class for the SAP_ACCOUNTING database table.
 */
@Entity
@Table(name = "SAP_ACCOUNTING")
public class SapAccountingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 165465874714L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SAPACC_ID")
	private Long idSapAcc;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "PIECE")
	private String piece;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "PIECE_NB")
	private String pieceNb;

	@Column(name = "CHANGE_RATE")
	private BigDecimal changeRate;

	@Column(name = "ACCOUNT")
	private String account;

	@Column(name = "ACCOUNT_GENERAL")
	private String accountGeneral;

	@Column(name = "DEBIT_CREDIT")
	private String debitCredit;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "RECONCILIATION")
	private String reconciliation;

	@Column(name = "EXPLAIN")
	private String explain;

	@Column(name = "PRODUCT")
	private String product;

	@Column(name = "POLICY")
	private String policy;

	@Column(name = "AGENT")
	private String agent;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "COUNTRY_OF_PRODUCT")
	private String countryOfProduct;

	@Column(name = "SUPPORT")
	private String support;

	@Column(name = "FUND")
	private String fund;

	@Column(name = "CREATION_DT")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column(name = "EXPORT_DT")
	@Temporal(TemporalType.DATE)
	private Date exportDate;

	@Column(name = "ACCOUNT_DT")
	@Temporal(TemporalType.DATE)
	private Date accountDate;

	@Column(name = "ORIGIN")
	private String origin;

	@Column(name = "STATUS_CD")
	private String statusCD;

	@Column(name = "ORIGIN_ID")
	private Long originId;

	@Transient
	private String pstId;

	/**
	 * @return the idSapAcc
	 */
	public Long getIdSapAcc() {
		return idSapAcc;
	}

	/**
	 * @param idSapAcc the idSapAcc to set
	 */
	public void setIdSapAcc(Long idSapAcc) {
		this.idSapAcc = idSapAcc;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param compagny the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the piece
	 */
	public String getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(String piece) {
		this.piece = piece;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the pieceNb
	 */
	public String getPieceNb() {
		return pieceNb;
	}

	/**
	 * @param pieceNb the pieceNb to set
	 */
	public void setPieceNb(String pieceNb) {
		this.pieceNb = pieceNb;
	}

	/**
	 * @return the changeRate
	 */
	public BigDecimal getChangeRate() {
		return changeRate;
	}

	/**
	 * @param changeRate the changeRate to set
	 */
	public void setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the accountGeneral
	 */
	public String getAccountGeneral() {
		return accountGeneral;
	}

	/**
	 * @param accountGeneral the accountGeneral to set
	 */
	public void setAccountGeneral(String accountGeneral) {
		this.accountGeneral = accountGeneral;
	}

	/**
	 * @return the Debit Credit
	 */
	public String getDebitCredit() {
		return debitCredit;
	}

	/**
	 * @param sense the Debit Credit to set
	 */
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the reconciliation
	 */
	public String getReconciliation() {
		return reconciliation;
	}

	/**
	 * @param reconciliation the reconciliation to set
	 */
	public void setReconciliation(String reconciliation) {
		this.reconciliation = reconciliation;
	}

	/**
	 * @return the explain
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * @param explain the explain to set
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the country of product
	 */
	public String getCountryOfProduct() {
		return countryOfProduct;
	}

	/**
	 * @param the country of product to set
	 */
	public void setCountryOfProduct(String countryOfProduct) {
		this.countryOfProduct = countryOfProduct;
	}

	/**
	 * @return the support
	 */
	public String getSupport() {
		return support;
	}

	/**
	 * @param support the support to set
	 */
	public void setSupport(String support) {
		this.support = support;
	}

	/**
	 * @return the fund
	 */
	public String getFund() {
		return fund;
	}

	/**
	 * @param fund
	 */
	public void setFund(String fund) {
		this.fund = fund;
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
	 * @return the exportDate
	 */
	public Date getExportDate() {
		return exportDate;
	}

	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * @return the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getStatusCD() {
		return statusCD;
	}

	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
	}

	public String getPstId() {
		return pstId;
	}

	public void setPstId(String pstId) {
		this.pstId = pstId;
	}
	/**
	 * @return the originId
	 */
	public Long getOriginId() {
		return originId;
	}

	/**
	 * @param originId the originId to set
	 */
	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SapAccountingEntity [idSapAcc=" + idSapAcc + ", company=" + company + ", piece=" + piece + ", currency=" + currency + ", pieceNb=" + pieceNb + ", changeRate=" + changeRate
				+ ", account=" + account + ", accountGeneral=" + accountGeneral + ", debitCredit=" + debitCredit + ", amount=" + amount + ", reconciliation=" + reconciliation + ", explain=" + explain
				+ ", product=" + product + ", policy=" + policy + ", agent=" + agent + ", country=" + country + ", countryOfProduct=" + countryOfProduct + ", support=" + support + ", fund=" + fund
				+ ", creationDate=" + creationDate + ", exportDate=" + exportDate + ", accountDate=" + accountDate + ", origin=" + origin + ", statusCD=" + statusCD + ", originId=" + originId
				+ ", pstId=" + pstId + "]";
	}

}
