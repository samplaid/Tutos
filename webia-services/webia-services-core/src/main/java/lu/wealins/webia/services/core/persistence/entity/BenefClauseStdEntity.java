package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "BENEF_CLAUSE_STD")
@IdClass(BenefClauseStdId.class)
public class BenefClauseStdEntity implements Serializable {

	private static final long serialVersionUID = -6873763750007507828L;

	@Id
	@Column(name = "BENEF_CLAUSE_CD")
	private String benefClauseCd;

	@Id
	@Column(name = "LANG_CD")
	private String langCd;

	@Column(name = "PRODUCT_CD")
	private String productCd;

	@Column(name = "SORT_NUMBER")
	private Integer sortNumber;

	@Column(name = "BENEF_CLAUSE_TEXT")
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
