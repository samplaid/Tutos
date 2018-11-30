package lu.wealins.webia.ws.rest.request;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NordicValidationRequest {

	private int policyHolderSize;
	private int lives;
	private String productCountry;
	private Collection<BenefClauseFormDTO> clauses;

	public int getPolicyHolderSize() {
		return policyHolderSize;
	}

	public void setPolicyHolderSize(int policyHolderSize) {
		this.policyHolderSize = policyHolderSize;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public String getProductCountry() {
		return productCountry;
	}

	public void setProductCountry(String productCountry) {
		this.productCountry = productCountry;
	}

	public Collection<BenefClauseFormDTO> getClauses() {
		return clauses;
	}

	public void setClauses(Collection<BenefClauseFormDTO> clauses) {
		this.clauses = clauses;
	}

}
