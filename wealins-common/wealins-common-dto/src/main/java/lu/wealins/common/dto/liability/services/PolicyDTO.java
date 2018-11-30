package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyDTO {

	private String polId;
	private ProductDTO product;
	private Date dateOfCommencement;
	private OptionDetailDTO policyStatus;
	private String activeStatus;
	private String additionalId; // = application Form
	private String currency;
	private PolicyAgentShareDTO broker;
	private PolicyAgentShareDTO brokerContact;
	private PolicyAgentShareDTO subBroker;
	private String brokerRefContract;
	private PolicyAgentShareDTO businessIntroducer;
	private Collection<PolicyAgentShareDTO> countryManagers = new HashSet<>(0);
	private PolicyCoverageDTO firstPolicyCoverages;
	private Collection<PolicyHolderDTO> policyHolders = new HashSet<>(0);
	private Collection<InsuredDTO> insureds = new HashSet<>(0);
	private Collection<BeneficiaryDTO> lifeBeneficiaries = new HashSet<>(0);
	private Collection<BeneficiaryDTO> deathBeneficiaries = new HashSet<>(0);
	private Collection<OtherClientDTO> otherClients = new HashSet<>(0);
	private UoptDetailDTO category;
	private Boolean orderByFax;
	private AgentLightDTO mailToAgent;
	private Date nonSurrenderClauseDate;
	private Boolean scudo;
	private Boolean mandatoAllIncasso;
	private Boolean assignmentPledge;
	private String assignmentPledgeDetails;
	private Integer scoreNewBusiness;
	private Integer scoreLastTrans;
	private List<PolicyNoteDTO> policyNotes = new ArrayList<>();
	private List<PolicyPremiumDTO> policyPremiums = new ArrayList<>();
	private Boolean noCooloff;
	private String issueCountryOfResidence;
	private Boolean exPaymentTransfer;
	private Boolean exMandate;
	private Date createdDate;
	private Collection<PolicyTransferDTO> policyTransfers = new HashSet<>(0);

	public PolicyAgentShareDTO getSubBroker() {
		return subBroker;
	}

	public void setSubBroker(PolicyAgentShareDTO subBroker) {
		this.subBroker = subBroker;
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
		PolicyDTO other = (PolicyDTO) obj;
		if (polId == null) {
			if (other.polId != null)
				return false;
		} else if (!polId.equals(other.polId))
			return false;
		return true;
	}

	public List<PolicyNoteDTO> getPolicyNotes() {
		return policyNotes;
	}

	public void setPolicyNotes(List<PolicyNoteDTO> policyNotes) {
		this.policyNotes = policyNotes;
	}

	public Integer getScoreNewBusiness() {
		return scoreNewBusiness;
	}

	public void setScoreNewBusiness(Integer scoreNewBusiness) {
		this.scoreNewBusiness = scoreNewBusiness;
	}

	public Integer getScoreLastTrans() {
		return scoreLastTrans;
	}

	public void setScoreLastTrans(Integer scoreLastTrans) {
		this.scoreLastTrans = scoreLastTrans;
	}

	public Boolean getMandatoAllIncasso() {
		return mandatoAllIncasso;
	}

	public void setMandatoAllIncasso(Boolean mandatoAllIncasso) {
		this.mandatoAllIncasso = mandatoAllIncasso;
	}

	public Boolean getScudo() {
		return scudo;
	}

	public void setScudo(Boolean scudo) {
		this.scudo = scudo;
	}

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
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

	public String getAdditionalId() {
		return additionalId;
	}

	public void setAdditionalId(String additionalId) {
		this.additionalId = additionalId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public PolicyAgentShareDTO getBroker() {
		return broker;
	}

	public void setBroker(PolicyAgentShareDTO broker) {
		this.broker = broker;
	}

	public String getBrokerRefContract() {
		return brokerRefContract;
	}

	public void setBrokerRefContract(String brokerRefContract) {
		this.brokerRefContract = brokerRefContract;
	}

	public PolicyAgentShareDTO getBusinessIntroducer() {
		return businessIntroducer;
	}

	public void setBusinessIntroducer(PolicyAgentShareDTO businessIntroducer) {
		this.businessIntroducer = businessIntroducer;
	}

	public Collection<PolicyAgentShareDTO> getCountryManagers() {
		return countryManagers;
	}

	public void setCountryManagers(Collection<PolicyAgentShareDTO> countryManagers) {
		this.countryManagers = countryManagers;
	}

	public PolicyCoverageDTO getFirstPolicyCoverages() {
		return firstPolicyCoverages;
	}

	public void setFirstPolicyCoverages(PolicyCoverageDTO firstPolicyCoverages) {
		this.firstPolicyCoverages = firstPolicyCoverages;
	}

	public Collection<PolicyHolderDTO> getPolicyHolders() {
		return policyHolders;
	}

	public void setPolicyHolders(Collection<PolicyHolderDTO> policyHolders) {
		this.policyHolders = policyHolders;
	}

	public Collection<InsuredDTO> getInsureds() {
		return insureds;
	}

	public void setInsureds(Collection<InsuredDTO> insureds) {
		this.insureds = insureds;
	}

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

	public UoptDetailDTO getCategory() {
		return category;
	}

	public void setCategory(UoptDetailDTO category) {
		this.category = category;
	}

	public Boolean getOrderByFax() {
		return orderByFax;
	}

	public void setOrderByFax(Boolean orderByFax) {
		this.orderByFax = orderByFax;
	}

	public AgentLightDTO getMailToAgent() {
		return mailToAgent;
	}

	public void setMailToAgent(AgentLightDTO mailToAgent) {
		this.mailToAgent = mailToAgent;
	}

	public Date getNonSurrenderClauseDate() {
		return nonSurrenderClauseDate;
	}

	public void setNonSurrenderClauseDate(Date nonSurrenderClauseDate) {
		this.nonSurrenderClauseDate = nonSurrenderClauseDate;
	}

	public Boolean getAssignmentPledge() {
		return assignmentPledge;
	}

	public void setAssignmentPledge(Boolean assignmentPledge) {
		this.assignmentPledge = assignmentPledge;
	}

	public List<PolicyPremiumDTO> getPolicyPremiums() {
		return policyPremiums;
	}

	public void setPolicyPremiums(List<PolicyPremiumDTO> policyPremiums) {
		this.policyPremiums = policyPremiums;
	}

	public Boolean getNoCooloff() {
		return noCooloff;
	}

	public void setNoCooloff(Boolean noCooloff) {
		this.noCooloff = noCooloff;
	}

	public void setBrokerContact(PolicyAgentShareDTO brokerContact) {
		this.brokerContact = brokerContact;
	}

	public PolicyAgentShareDTO getBrokerContact() {
		return brokerContact;
	}

	public String getIssueCountryOfResidence() {
		return issueCountryOfResidence;
	}

	public void setIssueCountryOfResidence(String issueCountryOfResidence) {
		this.issueCountryOfResidence = issueCountryOfResidence;
	}

	public String getAssignmentPledgeDetails() {
		return assignmentPledgeDetails;
	}

	public void setAssignmentPledgeDetails(String assignmentPledgeDetails) {
		this.assignmentPledgeDetails = assignmentPledgeDetails;
	}

	/**
	 * @return the exPaymentTransfer
	 */
	public Boolean getExPaymentTransfer() {
		return exPaymentTransfer;
	}

	/**
	 * @param exPaymentTransfer the exPaymentTransfer to set
	 */
	public void setExPaymentTransfer(Boolean exPaymentTransfer) {
		this.exPaymentTransfer = exPaymentTransfer;
	}

	/**
	 * @return the exMandate
	 */
	public Boolean getExMandate() {
		return exMandate;
	}

	/**
	 * @param exMandate the exMandate to set
	 */
	public void setExMandate(Boolean exMandate) {
		this.exMandate = exMandate;
	}

	public Collection<PolicyTransferDTO> getPolicyTransfers() {
		return policyTransfers;
	}

	public void setPolicyTransfers(Collection<PolicyTransferDTO> policyTransfers) {
		this.policyTransfers = policyTransfers;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
