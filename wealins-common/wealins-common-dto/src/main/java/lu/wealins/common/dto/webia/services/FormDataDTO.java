package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = AppFormDTO.class, name = "APP_FORM"),
		@JsonSubTypes.Type(value = BeneficiaryChangeFormDTO.class, name = "CHANGE_BENEF_FORM"),
		@JsonSubTypes.Type(value = BrokerChangeFormDTO.class, name = "BROKER_CHANGE_FORM"),
		@JsonSubTypes.Type(value = TransactionFormDTO.class, name = "TRANSACTION_FORM")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class FormDataDTO {

	private Integer formId;
	private Integer workflowItemId;
	private String statusCd;
	private String policyId;
	private String firstCpsUser;
	private String secondCpsUser;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getFirstCpsUser() {
		return firstCpsUser;
	}

	public void setFirstCpsUser(String firstCpsUser) {
		this.firstCpsUser = firstCpsUser;
	}

	public String getSecondCpsUser() {
		return secondCpsUser;
	}

	public void setSecondCpsUser(String secondCpsUser) {
		this.secondCpsUser = secondCpsUser;
	}

}

