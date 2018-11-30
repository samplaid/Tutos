package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lu.wealins.common.dto.webia.services.enums.FundTransactionInputType;

@Entity
@Table(name = "FUND_TRANSACTION_FORM")
public class FundTransactionFormEntity extends AuditingEntity {
	
	private static final long serialVersionUID = -5123093283413231569L;

	@Id
	@Column(name = "FUND_TRANSACTION_FORM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fundTransactionFormId;

	@Column(name = "FUND_ID")
	private String fundId;

	@Column(name = "FUND_TP")
	private String fundTp;

	@Column(name = "INPUT_TYPE")
	@Enumerated(EnumType.STRING)
	private FundTransactionInputType inputType;

	@Column(name = "SPLIT")
	private BigDecimal split;

	@Column(name = "PERCENTAGE")
	private BigDecimal percentage;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "UNITS")
	private BigDecimal units;

	@Column(name = "VALUATION_AMT")
	private BigDecimal valuationAmt;

	@Column(name = "CLOSURE")
	private Boolean closure;

	public Boolean getClosure() {
		return closure;
	}

	public void setClosure(Boolean closure) {
		this.closure = closure;
	}

	public Long getFundTransactionFormId() {
		return fundTransactionFormId;
	}

	public void setFundTransactionFormId(Long fundTransactionFormId) {
		this.fundTransactionFormId = fundTransactionFormId;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public FundTransactionInputType getInputType() {
		return inputType;
	}

	public void setInputType(FundTransactionInputType inputType) {
		this.inputType = inputType;
	}

	public BigDecimal getSplit() {
		return split;
	}

	public void setSplit(BigDecimal split) {
		this.split = split;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public BigDecimal getValuationAmt() {
		return valuationAmt;
	}

	public void setValuationAmt(BigDecimal valuationAmt) {
		this.valuationAmt = valuationAmt;
	}

	public String getFundTp() {
		return fundTp;
	}

	public void setFundTp(String fundTp) {
		this.fundTp = fundTp;
	}
}
