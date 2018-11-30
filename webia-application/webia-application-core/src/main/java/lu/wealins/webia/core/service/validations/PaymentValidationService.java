package lu.wealins.webia.core.service.validations;

import java.util.List;

import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface PaymentValidationService {

	void validatePremiumRange(AppFormDTO appForm, List<String> errors);
}
