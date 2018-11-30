package lu.wealins.webia.core.service.validations.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.service.validations.CoverageValidationService;

@Service
public class CoverageValidationServiceImpl implements CoverageValidationService {

	@Autowired
	private LiabilityProductValueService productValueService;

	@Override
	public List<String> validateMaxMultiplierAlphaValue(AppFormDTO appForm, List<String> errors) {
		Integer deathCoverageTp = appForm.getDeathCoverageTp();
		if (deathCoverageTp != null && deathCoverageTp.intValue() == 14) {
			try {
				BigDecimal maxMultiplerAlphaValue = productValueService.getMaxMultiplerAlphaValue(appForm);

				if (maxMultiplerAlphaValue == null) {
					errors.add("No max multiplier alpha value that corresponds to the death coverage percentage"
							+ appForm.getDeathCoveragePct() + " can be found in product value.");
				} else if (maxMultiplerAlphaValue.compareTo(appForm.getDeathCoveragePct()) < 0) {
					errors.add("The death coverage percentage " + appForm.getDeathCoveragePct()
							+ " cannot be higher than " + maxMultiplerAlphaValue);
				}
			} catch (Exception e) {
				errors.add("Error occurred during the evalution of the product line's alpha value: " + e.getMessage());
			}
		}
		return errors;
	}

}
