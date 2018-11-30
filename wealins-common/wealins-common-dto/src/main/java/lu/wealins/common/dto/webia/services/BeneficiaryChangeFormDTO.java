package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryChangeFormDTO extends AmendmentFormDTO {

	private Collection<BeneficiaryFormDTO> lifeBeneficiaries = new ArrayList<>();
	private Collection<BeneficiaryFormDTO> deathBeneficiaries = new ArrayList<>();
	private Collection<PolicyHolderFormDTO> policyHolders = new ArrayList<>();
	private Collection<ClientFormDTO> otherClients = new ArrayList<>();
	private Collection<BenefClauseFormDTO> lifeBenefClauseForms = new ArrayList<>();
	private Collection<BenefClauseFormDTO> deathBenefClauseForms = new ArrayList<>();

	public Collection<BeneficiaryFormDTO> getLifeBeneficiaries() {
		return lifeBeneficiaries;
	}

	public void setLifeBeneficiaries(Collection<BeneficiaryFormDTO> lifeBeneficiaries) {
		this.lifeBeneficiaries = lifeBeneficiaries;
	}

	public Collection<BeneficiaryFormDTO> getDeathBeneficiaries() {
		return deathBeneficiaries;
	}

	public void setDeathBeneficiaries(Collection<BeneficiaryFormDTO> deathBeneficiaries) {
		this.deathBeneficiaries = deathBeneficiaries;
	}

	public Collection<BenefClauseFormDTO> getLifeBenefClauseForms() {
		return lifeBenefClauseForms;
	}

	public void setLifeBenefClauseForms(Collection<BenefClauseFormDTO> lifeBenefClauseForms) {
		this.lifeBenefClauseForms = lifeBenefClauseForms;
	}

	public Collection<BenefClauseFormDTO> getDeathBenefClauseForms() {
		return deathBenefClauseForms;
	}

	public void setDeathBenefClauseForms(Collection<BenefClauseFormDTO> deathBenefClauseForms) {
		this.deathBenefClauseForms = deathBenefClauseForms;
	}

	public Collection<ClientFormDTO> getOtherClients() {
		return otherClients;
	}

	public void setOtherClients(Collection<ClientFormDTO> otherClients) {
		this.otherClients = otherClients;
	}

	public Collection<PolicyHolderFormDTO> getPolicyHolders() {
		return policyHolders;
	}

	public void setPolicyHolders(Collection<PolicyHolderFormDTO> policyHolders) {
		this.policyHolders = policyHolders;
	}

}
