package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CHECK_WORKFLOW")
public class CheckWorkflowEntity implements Serializable {

	private static final long serialVersionUID = -7196651349374140539L;

	@Id
	@Column(name = "CHECK_ID")
	private Integer checkId;
	
	@Column(name = "CHECK_DESC")
	private String checkDesc;

	@Column(name = "CHECK_EXPLAIN")
	private String checkExplain;

	@Column(name = "CHECK_TYPE")
	private String checkType;

	@Column(name = "DEFAULTVALUE")
	private String defaultValue;

	@Column(name = "CHECKCODE")
	private String checkCode;
	
	@Column(name = "COMMENTIF")
	private String commentIf;
	
	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}

	public String getCheckExplain() {
		return checkExplain;
	}

	public void setCheckExplain(String checkExplain) {
		this.checkExplain = checkExplain;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getCommentIf() {
		return commentIf;
	}

	public void setCommentIf(String commentIf) {
		this.commentIf = commentIf;
	}
}
