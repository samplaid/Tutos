package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryChangeDTO extends AmendmentDTO {

	private Collection<BeneficiaryDTO> lifeBeneficiaries = new ArrayList<>();
	private Collection<BeneficiaryDTO> deathBeneficiaries = new ArrayList<>();
	private Collection<OtherClientDTO> otherClients = new ArrayList<>();
	private Collection<PolicyHolderDTO> policyHolders = new ArrayList<>();
	private Collection<PolicyBeneficiaryClauseDTO> lifeBenefClauses = new ArrayList<>();
	private Collection<PolicyBeneficiaryClauseDTO> deathBenefClauses = new ArrayList<>();
	private Collection<PolicyBeneficiaryClauseDTO> deathNominativeClauses = new ArrayList<>();
	private Collection<PolicyBeneficiaryClauseDTO> lifeNominativeClauses = new ArrayList<>();

	public Collection<BeneficiaryDTO> getLifeBeneficiaries() {
		return lifeBeneficiaries;
	}

	public void setLifeBeneficiaries(Collection<BeneficiaryDTO> lifeBeneficiaries) {
		this.lifeBeneficiaries = lifeBeneficiaries;
	}

	public Collection<BeneficiaryDTO> getDeathBeneficiaries() {
		return deathBeneficiaries;
	}

	public void setDeathBeneficiaries(Collection<BeneficiaryDTO> deathBeneficiaries) {
		this.deathBeneficiaries = deathBeneficiaries;
	}

	public Collection<OtherClientDTO> getOtherClients() {
		return otherClients;
	}

	public void setOtherClients(Collection<OtherClientDTO> otherClients) {
		this.otherClients = otherClients;
	}

	public Collection<PolicyBeneficiaryClauseDTO> getLifeBenefClauses() {
		return lifeBenefClauses;
	}

	public void setLifeBenefClauses(Collection<PolicyBeneficiaryClauseDTO> lifeBenefClauses) {
		this.lifeBenefClauses = lifeBenefClauses;
	}

	public Collection<PolicyBeneficiaryClauseDTO> getDeathBenefClauses() {
		return deathBenefClauses;
	}

	public void setDeathBenefClauses(Collection<PolicyBeneficiaryClauseDTO> deathBenefClauses) {
		this.deathBenefClauses = deathBenefClauses;
	}

	public Collection<PolicyBeneficiaryClauseDTO> getDeathNominativeClauses() {
		return deathNominativeClauses;
	}

	public void setDeathNominativeClauses(Collection<PolicyBeneficiaryClauseDTO> deathNominativeClauses) {
		this.deathNominativeClauses = deathNominativeClauses;
	}

	public Collection<PolicyBeneficiaryClauseDTO> getLifeNominativeClauses() {
		return lifeNominativeClauses;
	}

	public void setLifeNominativeClauses(Collection<PolicyBeneficiaryClauseDTO> lifeNominativeClauses) {
		this.lifeNominativeClauses = lifeNominativeClauses;
	}

	public Collection<PolicyHolderDTO> getPolicyHolders() {
		return policyHolders;
	}

	public void setPolicyHolders(Collection<PolicyHolderDTO> policyHolders) {
		this.policyHolders = policyHolders;
	}
}
