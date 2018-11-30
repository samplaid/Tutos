/**
 * 
 */
package lu.wealins.webia.ws.rest.request;

import java.util.Date;

/**
 * @author lax
 *
 */
public class CustomPaymentsPk {

	private String swift;

	private Date paymentDate;

	private boolean isFid;

	private boolean isFisaDedicatedAccount;

	/**
	 * @return the swift
	 */
	public String getSwift() {
		return swift;
	}

	/**
	 * @param swift the swift to set
	 */
	public void setSwift(String swift) {
		this.swift = swift;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the isFid
	 */
	public boolean isFid() {
		return isFid;
	}

	/**
	 * @param isFid the isFid to set
	 */
	public void setFid(boolean isFid) {
		this.isFid = isFid;
	}

	/**
	 * @return the isFisaDedicatedAccount
	 */
	public boolean isFisaDedicatedAccount() {
		return isFisaDedicatedAccount;
	}

	/**
	 * @param isFisaDedicatedAccount the isFisaDedicatedAccount to set
	 */
	public void setFisaDedicatedAccount(boolean isFisaDedicatedAccount) {
		this.isFisaDedicatedAccount = isFisaDedicatedAccount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isFid ? 1231 : 1237);
		result = prime * result + (isFisaDedicatedAccount ? 1231 : 1237);
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + ((swift == null) ? 0 : swift.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomPaymentsPk other = (CustomPaymentsPk) obj;
		if (isFid != other.isFid)
			return false;
		if (isFisaDedicatedAccount != other.isFisaDedicatedAccount)
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (swift == null) {
			if (other.swift != null)
				return false;
		} else if (!swift.equals(other.swift))
			return false;
		return true;
	}

}
