package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateInputRequest {

	private String policyId;
	private String userName;
	private String brokerPartnerAuthorized;
	private String brokerRefContract;	
	private Boolean mandate;
	private String sendingRules;
	private String mailToAgent;
	private Boolean orderByFax;
	private Boolean noCoolOff;
	private Boolean paymentTransfer;
	private Date paymentDt;
	private Collection<PolicyBeneficiaryClauseDTO> deathPolicyBeneficiaryClauses = new ArrayList<>();
	private Collection<PolicyBeneficiaryClauseDTO> lifePolicyBeneficiaryClauses = new ArrayList<>();
	private Collection<BeneficiaryDTO> lifeBeneficiaries = new ArrayList<>();
	private Collection<BeneficiaryDTO> deathBeneficiaries = new ArrayList<>();
	private Collection<PolicyHolderDTO> policyHolders = new ArrayList<>();
	private Collection<OtherClientDTO> otherClients = new HashSet<>(0);
	private Collection<PolicyTransferDTO> policyTransfers = new ArrayList<>();

	public Collection<PolicyBeneficiaryClauseDTO> getDeathPolicyBeneficiaryClauses() {
		return deathPolicyBeneficiaryClauses;
	}

	public void setDeathPolicyBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> deathPolicyBeneficiaryClauses) {
		this.deathPolicyBeneficiaryClauses = deathPolicyBeneficiaryClauses;
	}

	public Collection<PolicyBeneficiaryClauseDTO> getLifePolicyBeneficiaryClauses() {
		return lifePolicyBeneficiaryClauses;
	}

	public void setLifePolicyBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> lifePolicyBeneficiaryClauses) {
		this.lifePolicyBeneficiaryClauses = lifePolicyBeneficiaryClauses;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	
	public String getBrokerPartnerAuthorized() {
		return brokerPartnerAuthorized;
	}
	
	public void setBrokerPartnerAuthorized(String brokerPartnerAuthorized) {
		this.brokerPartnerAuthorized = brokerPartnerAuthorized;
	}
	
	public String getBrokerRefContract() {
		return brokerRefContract;
	}
	
	public void setBrokerRefContract(String brokerRefContract) {
		this.brokerRefContract = brokerRefContract;
	}
	
	public Boolean getMandate() {
		return mandate;
	}
	
	public void setMandate(Boolean mandate) {
		this.mandate = mandate;
	}
	
	public String getSendingRules() {
		return sendingRules;
	}
	
	public void setSendingRules(String sendingRules) {
		this.sendingRules = sendingRules;
	}
	
	public String getMailToAgent() {
		return mailToAgent;
	}
	
	public void setMailToAgent(String mailToAgent) {
		this.mailToAgent = mailToAgent;
	}
	
	public Boolean getOrderByFax() {
		return orderByFax;
	}
	
	public void setOrderByFax(Boolean orderByFax) {
		this.orderByFax = orderByFax;
	}
	
	public Boolean getNoCoolOff() {
		return noCoolOff;
	}
	
	public void setNoCoolOff(Boolean noCoolOff) {
		this.noCoolOff = noCoolOff;
	}
	
	public Boolean getPaymentTransfer() {
		return paymentTransfer;
	}
	
	public void setPaymentTransfer(Boolean paymentTransfer) {
		this.paymentTransfer = paymentTransfer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Collection<PolicyHolderDTO> getPolicyHolders() {
		return policyHolders;
	}

	public void setPolicyHolders(Collection<PolicyHolderDTO> policyHolders) {
		this.policyHolders = policyHolders;
	}

	public Date getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(Date paymentDt) {
		this.paymentDt = paymentDt;
	}

	public Collection<OtherClientDTO> getOtherClients() {
		return otherClients;
	}

	public void setOtherClients(Collection<OtherClientDTO> otherClients) {
		this.otherClients = otherClients;
	}

	public Collection<PolicyTransferDTO> getPolicyTransfers() {
		return policyTransfers;
	}

	public void setPolicyTransfers(Collection<PolicyTransferDTO> policyTransfers) {
		this.policyTransfers = policyTransfers;
	}
}
