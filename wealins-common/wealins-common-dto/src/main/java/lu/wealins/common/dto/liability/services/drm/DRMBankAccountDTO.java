package lu.wealins.common.dto.liability.services.drm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DRMBankAccountDTO {

	private String lissia_id_c;
	private String bk_bank_bka_bankaccounts_1_name;
	private String name;
	private String iban;
	private String bic_c;
	private String currency;
	private String deleted;
	private String description;
	private String owner;

	public String getLissia_id_c() {
		return lissia_id_c;
	}

	public void setLissia_id_c(String lissia_id_c) {
		this.lissia_id_c = lissia_id_c;
	}

	public String getBk_bank_bka_bankaccounts_1_name() {
		return bk_bank_bka_bankaccounts_1_name;
	}

	public void setBk_bank_bka_bankaccounts_1_name(String bk_bank_bka_bankaccounts_1_name) {
		this.bk_bank_bka_bankaccounts_1_name = bk_bank_bka_bankaccounts_1_name;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic_c() {
		return bic_c;
	}

	public void setBic_c(String bic_c) {
		this.bic_c = bic_c;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
