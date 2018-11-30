package lu.wealins.webia.services.core.service.validations;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;

public interface FormDataRetriever<T extends FormDataDTO> {

	@SuppressWarnings("unchecked")
	default T getFormData(StepDTO step) {
		return (T) step.getFormData();
	}

}
