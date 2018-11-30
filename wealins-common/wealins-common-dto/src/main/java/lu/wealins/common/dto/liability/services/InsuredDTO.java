package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InsuredDTO extends ClientDTO {

	private Boolean economicBeneficiary;

	public Boolean getEconomicBeneficiary() {
		return economicBeneficiary;
	}

	public void setEconomicBeneficiary(Boolean economicBeneficiary) {
		this.economicBeneficiary = economicBeneficiary;
	}

}
