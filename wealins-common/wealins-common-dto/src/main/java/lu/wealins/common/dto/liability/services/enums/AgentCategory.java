package lu.wealins.common.dto.liability.services.enums;

import java.util.HashSet;
import java.util.Set;

public enum AgentCategory {

	ASSET_MANAGER("AM"),
	BROKER("BK"),
	DEPOSIT_BANK("DB"),
	DISTRIBUTION_COMPANY("DC"),
	PRESTATION_SERVICE_INVEST("PSI"),
	INDEPENDENT_FINANCIAL_INTERM("IFI"),
	WEALINS_SALES_PERSON("FS"),
	INTRODUCER("IN"),
	PERSON_CONTACT("PR"),
	SUB_BROKER("SB");

	private String category;

	private static Set<String> categories = new HashSet<>();

	static {
		for (AgentCategory agentCategory : AgentCategory.values()) {
			categories.add(agentCategory.getCategory());
		}
	}

	private AgentCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public static Set<String> getCategories() {
		return categories;
	}

	public static AgentCategory toAgentCategory(int policyAgentShareType, String agentCategory) {

		if (PolicyAgentShareType.ADVISOR_FEES.getType() == policyAgentShareType) {
			if (AgentCategory.BROKER.getCategory().equalsIgnoreCase((agentCategory))) {
				return AgentCategory.BROKER;
			} else if (AgentCategory.SUB_BROKER.getCategory().equalsIgnoreCase((agentCategory))) {
				return AgentCategory.SUB_BROKER;
			}
		}

		if (PolicyAgentShareType.ADDL_ADVISOR_FEE.getType() == policyAgentShareType) {
			return AgentCategory.INTRODUCER;
		}

		if (PolicyAgentShareType.FISA_SALES_PERSON.getType() == policyAgentShareType && AgentCategory.WEALINS_SALES_PERSON.getCategory().equalsIgnoreCase((agentCategory))) {
			return WEALINS_SALES_PERSON;
		}

		if (PolicyAgentShareType.SALES_REPRESENTATIVE.getType() == policyAgentShareType && AgentCategory.PERSON_CONTACT.getCategory().equalsIgnoreCase((agentCategory))) {
			return PERSON_CONTACT;
		}

		throw new IllegalStateException("Unknown agent category mapped with policy agent share type " + policyAgentShareType + " et agent category " + agentCategory + ".");
	}
}
