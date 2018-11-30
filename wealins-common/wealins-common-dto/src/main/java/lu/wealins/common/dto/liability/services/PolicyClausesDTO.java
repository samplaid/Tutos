package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.List;

public class PolicyClausesDTO {

	private List<PolicyBeneficiaryClauseDTO> death = new ArrayList<PolicyBeneficiaryClauseDTO>();

	private List<PolicyBeneficiaryClauseDTO> maturity = new ArrayList<PolicyBeneficiaryClauseDTO>();

	public List<PolicyBeneficiaryClauseDTO> getDeath() {
		return death;
	}

	public void setDeath(List<PolicyBeneficiaryClauseDTO> death) {
		this.death = death;
	}

	public List<PolicyBeneficiaryClauseDTO> getMaturity() {
		return maturity;
	}

	public void setMaturity(List<PolicyBeneficiaryClauseDTO> maturity) {
		this.maturity = maturity;
	}

}
