package lu.wealins.common.dto.liability.services;

public class PolicyTransactionsHistoryDetailsInputDTO {
	private String policyId;
	private PolicyTransactionsHistoryDTO transaction;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public PolicyTransactionsHistoryDTO getTransaction() {
		return transaction;
	}

	public void setTransaction(PolicyTransactionsHistoryDTO transaction) {
		this.transaction = transaction;
	}
}
