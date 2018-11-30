package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.DeathCoverageClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.webia.services.CodeLabelDTO;
import lu.wealins.common.dto.liability.services.enums.ControlDefinitionType;
import lu.wealins.webia.core.service.LiabilityDeathCoverageService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductLineService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.service.WebiaCodeLabelService;

@Service
public class LiabilityDeathCoverageServiceImpl implements LiabilityDeathCoverageService {

	private static final String DTHFAC = "DTHFAC";
	private static final String DTHFC3 = "DTHFC3";
	private static final String DTHFC2 = "DTHFC2";
	private static final String AMOUNT = "AMOUNT";
	private static final String MXCLFA = "MXCLFA";
	private static final String MNCLFA = "MNCLFA";
	private static final String MULTIPLIER = "MULTIPLIER";
	private static final String PERCENTAGE = "PERCENTAGE";
	private static final String STANDARD = "standard";
	private static final String REGEX = ".*\\[|\\].*";
	private static final String INDEXED_DEATH_COVERAGE_LABEL_CONTROL_TYPE = "10";
	private static final BigDecimal THOUSAND = new BigDecimal("100");

	@Autowired
	private LiabilityProductService productService;
	@Autowired
	private LiabilityProductLineService productLineService;
	@Autowired
	private LiabilityProductValueService productValueService;
	@Autowired
	private LiabilityPolicyService policyService;


	@Autowired
	private WebiaCodeLabelService codeLabelService;

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityDeathCoverageService#getDeathCoveragClauses(java.lang.String)
	 */
	@Override
	public DeathCoverageClausesDTO getDeathCoverageClauses(String productCd) {

		Collection<DeathCoverageClauseDTO> standartClauses = new HashSet<>();
		Collection<DeathCoverageClauseDTO> alternativeClauses = new HashSet<>();

		// No coverage for the CAPI products
		if (productCd != null && !productService.isCapiProduct(productCd.toUpperCase()) ) {
			Collection<ProductLineDTO> productLines = productLineService.getFilterProductLines(productCd,
					Arrays.asList(ControlDefinitionType.DEATH_COVERAGE.getValue(), MULTIPLIER, MNCLFA, MXCLFA, PERCENTAGE, AMOUNT, DTHFC2, DTHFC3, DTHFAC));

			for (ProductLineDTO productLine : productLines) {

				// Retrieve product values with DTHCAL control type
				ProductValueDTO dthcalProductValue = productLine.getProductValues().stream().filter(x -> ControlDefinitionType.DEATH_COVERAGE.getValue().equals(x.getControl())).findFirst()
						.orElse(null);

				if (dthcalProductValue != null) {
					String deathCoverageTp = productValueService.getAlphaValue(dthcalProductValue);
					CodeLabelDTO codeLabeldeathCoverage = codeLabelService.getDeathCoverageCodeLabel(productLine.getPrlId(), deathCoverageTp);

					if (codeLabeldeathCoverage != null) {
						String clause = codeLabeldeathCoverage.getLabel();
						String regex = clause.replaceAll(REGEX, "");
						String defaultValue = null;
						String inputType = null;
						String controlType = null;
						boolean isStandard = isStandard(productLine);
						boolean isOptional = !isStandard;
						boolean isUpdatable = !isStandard;
						if (StringUtils.isNotBlank(regex) && !regex.equals(clause)) {
							String[] regexTab = regex.split("\\|");

							if (regexTab.length != 2) {
								throw new IllegalStateException("Death coverage label must use a grammar such as that CONTROL_TYPE|INPUT_TYPE");
							}
							controlType = regexTab[0];
							inputType = regexTab[1];

							if (MULTIPLIER.equals(controlType)) {
								String min = productValueService.getAlphaValue(productLine, MNCLFA);
								String max = productValueService.getAlphaValue(productLine, MXCLFA);

								isUpdatable = isUpdatable && min != null && !min.equals(max);
								defaultValue = max;

							} else {
								isUpdatable = isUpdatable && isUpdtable(productLine, controlType);
								defaultValue = productValueService.getAlphaValue(productLine, controlType);
								if (defaultValue != null){
									if (PERCENTAGE.equals(inputType)) {
										BigDecimal percent = new BigDecimal(defaultValue.replaceAll(",", ""));
										defaultValue = coverageValueToPercent(percent).toPlainString();
									} else { //AMOUNT
										BigDecimal amount = new BigDecimal(defaultValue.replaceAll(",", ""));
										defaultValue = amount.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
									}
								}
							}
							clause = clause.replace("[" + regex + "]", "[" + inputType + "]");
						}

						DeathCoverageClauseDTO deathCoverageClause = createDeathCoverageClause(clause, defaultValue, deathCoverageTp, isUpdatable, inputType, controlType, isStandard, isOptional,
								isIndexed(deathCoverageTp));

						if (isStandard) {
							standartClauses.add(deathCoverageClause);
						} else {
							alternativeClauses.add(deathCoverageClause);
						}
					}
				}
			}
		}

		DeathCoverageClausesDTO deathCoverage = new DeathCoverageClausesDTO();

		deathCoverage.setStandardClauses(standartClauses);
		deathCoverage.setAlternativeClauses(alternativeClauses);

		return deathCoverage;
	}

