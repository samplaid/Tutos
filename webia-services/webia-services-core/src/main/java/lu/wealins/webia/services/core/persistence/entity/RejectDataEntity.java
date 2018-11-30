package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "REJECT_DATA")
@EntityListeners(AuditingEntityListener.class)
public class RejectDataEntity implements Serializable {

	private static final long serialVersionUID = 7546888227659900362L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REJECT_DATA_ID")
	private Integer rejectDataId;

	@Column(name = "WORKFLOW_ITEM_ID")
	private Integer workflowItemId;

	@Column(name = "STEP_ID")
	private Integer stepId;

	@Column(name = "REJECT_COMMENT")
	private String rejectComment;

	@Column(name = "CREATION_USER")
	@CreatedBy
	private String creationUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DT")
	@CreatedDate
	private Date creationDt;

	public Integer getRejectDataId() {
		return rejectDataId;
	}

	public void setRejectDataId(Integer rejectDataId) {
		this.rejectDataId = rejectDataId;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getRejectComment() {
		return rejectComment;
	}

	public void setRejectComment(String rejectComment) {
		this.rejectComment = rejectComment;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Date getCreationDt() {
		return creationDt;
	}

	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}

}
