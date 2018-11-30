package lu.wealins.common.dto.webia.services;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurrenderTransferFormDataDTO {

	private TransferDTO cashTransfer;
	private Collection<TransferCandidate> transferCandidates;

	public TransferDTO getCashTransfer() {
		return cashTransfer;
	}

	public void setCashTransfer(TransferDTO cashTransfer) {
		this.cashTransfer = cashTransfer;
	}

	public Collection<TransferCandidate> getTransferCandidates() {
		return transferCandidates;
	}

	public void setTransferCandidates(Collection<TransferCandidate> transferCandidates) {
		this.transferCandidates = transferCandidates;
	}
}
