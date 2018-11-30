package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StepDTO {

	private Integer stepId;

	private Integer workflowItemId;

	private Integer workflowItemTypeId;

	private Boolean stepRejectable;

	private Boolean stepAbortable;

	private String stepWorkflow;

	private Integer workFlowStatus;

	private FormDataDTO formData;

	private String applicationForm;

	private String productCd;

	private String productCountryCd;

	private String productName;

	private String rejectReason;

	private String polId;

	private Collection<CheckStepDTO> checkSteps = new ArrayList<>();

	/** FOR WEBIA-APPLICATION **/
	private Boolean updatable;

	private String firstCpsUser;

	private String secondCpsUser;

	private String assignedTo;

	private Boolean rejected;

	private List<String> errors = new ArrayList<>();

	/** END FOR WEBIA-APPLICATION **/

	public String getProductCountryCd() {
		return productCountryCd;
	}

	public void setProductCountryCd(String productCountryCd) {
		this.productCountryCd = productCountryCd;
	}

	public Boolean getStepRejectable() {
		return stepRejectable;
	}

	public void setStepRejectable(Boolean stepRejectable) {
		this.stepRejectable = stepRejectable;
	}

	public Boolean getStepAbortable() {
		return stepAbortable;
	}

	public void setStepAbortable(Boolean stepAbortable) {
		this.stepAbortable = stepAbortable;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Integer getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(Integer workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Integer getWorkflowItemTypeId() {
		return workflowItemTypeId;
	}

	public void setWorkflowItemTypeId(Integer workflowItemTypeId) {
		this.workflowItemTypeId = workflowItemTypeId;
	}

	public String getStepWorkflow() {
		return stepWorkflow;
	}

	public void setStepWorkflow(String stepWorkflow) {
		this.stepWorkflow = stepWorkflow;
	}

	public FormDataDTO getFormData() {
		return formData;
	}

	public void setFormData(FormDataDTO formData) {
		this.formData = formData;
	}

	public String getApplicationForm() {
		return applicationForm;
	}

	public void setApplicationForm(String applicationForm) {
		this.applicationForm = applicationForm;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public Collection<CheckStepDTO> getCheckSteps() {
		return checkSteps;
	}

	public void setCheckSteps(Collection<CheckStepDTO> checkSteps) {
		this.checkSteps = checkSteps;
	}

	public Boolean getUpdatable() {
		return updatable;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
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

	public Boolean getRejected() {
		return rejected;
	}

	public void setRejected(Boolean isRejected) {
		this.rejected = isRejected;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}