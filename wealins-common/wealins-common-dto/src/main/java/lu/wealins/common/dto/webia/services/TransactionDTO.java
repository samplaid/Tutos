package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {
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
	private String accountNbr;
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
	private String statusCD;
	private String fund;
	private String agent;
	private String fundSubType;
	private String support;
	private String subAgentId;
	private BigDecimal comRate;
	private BigDecimal comBase;
	private BigDecimal baseAmt;
	private String brokerRefContract;
	private String productCd;

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
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountNbr() {
		return accountNbr;
	}

	public void setAccountNbr(String accountNbr) {
		this.accountNbr = accountNbr;
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

	public String getStatusCD() {
		return statusCD;
	}

	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
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

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TransactionDTO [atrId=" + atrId + ", abnId=" + abnId + ", ftrId=" + ftrId + ", pocId=" + pocId + ", pstId=" + pstId + ", trnId=" + trnId + ", transaction0=" + transaction0
				+ ", lineNo=" + lineNo + ", set0=" + set0 + ", accset=" + accset + ", auditReportNo=" + auditReportNo + ", dbcr=" + dbcr + ", policy=" + policy + ", polId=" + polId + ", details="
				+ details + ", effectiveDate=" + effectiveDate + ", eventType=" + eventType + ", matched=" + matched + ", receiptType=" + receiptType + ", eventSubType=" + eventSubType
				+ ", reversalLineNo=" + reversalLineNo + ", statement=" + statement + ", commissionType=" + commissionType + ", account=" + account + ", accountNbr=" + accountNbr + ", centre="
				+ centre + ", amount=" + amount + ", company=" + company + ", branch=" + branch + ", currency=" + currency + ", financialYear=" + financialYear + ", status=" + status + ", createdBy="
				+ createdBy + ", period=" + period + ", createdDate=" + createdDate + ", createdTime=" + createdTime + ", createdProcess=" + createdProcess + ", createdSystemDate=" + createdSystemDate
				+ ", modifyBy=" + modifyBy + ", modifyDate=" + modifyDate + ", modifyTime=" + modifyTime + ", modifyProcess=" + modifyProcess + ", modifySystemDate=" + modifySystemDate
				+ ", previousPaidToDate=" + previousPaidToDate + ", fkAccountsaccId=" + fkAccountsaccId + ", type=" + type + ", changeRate=" + changeRate + ", product=" + product + ", country="
				+ country + ", nlCountry=" + nlCountry + ", statusCD=" + statusCD + ", fund=" + fund + ", agent=" + agent + ", fundSubType=" + fundSubType + ", support=" + support + ", subAgentId="
				+ subAgentId + ", comRate=" + comRate + ", comBase=" + comBase + ", baseAmt=" + baseAmt + ", brokerRefContract=" + brokerRefContract + ", productCd=" + productCd + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abnId == null) ? 0 : abnId.hashCode());
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((accountNbr == null) ? 0 : accountNbr.hashCode());
		result = prime * result + ((accset == null) ? 0 : accset.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + (int) (atrId ^ (atrId >>> 32));
		result = prime * result + ((auditReportNo == null) ? 0 : auditReportNo.hashCode());
		result = prime * result + ((baseAmt == null) ? 0 : baseAmt.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((brokerRefContract == null) ? 0 : brokerRefContract.hashCode());
		result = prime * result + ((centre == null) ? 0 : centre.hashCode());
		result = prime * result + ((changeRate == null) ? 0 : changeRate.hashCode());
		result = prime * result + ((comBase == null) ? 0 : comBase.hashCode());
		result = prime * result + ((comRate == null) ? 0 : comRate.hashCode());
		result = prime * result + ((commissionType == null) ? 0 : commissionType.hashCode());
		result = prime * result + company;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((createdProcess == null) ? 0 : createdProcess.hashCode());
		result = prime * result + ((createdSystemDate == null) ? 0 : createdSystemDate.hashCode());
		result = prime * result + ((createdTime == null) ? 0 : createdTime.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((dbcr == null) ? 0 : dbcr.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((eventSubType == null) ? 0 : eventSubType.hashCode());
		result = prime * result + eventType;
		result = prime * result + financialYear;
		result = prime * result + ((fkAccountsaccId == null) ? 0 : fkAccountsaccId.hashCode());
		result = prime * result + ((ftrId == null) ? 0 : ftrId.hashCode());
		result = prime * result + ((fund == null) ? 0 : fund.hashCode());
		result = prime * result + ((fundSubType == null) ? 0 : fundSubType.hashCode());
		result = prime * result + lineNo;
		result = prime * result + ((matched == null) ? 0 : matched.hashCode());
		result = prime * result + ((modifyBy == null) ? 0 : modifyBy.hashCode());
		result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
		result = prime * result + ((modifyProcess == null) ? 0 : modifyProcess.hashCode());
		result = prime * result + ((modifySystemDate == null) ? 0 : modifySystemDate.hashCode());
		result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
		result = prime * result + ((nlCountry == null) ? 0 : nlCountry.hashCode());
		result = prime * result + period;
		result = prime * result + ((pocId == null) ? 0 : pocId.hashCode());
		result = prime * result + ((polId == null) ? 0 : polId.hashCode());
		result = prime * result + ((policy == null) ? 0 : policy.hashCode());
		result = prime * result + ((previousPaidToDate == null) ? 0 : previousPaidToDate.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((productCd == null) ? 0 : productCd.hashCode());
		result = prime * result + ((pstId == null) ? 0 : pstId.hashCode());
		result = prime * result + ((receiptType == null) ? 0 : receiptType.hashCode());
		result = prime * result + ((reversalLineNo == null) ? 0 : reversalLineNo.hashCode());
		result = prime * result + ((set0 == null) ? 0 : set0.hashCode());
		result = prime * result + ((statement == null) ? 0 : statement.hashCode());
		result = prime * result + status;
		result = prime * result + ((statusCD == null) ? 0 : statusCD.hashCode());
		result = prime * result + ((subAgentId == null) ? 0 : subAgentId.hashCode());
		result = prime * result + ((support == null) ? 0 : support.hashCode());
		result = prime * result + ((transaction0 == null) ? 0 : transaction0.hashCode());
		result = prime * result + ((trnId == null) ? 0 : trnId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionDTO other = (TransactionDTO) obj;
		if (abnId == null) {
			if (other.abnId != null)
				return false;
		} else if (!abnId.equals(other.abnId))
			return false;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (accountNbr == null) {
			if (other.accountNbr != null)
				return false;
		} else if (!accountNbr.equals(other.accountNbr))
			return false;
		if (accset == null) {
			if (other.accset != null)
				return false;
		} else if (!accset.equals(other.accset))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (atrId != other.atrId)
			return false;
		if (auditReportNo == null) {
			if (other.auditReportNo != null)
				return false;
		} else if (!auditReportNo.equals(other.auditReportNo))
			return false;
		if (baseAmt == null) {
			if (other.baseAmt != null)
				return false;
		} else if (!baseAmt.equals(other.baseAmt))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (brokerRefContract == null) {
			if (other.brokerRefContract != null)
				return false;
		} else if (!brokerRefContract.equals(other.brokerRefContract))
			return false;
		if (centre == null) {
			if (other.centre != null)
				return false;
		} else if (!centre.equals(other.centre))
			return false;
		if (changeRate == null) {
			if (other.changeRate != null)
				return false;
		} else if (!changeRate.equals(other.changeRate))
			return false;
		if (comBase == null) {
			if (other.comBase != null)
				return false;
		} else if (!comBase.equals(other.comBase))
			return false;
		if (comRate == null) {
			if (other.comRate != null)
				return false;
		} else if (!comRate.equals(other.comRate))
			return false;
		if (commissionType == null) {
			if (other.commissionType != null)
				return false;
		} else if (!commissionType.equals(other.commissionType))
			return false;
		if (company != other.company)
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (createdProcess == null) {
			if (other.createdProcess != null)
				return false;
		} else if (!createdProcess.equals(other.createdProcess))
			return false;
		if (createdSystemDate == null) {
			if (other.createdSystemDate != null)
				return false;
		} else if (!createdSystemDate.equals(other.createdSystemDate))
			return false;
		if (createdTime == null) {
			if (other.createdTime != null)
				return false;
		} else if (!createdTime.equals(other.createdTime))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (dbcr == null) {
			if (other.dbcr != null)
				return false;
		} else if (!dbcr.equals(other.dbcr))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (eventSubType == null) {
			if (other.eventSubType != null)
				return false;
		} else if (!eventSubType.equals(other.eventSubType))
			return false;
		if (eventType != other.eventType)
			return false;
		if (financialYear != other.financialYear)
			return false;
		if (fkAccountsaccId == null) {
			if (other.fkAccountsaccId != null)
				return false;
		} else if (!fkAccountsaccId.equals(other.fkAccountsaccId))
			return false;
		if (ftrId == null) {
			if (other.ftrId != null)
				return false;
		} else if (!ftrId.equals(other.ftrId))
			return false;
		if (fund == null) {
			if (other.fund != null)
				return false;
		} else if (!fund.equals(other.fund))
			return false;
		if (fundSubType == null) {
			if (other.fundSubType != null)
				return false;
		} else if (!fundSubType.equals(other.fundSubType))
			return false;
		if (lineNo != other.lineNo)
			return false;
		if (matched == null) {
			if (other.matched != null)
				return false;
		} else if (!matched.equals(other.matched))
			return false;
		if (modifyBy == null) {
			if (other.modifyBy != null)
				return false;
		} else if (!modifyBy.equals(other.modifyBy))
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (modifyProcess == null) {
			if (other.modifyProcess != null)
				return false;
		} else if (!modifyProcess.equals(other.modifyProcess))
			return false;
		if (modifySystemDate == null) {
			if (other.modifySystemDate != null)
				return false;
		} else if (!modifySystemDate.equals(other.modifySystemDate))
			return false;
		if (modifyTime == null) {
			if (other.modifyTime != null)
				return false;
		} else if (!modifyTime.equals(other.modifyTime))
			return false;
		if (nlCountry == null) {
			if (other.nlCountry != null)
				return false;
		} else if (!nlCountry.equals(other.nlCountry))
			return false;
		if (period != other.period)
			return false;
		if (pocId == null) {
			if (other.pocId != null)
				return false;
		} else if (!pocId.equals(other.pocId))
			return false;
		if (polId == null) {
			if (other.polId != null)
				return false;
		} else if (!polId.equals(other.polId))
			return false;
		if (policy == null) {
			if (other.policy != null)
				return false;
		} else if (!policy.equals(other.policy))
			return false;
		if (previousPaidToDate == null) {
			if (other.previousPaidToDate != null)
				return false;
		} else if (!previousPaidToDate.equals(other.previousPaidToDate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (productCd == null) {
			if (other.productCd != null)
				return false;
		} else if (!productCd.equals(other.productCd))
			return false;
		if (pstId == null) {
			if (other.pstId != null)
				return false;
		} else if (!pstId.equals(other.pstId))
			return false;
		if (receiptType == null) {
			if (other.receiptType != null)
				return false;
		} else if (!receiptType.equals(other.receiptType))
			return false;
		if (reversalLineNo == null) {
			if (other.reversalLineNo != null)
				return false;
		} else if (!reversalLineNo.equals(other.reversalLineNo))
			return false;
		if (set0 == null) {
			if (other.set0 != null)
				return false;
		} else if (!set0.equals(other.set0))
			return false;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		if (status != other.status)
			return false;
		if (statusCD == null) {
			if (other.statusCD != null)
				return false;
		} else if (!statusCD.equals(other.statusCD))
			return false;
		if (subAgentId == null) {
			if (other.subAgentId != null)
				return false;
		} else if (!subAgentId.equals(other.subAgentId))
			return false;
		if (support == null) {
			if (other.support != null)
				return false;
		} else if (!support.equals(other.support))
			return false;
		if (transaction0 == null) {
			if (other.transaction0 != null)
				return false;
		} else if (!transaction0.equals(other.transaction0))
			return false;
		if (trnId == null) {
			if (other.trnId != null)
				return false;
		} else if (!trnId.equals(other.trnId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	

}
