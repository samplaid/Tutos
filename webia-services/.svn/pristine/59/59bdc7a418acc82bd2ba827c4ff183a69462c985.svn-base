package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Accounting Period Entity
 * 
 * @author xqt5q
 *
 */
@Table(name = "ACCOUNTING_PERIOD")
@Entity
public class AccountingPeriodEntity implements Serializable {

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -8798542751194224509L;

	@Column(name = "ACCOUNTING_DT")
	@Temporal(TemporalType.DATE)
	@Id
	private Date accountingDate;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "EFFECTIVE_START_DT")
	@Temporal(TemporalType.DATE)
	private Date effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DT")
	@Temporal(TemporalType.DATE)
	private Date effectiveEndDate;

	public Date getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(Date accountingDate) {
		this.accountingDate = accountingDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

}
