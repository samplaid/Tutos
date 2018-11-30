/**
 * 
 */
package lu.wealins.liability.services.core.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XHOLIDAYS")
public class HolidayEntity {

	private Date date;
	private int dd;
	private int ddPrev;

	/**
	 * @return the date
	 */
	@Id
	@Column(name = "dt")
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the dd
	 */
	@Column(name = "dd")
	public int getDd() {
		return dd;
	}

	/**
	 * @param dd
	 *            the dd to set
	 */
	public void setDd(int dd) {
		this.dd = dd;
	}

	/**
	 * @return the ddPrev
	 */
	@Column(name = "dd_prev")
	public int getDdPrev() {
		return ddPrev;
	}

	/**
	 * @param ddPrev
	 *            the ddPrev to set
	 */
	public void setDdPrev(int ddPrev) {
		this.ddPrev = ddPrev;
	}
}
