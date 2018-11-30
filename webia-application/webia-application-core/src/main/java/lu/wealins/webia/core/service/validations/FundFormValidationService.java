package lu.wealins.webia.core.service.validations;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;

public interface FundFormValidationService {

	void validateExternalFunds(Collection<FundFormDTO> fundForms, PartnerFormDTO broker, List<String> errors);

	void validateFIDorFASForDocumentation(Collection<FundFormDTO> fundForms, List<String> errors);

	void validateFundForDocumentation(String fdsId, List<String> errors);
}
