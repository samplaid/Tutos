package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

@Entity
@Table(name = "WORKFLOW_USER_GROUPS")
// WORKFLOW_USER_GROUPS is a SYNONYM
public class WorkflowGroupEntity implements Serializable {

	private static final long serialVersionUID = -5637061388820800049L;

	@Id
	@Column(name = "URG_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer urgId;

	@Column(name = "NAME0")
	private String name0;

	@Column(name = "CREATED_PROCESS")
	private String createdProcess;

	@Column(name = "MODIFY_PROCESS")
	private String modifyProcess;

	@Column(name = "STATUS")
	private BigDecimal status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_TIME")
	private Date createdTime;

	@Column(name = "MODIFY_BY")
	private String modifyBy;

	@Column(name = "MODIFY_DATE")
	private Date modifyDate;

	@Column(name = "MODIFY_TIME")
	private Date modifyTime;

	@Column(name = "AGENCY")
	private Integer agency;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "WORKFLOW_USER_GROUP_RELATIONSHIPS", joinColumns = {
			@JoinColumn(name = "FK_USER_GROUPSURG_ID")
	}, inverseJoinColumns = {
			@JoinColumn(name = "FK_USERSUSR_ID")
	})
	// filter relation status <> 3
	@WhereJoinTable(clause = "STATUS <> 3")
	// filter user status <> 3
	@Where(clause = "status <> 3")
	@OrderBy("name0")
	private List<WorkflowUserEntity> users = new ArrayList<WorkflowUserEntity>();

	public List<WorkflowUserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<WorkflowUserEntity> users) {
		this.users = users;
	}

	public Integer getUrgId() {
		return urgId;
	}

	public void setUrgId(Integer urgId) {
		this.urgId = urgId;
	}

	public String getName0() {
		return name0;
	}

	public void setName0(String name0) {
		this.name0 = name0;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

}
