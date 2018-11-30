package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckDataDTO {

	private Integer checkDataId;

	private Integer workflowItemId;

	private Integer checkId;

	private String dataValueYesNoNa;

	private String dataValueText;

	private Date dataValueDate;

	private BigDecimal dataValueAmount;

	private BigDecimal dataValueNumber;

	private String commentIf;

	private String creationUser;

	private Date creationDt;

	private String updateUser;

	private Date updateDt;

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

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

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

	public boolean hasValue(){
		return (getDataValueDate() != null
				|| getDataValueAmount() != null
				|| getDataValueNumber() != null
				|| !StringUtils.isEmpty(getDataValueText())
				|| !StringUtils.isEmpty(getDataValueYesNoNa()));
	}

}
