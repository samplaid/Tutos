package lu.wealins.webia.services.core.service.validations.appform;

import java.math.BigDecimal;
import java.util.List;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;

public interface PaymentValidationService {

	void validateSubscriptionMandatoryFields(AppFormDTO appForm, List<String> errors);

	void validatePaymentTransferRule(AppFormDTO appForm, List<String> errors);

	void validateEntryFees(AppFormDTO appForm, List<String> errors);

	void validateMaxFeesPercentage(BigDecimal value, String name, List<String> errors);

	void validateMinFeesAmount(BigDecimal value, String name, List<String> errors);

	void validateBrokerEntryFees(PartnerFormDTO broker, BigDecimal entryFeesPct, BigDecimal entryFeesAmt, List<String> errors);

	void validateEffectDate(AppFormDTO appForm, List<String> errors);

	void validatePremiumCountry(AppFormDTO appForm, List<String> errors);

	void validatePremiumAmount(AppFormDTO appForm, List<String> errors);

	void validateAnalysisMandatoryFields(AppFormDTO appForm, List<String> errors);

	void validateRegistrationMandatoryFields(AppFormDTO appForm, List<String> errors);
}
