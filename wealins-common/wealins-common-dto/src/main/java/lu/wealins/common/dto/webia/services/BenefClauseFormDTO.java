package lu.wealins.common.dto.webia.services;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BenefClauseFormDTO   {


	private Integer benefClauseFormId;
	private Integer formId;
	private String clauseFormTp;
	private String clauseTp;
	private String clauseCd;
	private Integer rankNumber;
	private String clauseText;
	private String creationUser;
	private Date creationDt;
	private String updateUser;
	private Date updateDt;

	public Integer getBenefClauseFormId() {
		return benefClauseFormId;
	}

	public void setBenefClauseFormId(Integer benefClauseFormId) {
		this.benefClauseFormId = benefClauseFormId;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getClauseFormTp() {
		return clauseFormTp;
	}

	public void setClauseFormTp(String clauseFormTp) {
		this.clauseFormTp = clauseFormTp;
	}

	public String getClauseTp() {
		return clauseTp;
	}

	public void setClauseTp(String clauseTp) {
		this.clauseTp = clauseTp;
	}

	public String getClauseCd() {
		return clauseCd;
	}

	public void setClauseCd(String clauseCd) {
		this.clauseCd = clauseCd;
	}
	public Integer getRankNumber() {
		return rankNumber;
	}
	public void setRankNumber(Integer rankNumber) {
		this.rankNumber = rankNumber;
	}

	public String getClauseText() {
		return clauseText;
	}

	public void setClauseText(String clauseText) {
		this.clauseText = clauseText;
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

}
