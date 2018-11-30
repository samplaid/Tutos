package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARTNER_FORM")
public class PartnerFormEntity extends AuditingEntity {

	private static final long serialVersionUID = -2122273517609325423L;

	@Id
	@Column(name = "PARTNER_FORM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer partnerFormId;

	@Column(name = "FORM_ID")
	private Integer formId;

	@Column(name = "PARTNER_CATEGORY")
	private String partnerCategory;

	@Column(name = "PARTNER_ID")
	private String partnerId;

	@Column(name = "ENTRY_FEES_PCT")
	private BigDecimal entryFeesPct;

	@Column(name = "ENTRY_FEES_AMT")
	private BigDecimal entryFeesAmt;

	@Column(name = "MNGT_FEES_PCT")
	private BigDecimal mngtFeesPct;

	@Column(name = "IS_OVERRIDEDFEES")
	private Boolean isOverridedFees;

	@Column(name = "EXPLAIN_OVERFEES")
	private String explainOverFees;

	@Column(name = "CONTACT_NAME")
	private String contactName;

	@Column(name = "SPLIT")
	private BigDecimal split;

	@Column(name = "PARTNER_AUTHORIZED")
	private Boolean partnerAuthorized;

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

}
