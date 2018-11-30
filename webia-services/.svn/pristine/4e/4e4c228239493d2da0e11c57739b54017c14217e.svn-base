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
import javax.persistence.Transient;

@Entity
@Table(name = "COMMISSION_TO_PAY")
public class CommissionToPayEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COM_ID")
	private Long comId;
	@Column(name = "AGENT_ID")
	private String agentId;
	@Column(name = "ORIGIN")
	private String origin;
	@Column(name = "ORIGIN_ID")
	private Long originId;
	@Column(name = "COM_TYPE")
	private String comType;
	@Column(name = "ACCOUNTING_MONTH")
	private String accountingMonth;
	@Column(name = "COM_DATE")
	private Date comDate;
	@Column(name = "COM_AMOUNT")
	private BigDecimal comAmount;
	@Column(name = "COM_CURRENCY")
	private String comCurrency;
	@Column(name = "COM_RATE")
	private BigDecimal comRate;
	@Column(name = "COM_BASE")
	private BigDecimal comBase;
	@Column(name = "PRODUCT_CD")
	private String productCd;
	@Column(name = "POLICY_ID")
	private String policyId;
	@Column(name = "FUND_NAME")
	private String fundName;
	@Column(name = "COM_PRINT_DATE")
	private Date comPrintDate;
	@Column(name = "SUB_AGENT_ID")
	private String subAgentId;
	@Column(name = "TRANSFER_ID")
	private Long transferId;
	@Column(name = "STATEMENT_ID")
	private Long statementId;
	@Column(name = "CODE_ISIN")
	private String codeIsin;
	@Column(name = "TRANSACTION0")
	private Long transaction0;
	@Column(name = "STATUS")
	private String status;
	
	@Transient
	private Long pstId;
	@Transient
	private Boolean reverse;

	/**
	 * @return the comId
	 */
	public Long getComId() {
		return comId;
	}
	/**
	 * @param comId the comId to set
	 */
	public void setComId(Long comId) {
		this.comId = comId;
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
	/**
	 * @return the comType
	 */
	public String getComType() {
		return comType;
	}
	/**
	 * @param comType the comType to set
	 */
	public void setComType(String comType) {
		this.comType = comType;
	}
	/**
	 * @return the accountingMonth
	 */
	public String getAccountingMonth() {
		return accountingMonth;
	}
	/**
	 * @param accountingMonth the accountingMonth to set
	 */
	public void setAccountingMonth(String accountingMonth) {
		this.accountingMonth = accountingMonth;
	}
	/**
	 * @return the comDate
	 */
	public Date getComDate() {
		return comDate;
	}
	/**
	 * @param comDate the comDate to set
	 */
	public void setComDate(Date comDate) {
		this.comDate = comDate;
	}
	/**
	 * @return the comAmount
	 */
	public BigDecimal getComAmount() {
		return comAmount;
	}
	/**
	 * @param comAmount the comAmount to set
	 */
	public void setComAmount(BigDecimal comAmount) {
		this.comAmount = comAmount;
	}
	/**
	 * @return the comCurrency
	 */
	public String getComCurrency() {
		return comCurrency;
	}
	/**
	 * @param comCurrency the comCurrency to set
	 */
	public void setComCurrency(String comCurrency) {
		this.comCurrency = comCurrency;
	}
	/**
	 * @return the comRate
	 */
	public BigDecimal getComRate() {
		return comRate;
	}
	/**
	 * @param comRate the comRate to set
	 */
	public void setComRate(BigDecimal comRate) {
		this.comRate = comRate;
	}
	/**
	 * @return the comBase
	 */
	public BigDecimal getComBase() {
		return comBase;
	}
	/**
	 * @param comBase the comBase to set
	 */
	public void setComBase(BigDecimal comBase) {
		this.comBase = comBase;
	}
	/**
	 * @return the productCd
	 */
	public String getProductCd() {
		return productCd;
	}
	/**
	 * @param productCd the productCd to set
	 */
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}
	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	/**
	 * @return the fundName
	 */
	public String getFundName() {
		return fundName;
	}
	/**
	 * @param fundName the fundName to set
	 */
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	/**
	 * @return the comPrintDate
	 */
	public Date getComPrintDate() {
		return comPrintDate;
	}
	/**
	 * @param comPrintDate the comPrintDate to set
	 */
	public void setComPrintDate(Date comPrintDate) {
		this.comPrintDate = comPrintDate;
	}
	/**
	 * @return the subAgentId
	 */
	public String getSubAgentId() {
		return subAgentId;
	}
	/**
	 * @param subAgentId the subAgentId to set
	 */
	public void setSubAgentId(String subAgentId) {
		this.subAgentId = subAgentId;
	}
	/**
	 * @return the transferId
	 */
	public Long getTransferId() {
		return transferId;
	}
	/**
	 * @param transferId the transferId to set
	 */
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @return the pstId
	 */
	public Long getPstId() {
		return pstId;
	}
	/**
	 * @param pstId the pstId to set
	 */
	public void setPstId(Long pstId) {
		this.pstId = pstId;
	}	
	
	/**
	 * @return the reverse
	 */
	public Boolean getReverse() {
		return reverse;
	}
	/**
	 * @param reverse the reverse to set
	 */
	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}	
	
	/**
	 * @return the statementId
	 */
	public Long getStatementId() {
		return statementId;
	}

	/**
	 * @param statementId the statementId to set
	 */
	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}
	
	/**
	 * @return the codeIsin
	 */
	public String getCodeIsin() {
		return codeIsin;
	}
	
	/**
	 * @param codeIsin the codeIsin to set
	 */
	public void setCodeIsin(String codeIsin) {
		this.codeIsin = codeIsin;
	}
	
	/**
	 * @return the transaction0
	 */
	public Long getTransaction0() {
		return transaction0;
	}
	
	/**
	 * @param transaction0 the transaction0 to set
	 */
	public void setTransaction0(Long transaction0) {
		this.transaction0 = transaction0;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
