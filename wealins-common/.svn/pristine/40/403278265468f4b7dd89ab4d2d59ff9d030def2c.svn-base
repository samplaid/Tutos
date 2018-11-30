package lu.wealins.common.dto.liability.services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyLightDTO {

	private String polId;
	private String productName;
	private String nlCountry;
	private String prdId;
	private Date dateOfCommencement;
	private OptionDetailDTO policyStatus;
	private String activeStatus;
	private String currency;
	private PolicyCoverageDTO firstCoverage;
	private Collection<PolicyHolderLiteDTO> policyHolders = new HashSet<>(0);
	private Collection<InsuredLiteDTO> insureds = new HashSet<>(0);
	private Collection<BeneficiaryLiteDTO> lifeBeneficiaries = new HashSet<>(0);
	private Collection<BeneficiaryLiteDTO> deathBeneficiaries = new HashSet<>(0);
	private Collection<OtherClientLiteDTO> otherClients = new HashSet<>(0);
	private PolicyAgentShareDTO broker;

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getDateOfCommencement() {
		return dateOfCommencement;
	}

	public void setDateOfCommencement(Date dateOfCommencement) {
		this.dateOfCommencement = dateOfCommencement;
	}

	public OptionDetailDTO getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(OptionDetailDTO policyStatus) {
		this.policyStatus = policyStatus;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Collection<PolicyHolderLiteDTO> getPolicyHolders() {
		return policyHolders;
	}

	public void setPolicyHolders(Collection<PolicyHolderLiteDTO> policyHolders) {
		this.policyHolders = policyHolders;
	}

	public String getPrdId() {
		return prdId;
	}

	public void setPrdId(String prdId) {
		this.prdId = prdId;
	}

	public Collection<InsuredLiteDTO> getInsureds() {
		return insureds;
	}

	public void setInsureds(Collection<InsuredLiteDTO> insureds) {
		this.insureds = insureds;
	}

	public Collection<BeneficiaryLiteDTO> getLifeBeneficiaries() {
		return lifeBeneficiaries;
	}

	public void setLifeBeneficiaries(Collection<BeneficiaryLiteDTO> lifeBeneficiaries) {
		this.lifeBeneficiaries = lifeBeneficiaries;
	}

	public Collection<BeneficiaryLiteDTO> getDeathBeneficiaries() {
		return deathBeneficiaries;
	}

	public void setDeathBeneficiaries(Collection<BeneficiaryLiteDTO> deathBeneficiaries) {
		this.deathBeneficiaries = deathBeneficiaries;
	}

	public Collection<OtherClientLiteDTO> getOtherClients() {
		return otherClients;
	}

	public void setOtherClients(Collection<OtherClientLiteDTO> otherClients) {
		this.otherClients = otherClients;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((polId == null) ? 0 : polId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolicyLightDTO other = (PolicyLightDTO) obj;
		if (polId == null) {
			if (other.polId != null)
				return false;
		} else if (!polId.equals(other.polId))
			return false;
		return true;
	}

	public String getNlCountry() {
		return nlCountry;
	}

	public void setNlCountry(String nlCountry) {
		this.nlCountry = nlCountry;
	}

	public PolicyCoverageDTO getFirstCoverage() {
		return firstCoverage;
	}

	public void setFirstCoverage(PolicyCoverageDTO firstCoverage) {
		this.firstCoverage = firstCoverage;
	}

	public PolicyAgentShareDTO getBroker() {
		return broker;
	}

	public void setBroker(PolicyAgentShareDTO broker) {
		this.broker = broker;
	}
}
