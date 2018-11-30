package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lu.wealins.common.dto.webia.services.enums.EncashmentFormFundStatus;

@Entity
@Table(name = "ENCASHMENT_FUND_FORM")
public class EncashmentFundFormEntity extends AuditingEntity {

	private static final long serialVersionUID = -7883348457862589852L;
	
	@Id
	@Column(name = "CASH_FUND_FORM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cashFundFormId;
	
	@Column(name = "FORM_ID")
	private Integer formId;
	
	@Column(name = "FUND_ID")
	private String fundId;
	
	@Column(name = "CASH_AMT")
	private BigDecimal cashAmt;
	
	@Column(name = "CASH_DT")
	private Date cashDt;
	
	@Column(name = "CASH_CURRENCY")
	private String cashCurrency;
	
	@Column(name = "CASH_STATUS")
	@Enumerated(EnumType.STRING)
	private EncashmentFormFundStatus cashStatus;
	
	public Integer getCashFundFormId() {
		return cashFundFormId;
	}

	public void setCashFundFormId(Integer cashFundFormId) {
		this.cashFundFormId = cashFundFormId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public BigDecimal getCashAmt() {
		return cashAmt;
	}

	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}

	public Date getCashDt() {
		return cashDt;
	}

	public void setCashDt(Date cashDt) {
		this.cashDt = cashDt;
	}

	public String getCashCurrency() {
		return cashCurrency;
	}

	public void setCashCurrency(String cashCurrency) {
		this.cashCurrency = cashCurrency;
	}

	public EncashmentFormFundStatus getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(EncashmentFormFundStatus cashStatus) {
		this.cashStatus = cashStatus;
	}

}
