package lu.wealins.webia.services.core.service.validations.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import lu.wealins.common.dto.webia.services.AmendmentFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.validations.ValidationStepService;
import lu.wealins.webia.services.core.service.validations.FormDataValidationService;

public abstract class AmendmentFormValidationService<T extends AmendmentFormDTO> implements ValidationStepService, FormDataValidationService<T> {

	public static final String CHANGE_DATE_MANDATORY = "The date of change is mandatory";
	public static final String CHANGE_DATE_FUTURE = "The date of change can't be in the future";

	private void validateEffectiveDateSave(T amendment, List<String> errors) {
		if (amendment.getChangeDate() == null) {
			errors.add(CHANGE_DATE_MANDATORY);
		}
	}

	private void validateEffectiveDateComplete(T amendment, List<String> errors) {
		if (amendment.getChangeDate().after(new Date())) {
			errors.add(CHANGE_DATE_FUTURE);
		}
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = new ArrayList<>();

		T formData = getFormData(step);

		validateEffectiveDateComplete(formData, errors);

		return errors;
	}

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		List<String> errors = new ArrayList<>();

		T formData = getFormData(step);

		validatePolicyPresence(formData, errors);

		if (CollectionUtils.isNotEmpty(errors)) {
			return errors;
		}

		validateEffectiveDateSave(formData, errors);

		return errors;
	}

}
