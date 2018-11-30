package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.enums.ClauseType;
import lu.wealins.common.dto.liability.services.enums.CountryCodeEnum;
import lu.wealins.common.dto.liability.services.enums.DeathCoverageLives;
import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.core.service.validations.DeathClausesValidationService;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;

/**
 * Implementation of {@link DeathClausesValidationService}.
 */
@Service
public class DeathClausesValidationServiceImpl implements DeathClausesValidationService {

	private static final String NORDIC_PRODUCT_SPECIFIC_DEATH_CLAUSE_MSG_MISSING = "The death clause provided for the nordic countries rule is missing or has been modified";

	private static final String RULES_2_POLICYHOLDERS_NORD = "RULES_2_POLICYHOLDERS_NORD";

	@Autowired
	ApplicationParameterService applicationParameterService;

	/**
	 * Implementation of the rule defined in KAN-133.
	 *
	 * @param appForm
	 *            the application form
	 * @param errors
	 */
	private void checkNordicRules(Integer policyHolders, String productCountry, Integer lives,
			Collection<BenefClauseFormDTO> benefClauses, List<String> errors) {

		// if product is from nordic countries and multiple insured are provided
		// and death coverage lives is at 'joint second death'
		if ((CountryCodeEnum.FINLAND.getCode().equals(productCountry)
				|| CountryCodeEnum.SWEDEN.getCode().equals(productCountry)) && policyHolders > 1
				&& DeathCoverageLives.JOINT_SECOND_DEATH.getCode().equals(lives)) {

			if (benefClauses.stream().filter(a -> Integer.valueOf(1).equals(a.getRankNumber())
					&& ClauseType.FREE.getValue().equals(a.getClauseTp())
					&& applicationParameterService.getStringValue(RULES_2_POLICYHOLDERS_NORD).equals(a.getClauseText()))
					.collect(Collectors.toList()).isEmpty()) {
				errors.add(NORDIC_PRODUCT_SPECIFIC_DEATH_CLAUSE_MSG_MISSING);
			}
		}
	}

	@Override
	public void validateDeathClauses(Integer policyHoldersSize, String productCountry, Integer lives,
			Collection<BenefClauseFormDTO> benefClauses, List<String> errors) {
		checkNordicRules(policyHoldersSize, productCountry, lives, benefClauses, errors);
	}
}
