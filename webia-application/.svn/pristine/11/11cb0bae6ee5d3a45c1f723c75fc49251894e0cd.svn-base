package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.validations.WorkflowItemSpecific;
import lu.wealins.webia.core.service.lps.CommonLpsUtilityService;

public interface WebiaStepServiceByOperation<T extends FormDataDTO> extends WorkflowItemSpecific {

	default StepDTO update(StepDTO step) {
		return step;
	}

	/**
	 * Method called before performing the update. Default implementation removes the comment that are set to null and thus prevent them to be displayed
	 * 
	 * @param step the step
	 * @return the step
	 */
	default StepDTO preUpdate(StepDTO step) {

		step.getCheckSteps().stream().forEach(x -> {
			if (x.getCheck() != null && "Comment".equals(x.getCheck().getCheckDesc()) && x.getCheck().getCheckData() != null && x.getCheck().getCheckData().getDataValueText() == null) {
				x.getCheck().setCheckData(null);
			}
		});

		setupCheckDataForLps(step);

		return step;
	}

	default void setupCheckDataForLps(StepDTO step) {
		T formData = getFormData(step);
		if (formData == null) {
			return;
		}

		CommonLpsUtilityService<T> lpsUtilityService = getLpsUtilityService();
		if (lpsUtilityService != null) {
			lpsUtilityService.updateCheckSteps(step.getCheckSteps(), formData);
		}
	}

	default CommonLpsUtilityService<T> getLpsUtilityService() {
		return null;
	}

	default StepDTO complete(StepDTO step) {
		return step;
	}

	default StepDTO postComplete(StepDTO step) {
		return step;
	}

	@SuppressWarnings("unchecked")
	default T getFormData(StepDTO step) {
		return (T) step.getFormData();
	}
}