	private boolean isStandard(ProductLineDTO productLine) {
		return (productLine.getName() != null && productLine.getName().toLowerCase().contains(STANDARD));
	}

	private boolean isUpdtable(ProductLineDTO productLine, String controlType) {
		ProductValueDTO control = productLine.getProductValues().stream().filter(x -> controlType.equals(x.getControl())).findFirst().orElse(null);
		return (control != null && control.getSubDataType() != null && control.getSubDataType().intValue() == 2);
	}

	private boolean isIndexed(String deathCoverageTp) {
		return (INDEXED_DEATH_COVERAGE_LABEL_CONTROL_TYPE.equals(deathCoverageTp));
	}

	private DeathCoverageClauseDTO createDeathCoverageClause(String clause, String defaultValue, String deathCoverageTp, boolean isUpdatable, String inputType, String controlType, boolean isStandard,
			boolean isOptional, boolean isIndexed) {
		DeathCoverageClauseDTO deathCoverageClause = new DeathCoverageClauseDTO();

		deathCoverageClause.setClause(clause);
		deathCoverageClause.setDefaultValue(defaultValue);
		deathCoverageClause.setDeathCoverageTp(deathCoverageTp);
		deathCoverageClause.setUpdatable(isUpdatable);
		deathCoverageClause.setInputType(inputType);
		deathCoverageClause.setControlType(controlType);
		deathCoverageClause.setStandard(isStandard);
		deathCoverageClause.setOptional(isOptional);
		deathCoverageClause.setIndexed(isIndexed);
		return deathCoverageClause;
	}

	@Override
	public BigDecimal coverageValueToPercent(BigDecimal value) {
		BigDecimal result = value;
		if (value == null || (BigDecimal.ZERO).compareTo(value) == 0) {
			result = BigDecimal.ZERO;
		}
		if (result.compareTo(BigDecimal.ONE) < 0) {
			result = result.multiply(THOUSAND).add(THOUSAND); // (0.01 => 101)
		} else {
			result = result.multiply(THOUSAND); // (1.01 => 101)
		}
		return result;
	}

	@Override
	public BigDecimal percentToCoverageValue(BigDecimal percent, String control) {
		BigDecimal result = percent;
		if (percent == null || (BigDecimal.ZERO).compareTo(percent) == 0) {
			result = BigDecimal.ZERO;
		}
		if (result.compareTo(THOUSAND) > -1) {
			result = percent.subtract(THOUSAND).divide(THOUSAND, 8, BigDecimal.ROUND_HALF_UP);
			if (control != null && ControlDefinitionType.DTHFAC.getValue().equals(control)) {
				result = result.add(BigDecimal.ONE);
			}
		}
		return result;
	}

