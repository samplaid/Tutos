package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserPermissionEntity generated by hbm2java
 */
@Entity
@Table(name = "USER_PERMISSIONS"

)
public class UserPermissionEntity implements java.io.Serializable {

	private String urpId;
	private UserGroupEntity userGroup;
	private String createdProcess;
	private String modifyProcess;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private int groupNumber;
	private int access;
	private int createAccess;
	private Integer amendAccess;
	private BigDecimal financialLimit;
	private String currency;
	private String process;
	private Integer executionAccess;
	private String processLevel;

	@Id
	@Column(name = "URP_ID", unique = true, nullable = false, length = 20)
	public String getUrpId() {
		return this.urpId;
	}

	public void setUrpId(String urpId) {
		this.urpId = urpId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_USER_GROUPSURG_ID")
	public UserGroupEntity getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroupEntity userGroup) {
		this.userGroup = userGroup;
	}

	@Column(name = "CREATED_PROCESS", nullable = false, length = 15)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "MODIFY_PROCESS", length = 15)
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

	@Column(name = "CREATED_BY", nullable = false, length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false, length = 23)
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

	@Column(name = "GROUP_NUMBER", nullable = false)
	public int getGroupNumber() {
		return this.groupNumber;
	}

	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}

	@Column(name = "ACCESS", nullable = false)
	public int getAccess() {
		return this.access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	@Column(name = "CREATE_ACCESS", nullable = false)
	public int getCreateAccess() {
		return this.createAccess;
	}

	public void setCreateAccess(int createAccess) {
		this.createAccess = createAccess;
	}

	@Column(name = "AMEND_ACCESS")
	public Integer getAmendAccess() {
		return this.amendAccess;
	}

	public void setAmendAccess(Integer amendAccess) {
		this.amendAccess = amendAccess;
	}

	@Column(name = "FINANCIAL_LIMIT", precision = 15)
	public BigDecimal getFinancialLimit() {
		return this.financialLimit;
	}

	public void setFinancialLimit(BigDecimal financialLimit) {
		this.financialLimit = financialLimit;
	}

	@Column(name = "CURRENCY", length = 5)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "PROCESS", length = 12)
	public String getProcess() {
		return this.process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "EXECUTION_ACCESS")
	public Integer getExecutionAccess() {
		return this.executionAccess;
	}

	public void setExecutionAccess(Integer executionAccess) {
		this.executionAccess = executionAccess;
	}

	@Column(name = "PROCESS_LEVEL", length = 8)
	public String getProcessLevel() {
		return this.processLevel;
	}

	public void setProcessLevel(String processLevel) {
		this.processLevel = processLevel;
	}

}
