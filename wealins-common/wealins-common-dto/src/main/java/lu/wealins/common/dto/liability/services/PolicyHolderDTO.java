package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyHolderDTO extends ClientDTO {

	private String polId;
	private BigDecimal percentageSplit;
	private Boolean usufructuary;
	private Boolean bareOwner;
	private Boolean deathSuccessor;
	private Boolean lifeSuccessor;
	// Represents the CliPolRelation type
	private Integer displayNumber;

	public String getPolId() {
		return polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	public BigDecimal getPercentageSplit() {
		return percentageSplit;
	}

	public void setPercentageSplit(BigDecimal percentageSplit) {
		this.percentageSplit = percentageSplit;
	}

	public Boolean getUsufructuary() {
		return usufructuary;
	}

	public void setUsufructuary(Boolean usufructuary) {
		this.usufructuary = usufructuary;
	}

	public Boolean getBareOwner() {
		return bareOwner;
	}

	public void setBareOwner(Boolean bareOwner) {
		this.bareOwner = bareOwner;
	}

	public Boolean getDeathSuccessor() {
		return deathSuccessor;
	}

	public void setDeathSuccessor(Boolean deathSuccessor) {
		this.deathSuccessor = deathSuccessor;
	}

	public Boolean getLifeSuccessor() {
		return lifeSuccessor;
	}

	public void setLifeSuccessor(Boolean lifeSuccessor) {
		this.lifeSuccessor = lifeSuccessor;
	}

	/**
	 * @return the displayNumber
	 */
	public Integer getDisplayNumber() {
		return displayNumber;
	}

	/**
	 * @param displayNumber the displayNumber to set
	 */
	public void setDisplayNumber(Integer displayNumber) {
		this.displayNumber = displayNumber;
	}
}
