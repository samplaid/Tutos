package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnerFormDTO {

	private Integer partnerFormId;

	private Integer formId;

	private String partnerCategory;

	private String partnerId;

	private BigDecimal entryFeesPct;

	private BigDecimal entryFeesAmt;

	private BigDecimal mngtFeesPct;

	private Boolean isOverridedFees;

	private String explainOverFees;

	private String contactName;

	private BigDecimal split;
	
	private Boolean partnerAuthorized;

	private int status;

	private String creationUser;

	private Date creationDt;

	private String updateUser;

	private Date updateDt;

	public Integer getPartnerFormId() {
		return partnerFormId;
	}

	public void setPartnerFormId(Integer partnerFormId) {
		this.partnerFormId = partnerFormId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getPartnerCategory() {
		return partnerCategory;
	}

	public void setPartnerCategory(String partnerCategory) {
		this.partnerCategory = partnerCategory;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public BigDecimal getEntryFeesPct() {
		return entryFeesPct;
	}

	public void setEntryFeesPct(BigDecimal entryFeesPct) {
		this.entryFeesPct = entryFeesPct;
	}

	public BigDecimal getEntryFeesAmt() {
		return entryFeesAmt;
	}

	public void setEntryFeesAmt(BigDecimal entryFeesAmt) {
		this.entryFeesAmt = entryFeesAmt;
	}

	public BigDecimal getMngtFeesPct() {
		return mngtFeesPct;
	}

	public void setMngtFeesPct(BigDecimal mngtFeesPct) {
		this.mngtFeesPct = mngtFeesPct;
	}

	public Boolean getIsOverridedFees() {
		return isOverridedFees;
	}

	public void setIsOverridedFees(Boolean isOverridedFees) {
		this.isOverridedFees = isOverridedFees;
	}

	public String getExplainOverFees() {
		return explainOverFees;
	}

	public void setExplainOverFees(String explainOverFees) {
		this.explainOverFees = explainOverFees;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public BigDecimal getSplit() {
		return split;
	}

	public void setSplit(BigDecimal split) {
		this.split = split;
	}

	public Boolean getPartnerAuthorized() {
		return partnerAuthorized;
	}

	public void setPartnerAuthorized(Boolean partnerAuthorized) {
		this.partnerAuthorized = partnerAuthorized;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
