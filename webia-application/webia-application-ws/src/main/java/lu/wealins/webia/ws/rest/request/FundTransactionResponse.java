package lu.wealins.webia.ws.rest.request;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.liability.services.FundTransactionDTO;

/**
 * The document generation request
 * 
 * @author bqv55
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundTransactionResponse {

	private Collection<FundTransactionDTO> fundTransactions;

	public Collection<FundTransactionDTO> getFundTransactions() {
		return fundTransactions;
	}

	public void setFundTransactions(Collection<FundTransactionDTO> fundTransactions) {
		this.fundTransactions = fundTransactions;
	}

}
