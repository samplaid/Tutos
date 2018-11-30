package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchTransactionTaxesResponse {

	private List<TransactionTaxDTO> transactionTaxList = new ArrayList<>();

	/**
	 * @return the transactionTaxList
	 */
	public List<TransactionTaxDTO> getTransactionTaxList() {
		return transactionTaxList;
	}

	/**
	 * @param transactionTaxList the transactionTaxList to set
	 */
	public void setTransactionTaxList(List<TransactionTaxDTO> transactionTaxList) {
		this.transactionTaxList = transactionTaxList;
	}
	
	
}
