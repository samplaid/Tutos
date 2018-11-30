package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.liability.services.PolicyValuationDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrokerChangeFormDTO extends AmendmentFormDTO {

	private String brokerRefContract;
	private PartnerFormDTO broker;
	private PartnerFormDTO brokerContact;
	private PartnerFormDTO subBroker;
	private String sendingRules;
	private String mailToAgent;
	private Boolean mandatoAllIncasso;
	private PolicyValuationDTO policyValuation;

	// Field used for validation
	private BigDecimal contractMngtFeesPct;
	private String originalPartnerId;

	public String getBrokerRefContract() {
		return brokerRefContract;
	}

	public void setBrokerRefContract(String brokerRefContract) {
		this.brokerRefContract = brokerRefContract;
	}

	public PartnerFormDTO getBroker() {
		return broker;
	}

	public void setBroker(PartnerFormDTO broker) {
		this.broker = broker;
	}

	public PartnerFormDTO getSubBroker() {
		return subBroker;
	}

	public void setSubBroker(PartnerFormDTO subBroker) {
		this.subBroker = subBroker;
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

	public Boolean getMandatoAllIncasso() {
		return mandatoAllIncasso;
	}

	public void setMandatoAllIncasso(Boolean mandatoAllIncasso) {
		this.mandatoAllIncasso = mandatoAllIncasso;
	}

	public PartnerFormDTO getBrokerContact() {
		return brokerContact;
	}

	public void setBrokerContact(PartnerFormDTO brokerContact) {
		this.brokerContact = brokerContact;
	}

	public PolicyValuationDTO getPolicyValuation() {
		return policyValuation;
	}

	public void setPolicyValuation(PolicyValuationDTO policyValuation) {
		this.policyValuation = policyValuation;
	}

	public BigDecimal getContractMngtFeesPct() {
		return contractMngtFeesPct;
	}

	public void setContractMngtFeesPct(BigDecimal contractMngtFeesPct) {
		this.contractMngtFeesPct = contractMngtFeesPct;
	}

	public String getOriginalPartnerId() {
		return originalPartnerId;
	}

	public void setOriginalPartnerId(String originalPartnerId) {
		this.originalPartnerId = originalPartnerId;
	}

}
