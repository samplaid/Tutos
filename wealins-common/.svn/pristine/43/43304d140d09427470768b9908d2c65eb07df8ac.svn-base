package lu.wealins.common.dto.liability.services.enums;

public enum PolicyAgentShareType {

	// PAS type as defined in table OPTION_DETAILS (PAS_TYPE)
	INITIAL_RENEWAL(1), OTHER(2), INITIAL_COMM_FEE(3), RENEWAL_COMM_FEE(4), ADVISOR_FEES(5), SALES_REPRESENTATIVE(6), WITHDRAWAL_FEE(7), SWITCH_FEE(8), SURRENDER_FEE(9), ADDL_ADVISOR_FEE(
			10), FINANCIAL_COMM(11), FISA_SALES_PERSON(12), FUND_CHGS_COMM(13), CUSTODY_FEES_COMM(14);


	private int type;

	private PolicyAgentShareType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public static PolicyAgentShareType toPolicyAgentShareType(String partnerCategory) {
		if (AgentCategory.BROKER.getCategory().equalsIgnoreCase(partnerCategory)) {
			return PolicyAgentShareType.ADVISOR_FEES;
		} else if (AgentCategory.SUB_BROKER.getCategory().equalsIgnoreCase(partnerCategory)) {
			return PolicyAgentShareType.ADVISOR_FEES;
		} else if (AgentCategory.PERSON_CONTACT.getCategory().equalsIgnoreCase(partnerCategory)) {
			return PolicyAgentShareType.SALES_REPRESENTATIVE;
		} else if (AgentCategory.INTRODUCER.getCategory().equalsIgnoreCase(partnerCategory)) {
			return PolicyAgentShareType.ADDL_ADVISOR_FEE;
		}
		throw new IllegalStateException("Cannot convert partner category " + partnerCategory + " to policy agent share type.");
	}

	public PolicyAgentShareType toPolicyAgentShareType(int type) {
		PolicyAgentShareType enumResult = null;

		for (PolicyAgentShareType eachType : values()) {
			if (eachType.getType() == type) {
				enumResult = eachType;
				break;
			}
		}

		return enumResult;
	}

}
