package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data Transfer Object for transfering SasIsinEntity.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SasIsinDTO {

	// private Integer sasIsinId;

	private String isin;

	private String currency;

	private String statusCode;

	private String bankBic;

	private String fundTitle;

	// private Date creationDate;

	// public Integer getSasIsinId() {
	// return sasIsinId;
	// }
	//
	// public void setSasIsinId(Integer sasIsinId) {
	// this.sasIsinId = sasIsinId;
	// }

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getBankBic() {
		return bankBic;
	}

	public void setBankBic(String bankBic) {
		this.bankBic = bankBic;
	}

	public String getFundTitle() {
		return fundTitle;
	}

	public void setFundTitle(String fundTitle) {
		this.fundTitle = fundTitle;
	}

	// public Date getCreationDate() {
	// return creationDate;
	// }
	//
	// public void setCreationDate(Date creationDate) {
	// this.creationDate = creationDate;
	// }
}
