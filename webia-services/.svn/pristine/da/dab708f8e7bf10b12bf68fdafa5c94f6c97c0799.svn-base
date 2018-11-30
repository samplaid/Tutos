package lu.wealins.webia.services.core.service.validations;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;

public interface BeneficiaryValidationService {

	/**
	 * Validate the beneficiaries part data.
	 * @param beneficiaries
	 * @return
	 */
	List<String> checkPart(Collection<BeneficiaryFormDTO> beneficiaries);

	/**
	 * Assert the beneficiary ranks not to be null.
	 * 
	 * @param beneficiaries a set of beneficiary
	 * @param errors Empty if the condition is met.
	 */
	void validateRankNotNull(Collection<BeneficiaryFormDTO> beneficiaries, List<String> errors);

	/**
	 * Assert the beneficiary parts not to be null.
	 * 
	 * @param beneficiaries a set of beneficiary
	 * @param errors Empty if the condition is met.
	 */
	void validatePartNotNull(Collection<BeneficiaryFormDTO> beneficiaries, List<String> errors);

	void validateConstraintRankNumberClause(Collection<BenefClauseFormDTO> clausesForm, List<String> errors);

	void validatePartNotNullFromRank(Collection<BeneficiaryFormDTO> beneficiaries, Integer rank, List<String> errors);
}
