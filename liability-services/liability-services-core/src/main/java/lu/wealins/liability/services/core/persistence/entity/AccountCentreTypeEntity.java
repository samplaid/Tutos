package lu.wealins.liability.services.core.persistence.entity;
// Generated Dec 1, 2016 12:16:30 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * AccountCentreTypeEntity generated by hbm2java
 */
@Entity
@Table(name = "ACCOUNT_CENTRE_TYPES"

)
public class AccountCentreTypeEntity implements java.io.Serializable {

	private int actId;
	private Set<AccountCentreEntity> accountCentres = new HashSet<AccountCentreEntity>(0);

	@Id
	@Column(name = "ACT_ID", unique = true, nullable = false)
	public int getActId() {
		return this.actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountCentreType")
	public Set<AccountCentreEntity> getAccountCentres() {
		return this.accountCentres;
	}

	public void setAccountCentres(Set<AccountCentreEntity> accountCentres) {
		this.accountCentres = accountCentres;
	}

}
