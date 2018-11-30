package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BillingRunEntity generated by hbm2java
 */
@Entity
@Table(name = "BILLING_RUNS"

)
public class BillingRunEntity implements java.io.Serializable {

	private long birId;
	private Date date0;
	private Date highDate;
	private BigDecimal amount;
	private String currency;
	private BigDecimal payment;
	private Integer method;
	private Integer subMethod;
	private String policy;
	private int status;
	private String createdProcess;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String modifyProcess;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private Long eftRunNo;
	private Set<BillsHistoryEntity> billsHistorys = new HashSet<BillsHistoryEntity>(0);
	private Set<BillEntity> bills = new HashSet<BillEntity>(0);

	@Id
	@Column(name = "BIR_ID", unique = true, nullable = false, precision = 12, scale = 0)
	public long getBirId() {
		return this.birId;
	}

	public void setBirId(long birId) {
		this.birId = birId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE0", length = 23)
	public Date getDate0() {
		return this.date0;
	}

	public void setDate0(Date date0) {
		this.date0 = date0;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HIGH_DATE", length = 23)
	public Date getHighDate() {
		return this.highDate;
	}

	public void setHighDate(Date highDate) {
		this.highDate = highDate;
	}

	@Column(name = "AMOUNT", precision = 15)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "CURRENCY", length = 5)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "PAYMENT", precision = 15)
	public BigDecimal getPayment() {
		return this.payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	@Column(name = "METHOD")
	public Integer getMethod() {
		return this.method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	@Column(name = "SUB_METHOD")
	public Integer getSubMethod() {
		return this.subMethod;
	}

	public void setSubMethod(Integer subMethod) {
		this.subMethod = subMethod;
	}

	@Column(name = "POLICY", length = 14)
	public String getPolicy() {
		return this.policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "CREATED_PROCESS", length = 12)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "CREATED_BY", length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "MODIFY_PROCESS", length = 12)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Column(name = "MODIFY_BY", length = 5)
	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 23)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 23)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "EFT_RUN_NO", precision = 10, scale = 0)
	public Long getEftRunNo() {
		return this.eftRunNo;
	}

	public void setEftRunNo(Long eftRunNo) {
		this.eftRunNo = eftRunNo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "billingRun")
	public Set<BillsHistoryEntity> getBillsHistorys() {
		return this.billsHistorys;
	}

	public void setBillsHistorys(Set<BillsHistoryEntity> billsHistorys) {
		this.billsHistorys = billsHistorys;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "billingRun")
	public Set<BillEntity> getBills() {
		return this.bills;
	}

	public void setBills(Set<BillEntity> bills) {
		this.bills = bills;
	}

}
