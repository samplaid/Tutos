package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FundFormDTO extends AbstractFundFormDTO {

	private Integer fundFormId;

	private Integer formId;

	private Boolean isCashFundAccount;

	private Boolean addOnValuableAmount;

	private Collection<EncashmentFundFormDTO> encashmentFundForms = new ArrayList<>();

	public Integer getFundFormId() {
		return fundFormId;
	}

	public void setFundFormId(Integer fundFormId) {
		this.fundFormId = fundFormId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Boolean getIsCashFundAccount() {
		return isCashFundAccount;
	}

	public void setIsCashFundAccount(Boolean isCashFundAccount) {
		this.isCashFundAccount = isCashFundAccount;
	}

	public Boolean getAddOnValuableAmount() {
		return addOnValuableAmount;
	}

	public void setAddOnValuableAmount(Boolean addOnValuableAmount) {
		this.addOnValuableAmount = addOnValuableAmount;
	}

	public Collection<EncashmentFundFormDTO> getEncashmentFundForms() {
		return encashmentFundForms;
	}

	public void setEncashmentFundForms(Collection<EncashmentFundFormDTO> encashmentFundForms) {
		this.encashmentFundForms = encashmentFundForms;
	}
}
