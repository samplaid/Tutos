package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BenefClauseStdDTO {

	private String benefClauseCd;
	private String langCd;
	private String productCd;
	private Integer sortNumber;
	private String benefClauseText;

	public String getBenefClauseCd() {
		return benefClauseCd;
	}

	public void setBenefClauseCd(String benefClauseCd) {
		this.benefClauseCd = benefClauseCd;
	}

	public String getLangCd() {
		return langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public String getBenefClauseText() {
		return benefClauseText;
	}

	public void setBenefClauseText(String benefClauseText) {
		this.benefClauseText = benefClauseText;
	}

}
