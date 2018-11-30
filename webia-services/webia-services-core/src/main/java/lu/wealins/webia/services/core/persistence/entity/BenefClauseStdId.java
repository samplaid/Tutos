package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

public class BenefClauseStdId implements Serializable {

	private static final long serialVersionUID = 182516310227933361L;

	private String benefClauseCd;

	private String langCd;

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

}
