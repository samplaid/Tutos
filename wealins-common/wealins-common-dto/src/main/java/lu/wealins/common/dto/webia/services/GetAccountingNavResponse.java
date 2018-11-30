package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.AccountingNavDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccountingNavResponse {


	private AccountingNavDTO accountingNav;

	public AccountingNavDTO getAccountingNav() {
		return accountingNav;
	}

	public void setAccountingNav(AccountingNavDTO accountingNav) {
		this.accountingNav = accountingNav;
	}

	

}
