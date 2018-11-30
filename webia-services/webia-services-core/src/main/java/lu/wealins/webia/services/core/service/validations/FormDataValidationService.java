package lu.wealins.webia.services.core.service.validations;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.validations.FormDataRetriever;

public interface FormDataValidationService<T extends FormDataDTO> extends FormDataRetriever<T> {

	public static final String POLICY_MANDATORY = "The policy is mandatory.";

	default void validatePolicyPresence(T stepNew, List<String> errors) {

		if (StringUtils.isBlank(stepNew.getPolicyId())) {
			errors.add(POLICY_MANDATORY);
		}
	}

}
