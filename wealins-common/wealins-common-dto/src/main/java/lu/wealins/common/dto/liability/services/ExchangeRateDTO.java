package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateDTO {

	private String xrsId;
	private String fromCurrency;
	private String toCurrency;
	private Date date0;
	private BigDecimal midRate;
	private BigDecimal buyRate;
	private BigDecimal sellRate;
	private BigDecimal companyRate;
	private Integer reciprocal;
	private int status;
	public String getXrsId() {
		return xrsId;
	}
	public void setXrsId(String xrsId) {
		this.xrsId = xrsId;
	}
	public String getFromCurrency() {
		return fromCurrency;
	}
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	public Date getDate0() {
		return date0;
	}
	public void setDate0(Date date0) {
		this.date0 = date0;
	}
	public BigDecimal getMidRate() {
		return midRate;
	}
	public void setMidRate(BigDecimal midRate) {
		this.midRate = midRate;
	}
	public BigDecimal getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(BigDecimal buyRate) {
		this.buyRate = buyRate;
	}
	public BigDecimal getSellRate() {
		return sellRate;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}
	public BigDecimal getCompanyRate() {
		return companyRate;
	}
	public void setCompanyRate(BigDecimal companyRate) {
		this.companyRate = companyRate;
	}
	public Integer getReciprocal() {
		return reciprocal;
	}
	public void setReciprocal(Integer reciprocal) {
		this.reciprocal = reciprocal;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
}
