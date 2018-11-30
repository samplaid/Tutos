package lu.wealins.webia.services.core.service.validations.beneficiarychangeform;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;

public interface BeneficiaryClausesValidationService {
	
	<B extends BeneficiaryFormDTO, C extends BenefClauseFormDTO> List<String> validateClausesRules(Collection<B> beneficiaries, Collection<C> clauses);

}
