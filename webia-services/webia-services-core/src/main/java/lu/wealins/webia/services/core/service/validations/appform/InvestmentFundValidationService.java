package lu.wealins.webia.services.core.service.validations.appform;

import java.util.List;

import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface InvestmentFundValidationService {

	void validateFundNotNull(AppFormDTO form, List<String> errors);
	void validateFundPartNotNull(AppFormDTO form, List<String> errors);
	void validateFundPartEq100(AppFormDTO form, List<String> errors);
	void validateFidAndFasHaveAmoutValuation(AppFormDTO form, List<String> errors);
}
