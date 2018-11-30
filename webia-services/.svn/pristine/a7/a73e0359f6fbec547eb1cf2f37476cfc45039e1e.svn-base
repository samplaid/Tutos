package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_FORM")
public class ClientFormEntity extends AuditingEntity {

	private static final long serialVersionUID = 4182591546388890890L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENT_FORM_ID")
	private Integer clientFormId;
	@Column(name = "FORM_ID")
	private Integer formId;
	@Column(name = "CLIENT_RELATION_TP")
	private Integer clientRelationTp;
	@Column(name = "CLIENT_ID")
	private Integer clientId;
	@Column(name = "RANK_NUMBER")
	private Integer rankNumber;
	@Column(name = "SPLIT")
	private BigDecimal split;
	@Column(name = "AGE_RATING")
	private Integer ageRating;
	@Column(name = "ADDN_RPM")
	private BigDecimal addnRpm;
	@Column(name = "ADDN_FACTOR")
	private BigDecimal addnFactor;
	@Column(name = "IS_EQUAL_PARTS")
	private Boolean isEqualParts;

	/**
	 * @return the equalParts
	 */
	public Boolean getIsEqualParts() {
		return isEqualParts;
	}

	/**
	 * @param equalParts the equalParts to set
	 */
	public void setIsEqualParts(Boolean isEqualParts) {
		this.isEqualParts = isEqualParts;
	}

	public Integer getClientFormId() {
		return clientFormId;
	}

	public void setClientFormId(Integer clientFormid) {
		this.clientFormId = clientFormid;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getClientRelationTp() {
		return clientRelationTp;
	}

	public void setClientRelationTp(Integer clientRelationTp) {
		this.clientRelationTp = clientRelationTp;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getRankNumber() {
		return rankNumber;
	}

	public void setRankNumber(Integer rankNumber) {
		this.rankNumber = rankNumber;
	}

	public BigDecimal getSplit() {
		return split;
	}

	public void setSplit(BigDecimal split) {
		this.split = split;
	}

	public Integer getAgeRating() {
		return ageRating;
	}

	public void setAgeRating(Integer ageRating) {
		this.ageRating = ageRating;
	}

	public BigDecimal getAddnRpm() {
		return addnRpm;
	}

	public void setAddnRpm(BigDecimal addnRpm) {
		this.addnRpm = addnRpm;
	}

	public BigDecimal getAddnFactor() {
		return addnFactor;
	}

	public void setAddnFactor(BigDecimal addnFactor) {
		this.addnFactor = addnFactor;
	}

}
