package lu.wealins.webia.core.service.validations;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.InsuredFormDTO;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;

public interface ClientFormValidationService {

	void validatePolicyHolders(Collection<PolicyHolderFormDTO> policyHolders, List<String> errors);

	void validateInsureds(Collection<InsuredFormDTO> insureds, List<String> errors);

	void validateGdpr(Collection<InsuredFormDTO> insureds, List<String> errors);
}
