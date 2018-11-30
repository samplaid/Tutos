package lu.wealins.common.dto.liability.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountTransactionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8848503004385110652L;
	private long atrId;
	private Long abnId;
	private Long ftrId;
	private String pocId;
	private Long pstId;
	private Long trnId;
	private Long transaction0;
	private int lineNo;
	private Integer set0;
	private String accset;
	private Integer auditReportNo;
	private Character dbcr;
	private String policy;
	private String polId;
	private String details;
	private Date effectiveDate;
	private int eventType;
	private Long matched;
	private Integer receiptType;
	private Integer eventSubType;
	private Integer reversalLineNo;
	private Integer statement;
	private Integer commissionType;
	private String account;
	private String centre;
	private BigDecimal amount;
	private int company;
	private String branch;
	private String currency;
	private int financialYear;
	private int status;
	private String createdBy;
	private int period;
	private Date createdDate;
	private Date createdTime;
	private String createdProcess;
	private Date createdSystemDate;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String modifyProcess;
	private Date modifySystemDate;
	private Date previousPaidToDate;
	private String fkAccountsaccId;
	private Integer type;
	private BigDecimal changeRate;
	private String product;
	private String country;
	private String nlCountry;
	private String fund;
	private String agent;
	private String fundSubType;
	private String subAgentId;
	private BigDecimal comRate;
	private BigDecimal comBase;
	private BigDecimal baseAmt;
	private String brokerRefContract;
	private String productCd;
	private int coverage;

	public long getAtrId() {
		return atrId;
	}

	public void setAtrId(long atrId) {
		this.atrId = atrId;
	}

	public Long getAbnId() {
		return abnId;
	}

	public void setAbnId(Long abnId) {
		this.abnId = abnId;
	}

	public Long getFtrId() {
		return ftrId;
	}

	public void setFtrId(Long ftrId) {
		this.ftrId = ftrId;
	}

	public String getPocId() {
		return pocId;
	}

	public void setPocId(String pocId) {
		this.pocId = pocId;
	}

	public Long getPstId() {
		return pstId;
	}

	public void setPstId(Long pstId) {
		this.pstId = pstId;
	}

	public Long getTrnId() {
		return trnId;
	}

	public void setTrnId(Long trnId) {
		this.trnId = trnId;
	}

	public Long getTransaction0() {
		return transaction0;
	}

	public void setTransaction0(Long transaction0) {
		this.transaction0 = transaction0;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public Integer getSet0() {
		return set0;
	}

	public void setSet0(Integer set0) {
		this.set0 = set0;
	}

	public String getAccset() {
		return accset;
	}

	public void setAccset(String accset) {
		this.accset = accset;
	}

	public Integer getAuditReportNo() {
		return auditReportNo;
	}

	public void setAuditReportNo(Integer auditReportNo) {
		this.auditReportNo = auditReportNo;
	}

	public Character getDbcr() {
		return dbcr;
	}

	public void setDbcr(Character dbcr) {
		this.dbcr = dbcr;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Long getMatched() {
		return matched;
	}

	public void setMatched(Long matched) {
		this.matched = matched;
	}

	public Integer getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(Integer receiptType) {
		this.receiptType = receiptType;
	}

	public Integer getEventSubType() {
		return eventSubType;
	}

	public void setEventSubType(Integer eventSubType) {
		this.eventSubType = eventSubType;
	}

	public Integer getReversalLineNo() {
		return reversalLineNo;
	}

	public void setReversalLineNo(Integer reversalLineNo) {
		this.reversalLineNo = reversalLineNo;
	}

	public Integer getStatement() {
		return statement;
	}

	public void setStatement(Integer statement) {
		this.statement = statement;
	}

	public Integer getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(Integer commissionType) {
		this.commissionType = commissionType;
	}

	public String getAccount() {
		if (account != null) {
			return account.trim();
		} else {
			return account;
		}
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCentre() {
		return centre;
	}

	public void setCentre(String centre) {
		this.centre = centre;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(int financialYear) {
		this.financialYear = financialYear;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public Date getCreatedSystemDate() {
		return createdSystemDate;
	}

	public void setCreatedSystemDate(Date createdSystemDate) {
		this.createdSystemDate = createdSystemDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	public Date getModifySystemDate() {
		return modifySystemDate;
	}

	public void setModifySystemDate(Date modifySystemDate) {
		this.modifySystemDate = modifySystemDate;
	}

	public Date getPreviousPaidToDate() {
		return previousPaidToDate;
	}

	public void setPreviousPaidToDate(Date previousPaidToDate) {
		this.previousPaidToDate = previousPaidToDate;
	}

	public String getFkAccountsaccId() {
		return fkAccountsaccId;
	}

	public void setFkAccountsaccId(String fkAccountsaccId) {
		this.fkAccountsaccId = fkAccountsaccId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(BigDecimal changeRate) {
		this.changeRate = changeRate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNlCountry() {
		return nlCountry;
	}

	public void setNlCountry(String nlCountry) {
		this.nlCountry = nlCountry;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getFundSubType() {
		return fundSubType;
	}

	public void setFundSubType(String fundSubType) {
		this.fundSubType = fundSubType;
	}

	public String getSubAgentId() {
		return subAgentId;
	}

	public void setSubAgentId(String subAgentId) {
		this.subAgentId = subAgentId;
	}

	public BigDecimal getComRate() {
		return comRate;
	}

	public void setComRate(BigDecimal comRate) {
		this.comRate = comRate;
	}

	public BigDecimal getComBase() {
		return comBase;
	}

	public void setComBase(BigDecimal comBase) {
		this.comBase = comBase;
	}

	public BigDecimal getBaseAmt() {
		return baseAmt;
	}

	public void setBaseAmt(BigDecimal baseAmt) {
		this.baseAmt = baseAmt;
	}

	public String getBrokerRefContract() {
		return brokerRefContract;
	}

	public void setBrokerRefContract(String brokerRefContract) {
		this.brokerRefContract = brokerRefContract;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

}
