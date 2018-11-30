package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.AccountingPeriodDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccountingPeriodResponse {

	private AccountingPeriodDTO accountingPeriod;

	public AccountingPeriodDTO getAccountingPeriod() {
		return accountingPeriod;
	}

	public void setAccountingPeriod(AccountingPeriodDTO accountingPeriod) {
		this.accountingPeriod = accountingPeriod;
	}

}
