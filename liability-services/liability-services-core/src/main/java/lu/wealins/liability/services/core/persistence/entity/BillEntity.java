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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * BillEntity generated by hbm2java
 */
@Entity
@Table(name = "BILLS"

)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "createdProcess", "modifyProcess", "createdBy", "createdDate", "createdTime", "modifyDate", "modifyTime", "modifyBy" })
public class BillEntity implements java.io.Serializable {

	private long bilNo;
	private BillingRunEntity billingRun;
	private PolicyEntity policy;
	private String policyId;
	private String collectionReference;
	private Date date0;
	private BigDecimal amount;
	private String currency;
	private Integer method;
	private Integer subMethod;
	private Integer rejection;
	private String createdProcess;
	private String modifyProcess;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private Set<PolicyCoverageBillEntity> policyCoverageBills = new HashSet<PolicyCoverageBillEntity>(0);

	@Id
	@Column(name = "BIL_NO", unique = true, nullable = false, precision = 12, scale = 0)
	public long getBilNo() {
		return this.bilNo;
	}

	public void setBilNo(long bilNo) {
		this.bilNo = bilNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BILLING_RUNSBIR_ID")
	public BillingRunEntity getBillingRun() {
		return this.billingRun;
	}

	public void setBillingRun(BillingRunEntity billingRun) {
		this.billingRun = billingRun;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_POLICIESPOL_ID")
	public PolicyEntity getPolicy() {
		return this.policy;
	}

	public void setPolicy(PolicyEntity policy) {
		this.policy = policy;
	}

	@Column(name = "POLICY", length = 14)
	public String getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	@Column(name = "COLLECTION_REFERENCE", length = 16)
	public String getCollectionReference() {
		return this.collectionReference;
	}

	public void setCollectionReference(String collectionReference) {
		this.collectionReference = collectionReference;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE0", length = 23)
	public Date getDate0() {
		return this.date0;
	}

	public void setDate0(Date date0) {
		this.date0 = date0;
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

	@Column(name = "REJECTION")
	public Integer getRejection() {
		return this.rejection;
	}

	public void setRejection(Integer rejection) {
		this.rejection = rejection;
	}

	@Column(name = "CREATED_PROCESS", length = 12)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "MODIFY_PROCESS", length = 12)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bill")
	public Set<PolicyCoverageBillEntity> getPolicyCoverageBills() {
		return this.policyCoverageBills;
	}

	public void setPolicyCoverageBills(Set<PolicyCoverageBillEntity> policyCoverageBills) {
		this.policyCoverageBills = policyCoverageBills;
	}

}
