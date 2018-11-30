package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "FUND_FORM")
public class FundFormEntity extends AuditingEntity {

	private static final long serialVersionUID = 2187372459171456555L;

	@Id
	@Column(name = "FUND_FORM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fundFormId;

	@Column(name = "FORM_ID")
	private Integer formId;

	@Column(name = "FUND_TP")
	private String fundTp;

	@Column(name = "FUND_ID")
	private String fundId;

	@Column(name = "SPLIT")
	private BigDecimal split;

	@Column(name = "IS_CASH_FUND_ACCOUNT")
	private Boolean isCashFundAccount;
	
	@Column(name = "VALUATION_AMT")
	private BigDecimal valuationAmt;

	/**
	 * @return the valuationAmt
	 */
	public BigDecimal getValuationAmt() {
		return valuationAmt;
	}

	/**
	 * @param valuationAmt the valuationAmt to set
	 */
	public void setValuationAmt(BigDecimal valuationAmt) {
		this.valuationAmt = valuationAmt;
	}

	@OneToMany
	@JoinColumns({
	    @JoinColumn(name="FUND_ID", referencedColumnName = "FUND_ID" ),
	    @JoinColumn(name="FORM_ID", referencedColumnName = "FORM_ID")
	})
	@OrderBy("cashFundFormId")
	private Collection<EncashmentFundFormEntity> encashmentFundForms = new ArrayList<>();

	public Integer getFundFormId() {
		return fundFormId;
	}

	public void setFundFormId(Integer fundFormId) {
		this.fundFormId = fundFormId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getFundTp() {
		return fundTp;
	}

	public void setFundTp(String fundTp) {
		this.fundTp = fundTp;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public BigDecimal getSplit() {
		return split;
	}

	public void setSplit(BigDecimal split) {
		this.split = split;
	}
	public Boolean getIsCashFundAccount() {
		return isCashFundAccount;
	}

	public void setIsCashFundAccount(Boolean isCashFundAccount) {
		this.isCashFundAccount = isCashFundAccount;
	}

	public Collection<EncashmentFundFormEntity> getEncashmentFundForms() {
		return encashmentFundForms;
	}

	public void setEncashmentFundForms(Collection<EncashmentFundFormEntity> encashmentFundForms) {
		this.encashmentFundForms = encashmentFundForms;
	}

}
