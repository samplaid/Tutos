package lu.wealins.webia.core.service.validations;

import java.util.Collection;

public interface SendingRulesValidationService {

	void validate(String sendingRules, Integer firstPolicyHolderClientId, Collection<String> errors);
}
