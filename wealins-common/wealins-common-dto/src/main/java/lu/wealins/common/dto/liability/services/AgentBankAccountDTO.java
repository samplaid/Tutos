package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentBankAccountDTO {
	
	private Integer AgbId;
	private String agent0;
	private String accountName;
	private String bankName;
	private String bic;
	private String iban;
	private Integer status;
	private String accountCurrency;
	private String commPaymentCurrency;	
	
	
	
	public String getCommPaymentCurrency() {
		return commPaymentCurrency;
	}

	public void setCommPaymentCurrency(String commPaymentCurrency) {
		this.commPaymentCurrency = commPaymentCurrency;
	}

	public Integer getAgbId() {
		return AgbId;
	}

	public void setAgbId(Integer agbId) {
		AgbId = agbId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAgent0() {
		return agent0;
	}

	public void setAgent0(String agent0) {
		this.agent0 = agent0;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

}
