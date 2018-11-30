package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OptionValueEntity generated by hbm2java
 */
@Entity
@Table(name = "OPTION_VALUES"

)
public class OptionValueEntity implements java.io.Serializable {

	private int number;
	private String details;

	@Id
	@Column(name = "NUMBER", unique = true, nullable = false)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name = "DETAILS", length = 35)
	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
