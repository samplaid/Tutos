package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CHECK_DATA")
public class CheckDataEntity extends AuditingEntity {

	private static final long serialVersionUID = -3843829029671871955L;

	@Id
	@Column(name = "CHECK_DATA_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer checkDataId;

	@Column(name = "WORKFLOW_ITEM_ID")
	private Integer workflowItemId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECK_ID", unique = true, nullable = false, insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private CheckWorkflowEntity checkWorkflow;

	@Column(name = "CHECK_ID")
	private Integer checkId;

	@Column(name = "DATA_VALUE_YESNONA", columnDefinition = "char(3)")
	private String dataValueYesNoNa;

	@Column(name = "DATA_VALUE_TEXT")
	private String dataValueText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_VALUE_DATE")
	private Date dataValueDate;

	@Column(name = "DATA_VALUE_AMOUNT")
	private BigDecimal dataValueAmount;

	@Column(name = "DATA_VALUE_NUMBER")
	private BigDecimal dataValueNumber;

	@Column(name = "COMMENT_IF")
	private String commentIf;

	public Integer getCheckDataId() {
		return checkDataId;
	}

	public void setCheckDataId(Integer checkDataId) {
		this.checkDataId = checkDataId;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public String getDataValueYesNoNa() {
		return dataValueYesNoNa;
	}

	public void setDataValueYesNoNa(String dataValueYesNoNa) {
		this.dataValueYesNoNa = dataValueYesNoNa;
	}

	public String getDataValueText() {
		return dataValueText;
	}

	public void setDataValueText(String dataValueText) {
		this.dataValueText = dataValueText;
	}

	public Date getDataValueDate() {
		return dataValueDate;
	}

	public void setDataValueDate(Date dataValueDate) {
		this.dataValueDate = dataValueDate;
	}

	public BigDecimal getDataValueAmount() {
		return dataValueAmount;
	}

	public void setDataValueAmount(BigDecimal dataValueAmount) {
		this.dataValueAmount = dataValueAmount;
	}

	public BigDecimal getDataValueNumber() {
		return dataValueNumber;
	}

	public void setDataValueNumber(BigDecimal dataValueNumber) {
		this.dataValueNumber = dataValueNumber;
	}

	public String getCommentIf() {
		return commentIf;
	}

	public void setCommentIf(String commentIf) {
		this.commentIf = commentIf;
	}

	public CheckWorkflowEntity getCheckWorkflow() {
		return checkWorkflow;
	}

	public void setCheckWorkflow(CheckWorkflowEntity checkWorkflow) {
		this.checkWorkflow = checkWorkflow;
	}

}
