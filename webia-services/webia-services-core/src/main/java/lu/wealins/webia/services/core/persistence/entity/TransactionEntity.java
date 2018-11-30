package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * For dev test, not a table in database.
 */
@Entity
@Table(name = "ACCOUNT_TRANSACTIONS")
public class TransactionEntity {
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

	@Id
	@Column(name = "ATRID")
	public long getAtrId() {
		return atrId;
	}

	public void setAtrId(long atrId) {
		this.atrId = atrId;
	}

	@Column
	public Long getAbnId() {
		return abnId;
	}

	public void setAbnId(Long abnId) {
		this.abnId = abnId;
	}

	@Column
	public Long getFtrId() {
		return ftrId;
	}

	public void setFtrId(Long ftrId) {
		this.ftrId = ftrId;
	}

	@Column
	public String getPocId() {
		return pocId;
	}

	public void setPocId(String pocId) {
		this.pocId = pocId;
	}

	@Column
	public Long getPstId() {
		return pstId;
	}

	@Column
	public void setPstId(Long pstId) {
		this.pstId = pstId;
	}

	@Column
	public Long getTrnId() {
		return trnId;
	}

	public void setTrnId(Long trnId) {
		this.trnId = trnId;
	}

	@Column
	public Long getTransaction0() {
		return transaction0;
	}

	public void setTransaction0(Long transaction0) {
		this.transaction0 = transaction0;
	}

	@Column
	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	@Column
	public Integer getSet0() {
		return set0;
	}

	public void setSet0(Integer set0) {
		this.set0 = set0;
	}

	@Column
	public String getAccset() {
		return accset;
	}

	public void setAccset(String accset) {
		this.accset = accset;
	}

	@Column
	public Integer getAuditReportNo() {
		return auditReportNo;
	}

	public void setAuditReportNo(Integer auditReportNo) {
		this.auditReportNo = auditReportNo;
	}

	@Column
	public Character getDbcr() {
		return dbcr;
	}

	public void setDbcr(Character dbcr) {
		this.dbcr = dbcr;
	}

	@Column
	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	@Column
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Column
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column
	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	@Column
	public Long getMatched() {
		return matched;
	}

	public void setMatched(Long matched) {
		this.matched = matched;
	}

	@Column
	public Integer getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(Integer receiptType) {
		this.receiptType = receiptType;
	}

	@Column
	public Integer getEventSubType() {
		return eventSubType;
	}

	public void setEventSubType(Integer eventSubType) {
		this.eventSubType = eventSubType;
	}

	@Column
	public Integer getReversalLineNo() {
		return reversalLineNo;
	}

	public void setReversalLineNo(Integer reversalLineNo) {
		this.reversalLineNo = reversalLineNo;
	}

	@Column
	public Integer getStatement() {
		return statement;
	}

	public void setStatement(Integer statement) {
		this.statement = statement;
	}

	@Column
	public Integer getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(Integer commissionType) {
		this.commissionType = commissionType;
	}

	@Column
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column
	public String getCentre() {
		return centre;
	}

	public void setCentre(String centre) {
		this.centre = centre;
	}

	@Column
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column
	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	@Column
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public int getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(int financialYear) {
		this.financialYear = financialYear;
	}

	@Column
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	@Column
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column
	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column
	public Date getCreatedSystemDate() {
		return createdSystemDate;
	}

	public void setCreatedSystemDate(Date createdSystemDate) {
		this.createdSystemDate = createdSystemDate;
	}

	@Column
	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Column
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column
	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Column
	public Date getModifySystemDate() {
		return modifySystemDate;
	}

	public void setModifySystemDate(Date modifySystemDate) {
		this.modifySystemDate = modifySystemDate;
	}

	@Column
	public Date getPreviousPaidToDate() {
		return previousPaidToDate;
	}

	public void setPreviousPaidToDate(Date previousPaidToDate) {
		this.previousPaidToDate = previousPaidToDate;
	}

	@Column
	public String getFkAccountsaccId() {
		return fkAccountsaccId;
	}

	public void setFkAccountsaccId(String fkAccountsaccId) {
		this.fkAccountsaccId = fkAccountsaccId;
	}

	@Column
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
