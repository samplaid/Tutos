package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyHolderFormDTO extends ClientFormDTO {

	private Boolean usufructuary;
	private Boolean bareOwner;
	private Boolean deathSuccessor;
	private Boolean lifeSuccessor;

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

}
