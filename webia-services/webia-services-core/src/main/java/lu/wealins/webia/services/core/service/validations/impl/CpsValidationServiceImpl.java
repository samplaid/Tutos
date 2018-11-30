package lu.wealins.webia.services.core.service.validations.impl;

import static lu.wealins.webia.services.core.service.validations.GenericMessageConstants.CPS1_AND_CPS2_MUST_BE_DIFFERENT;
import static lu.wealins.webia.services.core.service.validations.GenericMessageConstants.CPS_1_IS_MANDATORY;
import static lu.wealins.webia.services.core.service.validations.GenericMessageConstants.CPS_2_IS_MANDATORY;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lu.wealins.webia.services.core.service.validations.CpsValidationService;

@Service
public class CpsValidationServiceImpl implements CpsValidationService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.CpsValidationService#validateFirstCps(java.lang.String, java.util.List)
	 */
	@Override
	public void validateFirstCps(String firstCpsUser, List<String> errors) {
		if (StringUtils.isBlank(firstCpsUser)) {
			errors.add(CPS_1_IS_MANDATORY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.CpsValidationService#validateSecondCps(java.lang.String, java.util.List)
	 */
	@Override
	public void validateSecondCps(String secondCpsUser, List<String> errors) {
		if (StringUtils.isBlank(secondCpsUser)) {
			errors.add(CPS_2_IS_MANDATORY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.CpsValidationService#validateFourEyes(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void validateFourEyes(String firstCpsUser, String secondCpsUser, List<String> errors) {

		if (StringUtils.isNotBlank(firstCpsUser) && firstCpsUser.equalsIgnoreCase(secondCpsUser)) {
			errors.add(CPS1_AND_CPS2_MUST_BE_DIFFERENT);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.validations.CpsValidationService#validateCpsUsers(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void validateCpsUsers(String firstCpsUser, String secondCpsUser, List<String> errors) {

		validateFirstCps(firstCpsUser, errors);
		validateSecondCps(secondCpsUser, errors);
		validateFourEyes(firstCpsUser, secondCpsUser, errors);
	}

}
