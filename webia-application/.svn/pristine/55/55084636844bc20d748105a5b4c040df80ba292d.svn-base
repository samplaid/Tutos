package lu.wealins.webia.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.webia.core.service.WebiaCheckStepService;

@Service
public class WebiaCheckStepServiceImpl implements WebiaCheckStepService {

	private static final String CHECK_CODE_CANNOT_BE_NULL = "CheckCode cannot be null";
	private static final String STEP_CANNOT_BE_NULL = "Step cannot be null";

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WebiaCheckStepService#getCheckStep(lu.wealins.webia.ws.rest.dto.StepDTO, java.lang.String)
	 */
	@Override
	public CheckStepDTO getCheckStep(StepDTO step, String checkCode) {
		Assert.notNull(step, STEP_CANNOT_BE_NULL);
		Assert.notNull(checkCode, CHECK_CODE_CANNOT_BE_NULL);

		return step.getCheckSteps().stream().filter(x -> x.getCheck() != null && checkCode.equals(x.getCheck().getCheckCode())).findFirst().orElse(null);
	}

}