	@Override
	public DeathCoverageClauseDTO getDeathCoverageClause(String productCd, String code, boolean isStandard) {
		if (code != null) {
			DeathCoverageClausesDTO deathCoverageClauses = getDeathCoverageClauses(productCd);
			Collection<DeathCoverageClauseDTO> clauses;
			if (BooleanUtils.isTrue(isStandard)) {
				clauses = deathCoverageClauses.getStandardClauses();
			} else {
				clauses = deathCoverageClauses.getAlternativeClauses();
			}
			for (DeathCoverageClauseDTO clause : clauses) {
				if (code.equals(clause.getDeathCoverageTp())) {
					return clause;
				}
			}
		}
		return null;
	}

	@Override
	public DeathCoverageClauseDTO getPolicyDeathCoverage(String polId) {
		DeathCoverageClauseDTO deathCoverageClause = new DeathCoverageClauseDTO();
		PolicyDTO policy = policyService.getPolicy(polId);
		PolicyCoverageDTO firstCoverage = policy.getFirstPolicyCoverages();

		if (firstCoverage != null) {
			String deathCoverageTp = productValueService.getAlphaValue(firstCoverage.getProductLine(), ControlDefinitionType.DEATH_COVERAGE.getValue());
			
			if (StringUtils.isNotBlank(deathCoverageTp)){
				String clause = null;
				ProductLineDTO productLine = productLineService.getProductLine(firstCoverage.getProductLine());
				CodeLabelDTO codeLabeldeathCoverage = codeLabelService.getDeathCoverageCodeLabel(productLine.getPrlId(), deathCoverageTp);
				
				if (codeLabeldeathCoverage != null) {
					clause = codeLabeldeathCoverage.getLabel();
					String regex = clause.replaceAll(REGEX, "");
	
					String controlType = null;
					String inputType = null;
					String value = null;
					if (StringUtils.isNotBlank(regex) && !regex.equals(clause)) {
						String[] regexTab = regex.split("\\|");
	
						if (regexTab.length != 2) {
							throw new IllegalStateException("Death coverage label must use a grammar such as that CONTROL_TYPE|INPUT_TYPE");
						}
						controlType = regexTab[0];
						inputType = regexTab[1];
	
						if (MULTIPLIER.equals(controlType)) {
							BigDecimal multiplier = firstCoverage.getMultiplier();
							value = multiplier.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
							clause = clause.replace("[" + regex + "]", value);
						} else {
							// get the override policy value or the default product line value
							ProductValueDTO deathCoverageAmountOrPercentage = productValueService.getDeathCoverageAmountOrPercentage(polId, firstCoverage.getProductLine(), controlType);
							if (deathCoverageAmountOrPercentage != null) {
								value = productValueService.getAlphaValue(deathCoverageAmountOrPercentage);
								if (value != null){
									if (PERCENTAGE.equals(inputType) ) {
										BigDecimal percent = new BigDecimal(value.replaceAll(",", ""));
										value = coverageValueToPercent(percent).setScale(2, RoundingMode.HALF_EVEN).toPlainString();
									} else { //AMOUNT
										BigDecimal amount = new BigDecimal(value.replaceAll(",", ""));
										value = amount.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
									}
								}
								clause = clause.replace("[" + regex + "]", value);
							}
						}
					}
					boolean isStandard = isStandard(productLine);
					boolean isOptional = !isStandard;
					deathCoverageClause = createDeathCoverageClause(clause, value, deathCoverageTp, false, inputType, controlType, isStandard, isOptional, isIndexed(deathCoverageTp));
	
				} else {
					// Spec: Webia-Lissia 18.1_v4.docx: 17. Garantie décès: S’il n’existe pas de libellé dans CODE_LABEL, afficher See the documentation of the policy
					deathCoverageClause.setClause("See the documentation of the policy");
				}
			} else {
				// Spec: Webia-Lissia 18.1_v4.docx: 17. Garantie décès: Si la garantie décès n’est pas renseignée dans product_values (cas Capi), afficher No death coverage
				deathCoverageClause.setClause("No death coverage");
			}
		}
		return deathCoverageClause;
	}

}
