package lu.wealins.webia.core.service.lps;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.FormDataDTO;

public interface CommonLpsUtilityService<T extends FormDataDTO> {
	void updateCheckSteps(Collection<CheckStepDTO> checkSteps, T formData);
}
