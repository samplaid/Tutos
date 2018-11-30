package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrokerChangeDTO extends AmendmentDTO {

	private String brokerRefContract;
	private PolicyAgentShareDTO broker;
	private PolicyAgentShareDTO subBroker;
	private PolicyAgentShareDTO brokerContact;
	private AgentLightDTO mailToAgent;
	// sending rules
	private UoptDetailDTO category;
	private PolicyValuationDTO policyValuation;

	public String getBrokerRefContract() {
		return brokerRefContract;
	}

	public void setBrokerRefContract(String brokerRefContract) {
		this.brokerRefContract = brokerRefContract;
	}

	public PolicyAgentShareDTO getBroker() {
		return broker;
	}

	public void setBroker(PolicyAgentShareDTO broker) {
		this.broker = broker;
	}

	public PolicyAgentShareDTO getSubBroker() {
		return subBroker;
	}

	public void setSubBroker(PolicyAgentShareDTO subBroker) {
		this.subBroker = subBroker;
	}

	public AgentLightDTO getMailToAgent() {
		return mailToAgent;
	}

	public void setMailToAgent(AgentLightDTO mailToAgent) {
		this.mailToAgent = mailToAgent;
	}

	public UoptDetailDTO getCategory() {
		return category;
	}

	public void setCategory(UoptDetailDTO category) {
		this.category = category;
	}

	public PolicyValuationDTO getPolicyValuation() {
		return policyValuation;
	}

	public void setPolicyValuation(PolicyValuationDTO policyValuation) {
		this.policyValuation = policyValuation;
	}

	public PolicyAgentShareDTO getBrokerContact() {
		return brokerContact;
	}

	public void setBrokerContact(PolicyAgentShareDTO brokerContact) {
		this.brokerContact = brokerContact;
	}

}
