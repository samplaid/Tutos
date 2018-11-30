package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Accounting NAV Entity
 * 
 * @author xqt5q
 *
 */
@Table(name = "ACCOUNTING_NAV")
@Entity
public class AccountingNavEntity implements Serializable {

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -8798542751194224509L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NAV_ACCOUNTING_ID")
	private Long id;

	@Column(name = "FUND_ID")
	private String fundId;

	@Column(name = "ISIN_CODE")
	private String isinCode;

	@Column(name = "NAV")
	private BigDecimal nav;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "NAV_DT")
	@Temporal(TemporalType.DATE)
	private Date navDate;

	@Column(name = "CREATION_DT")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public BigDecimal getNav() {
		return nav;
	}

	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getNavDate() {
		return navDate;
	}

	public void setNavDate(Date navDate) {
		this.navDate = navDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

}
