package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FundTransactionsValidationRequest {
	private String policyId;
	private Date effectiveDate;
	private List<Integer> eventTypes = new ArrayList<>();
	private List<Integer> valorizedStatus = new ArrayList<>();
	private List<Integer> unValorizedStatus = new ArrayList<>();

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public List<Integer> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(List<Integer> eventTypes) {
		this.eventTypes = eventTypes;
	}

	public List<Integer> getValorizedStatus() {
		return valorizedStatus;
	}

	public void setValorizedStatus(List<Integer> valorizedStatus) {
		this.valorizedStatus = valorizedStatus;
	}

	public List<Integer> getUnValorizedStatus() {
		return unValorizedStatus;
	}

	public void setUnValorizedStatus(List<Integer> unValorizedStatus) {
		this.unValorizedStatus = unValorizedStatus;
	}
}
