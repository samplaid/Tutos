package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ExchangeRateEntity generated by hbm2java
 */
@Entity
@Table(name = "EXCHANGE_RATES"

)
public class ExchangeRateEntity implements java.io.Serializable {

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
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String createdProcess;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String modifyProcess;

	@Id
	@Column(name = "XRS_ID", unique = true, nullable = false, length = 16)
	public String getXrsId() {
		return this.xrsId;
	}

	public void setXrsId(String xrsId) {
		this.xrsId = xrsId;
	}

	@Column(name = "FROM_CURRENCY", nullable = false, length = 4)
	public String getFromCurrency() {
		return this.fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	@Column(name = "TO_CURRENCY", nullable = false, length = 4)
	public String getToCurrency() {
		return this.toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE0", nullable = false, length = 23)
	public Date getDate0() {
		return this.date0;
	}

	public void setDate0(Date date0) {
		this.date0 = date0;
	}

	@Column(name = "MID_RATE", precision = 15, scale = 7)
	public BigDecimal getMidRate() {
		return this.midRate;
	}

	public void setMidRate(BigDecimal midRate) {
		this.midRate = midRate;
	}

	@Column(name = "BUY_RATE", precision = 15, scale = 7)
	public BigDecimal getBuyRate() {
		return this.buyRate;
	}

	public void setBuyRate(BigDecimal buyRate) {
		this.buyRate = buyRate;
	}

	@Column(name = "SELL_RATE", precision = 15, scale = 7)
	public BigDecimal getSellRate() {
		return this.sellRate;
	}

	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}

	@Column(name = "COMPANY_RATE", precision = 15, scale = 7)
	public BigDecimal getCompanyRate() {
		return this.companyRate;
	}

	public void setCompanyRate(BigDecimal companyRate) {
		this.companyRate = companyRate;
	}

	@Column(name = "RECIPROCAL")
	public Integer getReciprocal() {
		return this.reciprocal;
	}

	public void setReciprocal(Integer reciprocal) {
		this.reciprocal = reciprocal;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false, length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "CREATED_PROCESS", length = 12)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "MODIFY_BY", length = 5)
	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 23)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 23)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "MODIFY_PROCESS", length = 12)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

}
