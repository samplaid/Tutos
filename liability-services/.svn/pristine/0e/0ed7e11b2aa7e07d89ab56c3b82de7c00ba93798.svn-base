package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lu.wealins.liability.services.core.utils.Historizable;


/**
 * The persistent class for the POLICY_BENEFICIARY_CLAUSES database table.
 * 
 */
@Entity
@Table(name="POLICY_BENEFICIARY_CLAUSES")
@Historizable(id = "pbcId")
public class PolicyBeneficiaryClauseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PBC_ID")
	private Long pbcId;

	@Column(name="CODE")
	private String code;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name="CREATED_PROCESS")
	private String createdProcess;

	@Column(name="CREATED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name="FK_POLICIESPOL_ID")
	private String fkPoliciespolId;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Column(name="MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

	@Column(name="MODIFY_PROCESS")
	private String modifyProcess;

	@Column(name="MODIFY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;

	@Column(name="POLICY")
	private String policy;

	@Column(name="RANK")
	private Integer rank;

	@Column(name="STATUS")
	private Integer status;

	@Column(name="TEXT_OF_CLAUSE")
	private String textOfClause;

	@Column(name="[TYPE]")
	private String type;

	@Column(name="TYPE_OF_CLAUSE")
	private String typeOfClause;

	public Long getPbcId() {
		return pbcId;
	}

	public void setPbcId(Long pbcId) {
		this.pbcId = pbcId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getFkPoliciespolId() {
		return fkPoliciespolId;
	}

	public void setFkPoliciespolId(String fkPoliciespolId) {
		this.fkPoliciespolId = fkPoliciespolId;
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

	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTextOfClause() {
		return textOfClause;
	}

	public void setTextOfClause(String textOfClause) {
		this.textOfClause = textOfClause;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeOfClause() {
		return typeOfClause;
	}

	public void setTypeOfClause(String typeOfClause) {
		this.typeOfClause = typeOfClause;
	}

}