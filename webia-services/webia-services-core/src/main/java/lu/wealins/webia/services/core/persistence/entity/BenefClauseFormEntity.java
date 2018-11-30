package lu.wealins.webia.services.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BENEF_CLAUSE_FORM")
public class BenefClauseFormEntity extends AuditingEntity {

	private static final long serialVersionUID = 5010624915680344299L;

	@Id
	@Column(name = "BENEF_CLAUSE_FORM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer benefClauseFormId;
	
	@Column(name = "FORM_ID")
	private Integer formId;
	
	@Column(name = "CLAUSE_FORM_TP")
	private String clauseFormTp;
	
	@Column(name = "CLAUSE_TP")
	private String clauseTp;
	
	@Column(name = "CLAUSE_CD")
	private String clauseCd;
	
	@Column(name = "RANK_NUMBER")
	private Integer rankNumber;
	
	@Column(name = "CLAUSE_TEXT")
	private String clauseText;

	public Integer getBenefClauseFormId() {
		return benefClauseFormId;
	}

	public void setBenefClauseFormId(Integer benefClauseFormId) {
		this.benefClauseFormId = benefClauseFormId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getClauseFormTp() {
		return clauseFormTp;
	}

	public void setClauseFormTp(String clauseFormTp) {
		this.clauseFormTp = clauseFormTp;
	}

	public String getClauseTp() {
		return clauseTp;
	}

	public void setClauseTp(String clauseTp) {
		this.clauseTp = clauseTp;
	}

	public String getClauseCd() {
		return clauseCd;
	}

	public void setClauseCd(String clauseCd) {
		this.clauseCd = clauseCd;
	}

	public Integer getRankNumber() {
		return rankNumber;
	}

	public void setRankNumber(Integer rankNumber) {
		this.rankNumber = rankNumber;
	}

	public String getClauseText() {
		return clauseText;
	}

	public void setClauseText(String clauseText) {
		this.clauseText = clauseText;
	}
}
