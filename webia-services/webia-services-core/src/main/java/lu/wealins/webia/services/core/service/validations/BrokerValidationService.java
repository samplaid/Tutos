package lu.wealins.webia.services.core.service.validations;

import java.math.BigDecimal;
import java.util.List;

import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;

public interface BrokerValidationService {

	void validateBrokerContractMngFees(PartnerFormDTO broker, BigDecimal contractMngtFeesPct, List<String> errors);

	void validateMandatoryBrokerContractMngFees(List<PolicyValuationHoldingDTO> holdings, List<String> errors);

	void validateMandatoryCommissionRates(List<PolicyValuationHoldingDTO> holdings, List<String> errors);

	void validateCommissionsRatesFeesRates(List<PolicyValuationHoldingDTO> holdings, List<String> errors);

	void validateMandatoryFieldsForBroker(PartnerFormDTO broker, List<String> errors);

	void validateMandatoryBroker(PartnerFormDTO broker, List<String> errors);

	void validateDifferentBroker(PartnerFormDTO brokerA, String partnerId, List<String> errors);

	void validateWealinsBroker(PartnerFormDTO broker, PartnerFormDTO businessIntroducer, PartnerFormDTO brokerContact, List<String> errors);

	void validateMandateTransmission(PartnerFormDTO broker, List<String> errors);
}
