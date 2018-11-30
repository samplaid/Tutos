package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PATTERN_ACCOUNT_ROOT")
public class PatternAccountRootEntity implements Serializable {

	private static final long serialVersionUID = 3074761784807631254L;
	
	private Integer patternId;
	private String accountBic;
	private String exemple;
	private String pattern;

	/**
	 * @return the patternId
	 */
	@Id
	@Column(name = "PATTERN_ID", nullable = false)
	public Integer getPatternId() {
		return patternId;
	}

	/**
	 * @param patternId the patternId to set
	 */
	public void setPatternId(Integer patternId) {
		this.patternId = patternId;
	}

	/**
	 * @return the accountBic
	 */
	@Column(name = "ACCOUNT_BIC", nullable = false)
	public String getAccountBic() {
		return accountBic;
	}

	/**
	 * @param accountBic the accountBic to set
	 */
	public void setAccountBic(String accountBic) {
		this.accountBic = accountBic;
	}

	/**
	 * @return the exemple
	 */
	@Column(name = "EXEMPLE", nullable = true)
	public String getExemple() {
		return exemple;
	}

	/**
	 * @param exemple the exemple to set
	 */
	public void setExemple(String exemple) {
		this.exemple = exemple;
	}

	/**
	 * @return the pattern
	 */
	@Column(name = "PATTERN", nullable = true)
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
