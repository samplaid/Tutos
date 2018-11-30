package lu.wealins.common.dto.liability.services;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyBeneficiaryChangeRequest {

	private BeneficiaryChangeDTO beneficiaryChange;
	private Map<String, String> applicationParams = new HashMap<>();

	public BeneficiaryChangeDTO getBeneficiaryChange() {
		return beneficiaryChange;
	}

	public void setBeneficiaryChange(BeneficiaryChangeDTO beneficiaryChange) {
		this.beneficiaryChange = beneficiaryChange;
	}

	public Map<String, String> getApplicationParams() {
		return applicationParams;
	}

	public void setApplicationParams(Map<String, String> applicationParams) {
		this.applicationParams = applicationParams;
	}

}
