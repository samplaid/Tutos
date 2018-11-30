package lu.wealins.webia.core.service;

import java.math.BigDecimal;

import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.DeathCoverageClausesDTO;

public interface LiabilityDeathCoverageService {

	/**
	 * Get death coverage clauses according to the product code.
	 * 
	 * @param productCd The product code.
	 * @return The death coverage clauses.
	 */
	DeathCoverageClausesDTO getDeathCoverageClauses(String productCd);

	/**
	 * Get the death coverage text for a given policy using webia application definition It search the death coverage subscribe in the policy (DTHCAL) and add the label from webia APPLI_PARAM
	 * 
	 * @param polId The policy Id
	 * @return the death coverage text.
	 */
	DeathCoverageClauseDTO getPolicyDeathCoverage(String polId);

	/**
	 * Get the value of the clause as set in the DB
	 * 
	 * @param productCd the product id
	 * @param code of the clause
	 * @param isStandard boolean to search in Standard or Alternative clauses
	 * @return the death coverage clause
	 */
	DeathCoverageClauseDTO getDeathCoverageClause(String productCd, String code, boolean isStandard);

	/**
	 * Convert decimal into percentage (ex : 0.01 => 101%)
	 * 
	 * @param value the coverage's product value
	 * @return a percentage above 100(%) or Zero
	 */
	BigDecimal coverageValueToPercent(BigDecimal value);

	/**
	 * Convert percentage value into decimal
	 * 
	 * - DTHFC2 control values are saved as decimal less than 1 (ex : 101% => 0.01) - DTHFAC control values are saved as decimal above 1 (ex : 101% => 1.01)
	 *
	 * @param percent the percentage
	 * @param control the product value's control type (from @link{ControlDefinitionType} list)
	 * @return a decimal number or Zero
	 */
	BigDecimal percentToCoverageValue(BigDecimal percent, String control);
}
