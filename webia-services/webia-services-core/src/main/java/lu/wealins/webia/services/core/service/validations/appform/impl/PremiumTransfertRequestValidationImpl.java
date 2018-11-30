package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.ClauseType;
import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.common.dto.webia.services.enums.NLProductCountryEnum;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.ProductService;
import lu.wealins.webia.services.core.service.validations.BeneficiaryValidationService;
import lu.wealins.webia.services.core.service.validations.BrokerValidationService;
import lu.wealins.webia.services.core.service.validations.SendingRulesValidationService;
import lu.wealins.webia.services.core.service.validations.appform.AppFormValidationStepService;
import lu.wealins.webia.services.core.service.validations.appform.ComAndSpecifitiesValidationService;
import lu.wealins.webia.services.core.service.validations.appform.InvestmentFundValidationService;
import lu.wealins.webia.services.core.service.validations.appform.OtherClientRelationshipValidationService;

@Service(value = "PremiumTransfertRequestValidation")
public class PremiumTransfertRequestValidationImpl extends AppFormValidationStepService {
	private static final String PREMIUM_TRANSFERT_REQUEST_NULL_DATA = "Premium transfert request Step error: the input form data is null.";
	private static final String ORDER_BY_FAX_REQUIRED = "The order by FAX is mandatory.";
	private static final String MANDATE_EXCEPT_BROKER_REQUIRED = "The mandate of transmission except broker is mandatory.";
	private static final String NOMINATIVE_BENEF_BEFORE_STD_CLAUSE_REQUIRED = "A nominative beneficiary before a standard clause is mandatory.";
	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.PREMIUM_TRANSFER_REQUEST);
	}

	@Autowired
	private ProductService productService;
	@Autowired
	private BeneficiaryValidationService beneficiaryValidationService;
	@Autowired
	private InvestmentFundValidationService investmentFundValidationService;
	@Autowired
	private ComAndSpecifitiesValidationService comAndSpecifitiesValidationService;
	@Autowired
	private OtherClientRelationshipValidationService otherClientRelationshipValidation;
	@Autowired
	private BrokerValidationService brokerValidationService;
	@Autowired
	private SendingRulesValidationService sendingRulesValidationService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO transfertRequestData = getFormData(step);
		Assert.notNull(transfertRequestData, PREMIUM_TRANSFERT_REQUEST_NULL_DATA);
		List<String> errors = new ArrayList<String>();

		brokerValidationService.validateMandateTransmission(transfertRequestData.getBroker(), errors);
		validateMandateExceptBroker(transfertRequestData, errors);
		validateBeneficiaryRuleAndPart(transfertRequestData, errors);
		otherClientRelationshipValidation.assertClientCessionPartsNotNullForRole(transfertRequestData.getOtherClients(), ClientRelationType.CESSION_SURRENDER_RIGHTS, errors);
		comAndSpecifitiesValidationService.validateSendingRulesNotNull(transfertRequestData, errors);
		sendingRulesValidationService.validateMailToAgentRules(transfertRequestData.getSendingRules(), transfertRequestData.getMailToAgent(), errors);
		validateOrderFax(transfertRequestData, errors);
		validateFundPartRules(transfertRequestData, errors);
		validateCoolingOffPeriodRules(transfertRequestData, errors);
		errors.removeAll(Collections.singletonList(null));
		return errors.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * Check if the sum of the fund parts is equal to 100%
	 * 
	 * @param analysis the analysis model
	 * @return a list of error message. Empty if all rules are verified
	 */
	private void validateFundPartRules(AppFormDTO analysis, List<String> errors) {
		investmentFundValidationService.validateFundNotNull(analysis, errors);
		investmentFundValidationService.validateFundPartNotNull(analysis, errors);
		investmentFundValidationService.validateFundPartEq100(analysis, errors);
	}

	private void validateBeneficiaryRuleAndPart(AppFormDTO appForm, List<String> errors) {
		if (!productService.isCapiProduct(appForm.getProductCd())) {
			validateBeneficiacyRules(appForm.getDeathBeneficiaries(), errors);
			validateBeneficiacyRules(appForm.getLifeBeneficiaries(), errors);
			validateClausesRules(appForm.getDeathBeneficiaries(), appForm.getDeathBenefClauseForms(), errors);
			validateClausesRules(appForm.getLifeBeneficiaries(), appForm.getLifeBenefClauseForms(), errors);
		}
	}

	protected <B extends BeneficiaryFormDTO, C extends BenefClauseFormDTO> void validateClausesRules(Collection<B> beneficiaries, Collection<C> clauses, List<String> errors) {

		// check to perform if clauses is not empty
		if (CollectionUtils.isNotEmpty(clauses)) {
			// Check if there is a standard Clause set, then at least one nominative beneficiary has to be defined with a previous rank
			int maxStandardRank = clauses.stream()
									.filter(c -> ClauseType.STANDARD.getValue().equals(c.getClauseTp()) && c.getRankNumber()!=null)
									.map(c -> c.getRankNumber())
					.mapToInt(Integer::new).max().orElse(-1);
			
			if (maxStandardRank > -1) { // -1 means there is no standard clause
				int maxBenefRank = beneficiaries.stream()
						.filter(b -> b.getRankNumber()!=null)
						.map(c -> c.getRankNumber())
						.mapToInt(Integer::new).max().orElse(-1); // -1 means collection is empty

				if (maxBenefRank <= -1 || (maxBenefRank >= maxStandardRank)) {
					 errors.add(NOMINATIVE_BENEF_BEFORE_STD_CLAUSE_REQUIRED);
				 }
			}
		}
	}
	

	/**
	 * Verify if the beneficiaries met the condition below. If a rule is not verified, an error message is added to the returned list.<br>
	 * <ul>
	 * <li><b>Rule1</b>: if beneficiary exists then clause should exist also.</li>
	 * <li><b>Rule2</b>: rank, part should be filled in.</li>
	 * <li><b>Rule3</b>: The beneficiary and clause rank should be in a sequence order.</li>
	 * <li><b>Rule4</b>: The sum of beneficiary part should be equal to 100%</li>
	 * 
	 * </ul>
	 * 
	 * @param beneficiaries a set of beneficiary
	 * @param clauses a set of clauses
	 * @return a list of error message. Empty if all rules are verified
	 */
	private void validateBeneficiacyRules(Collection<BeneficiaryFormDTO> beneficiaries, List<String> errors) {
		beneficiaryValidationService.validateRankNotNull(beneficiaries, errors);
		beneficiaryValidationService.validatePartNotNullFromRank(beneficiaries, 1, errors);

		// part are all assumed to be filled in
		if (CollectionUtils.isNotEmpty(beneficiaries) && beneficiaries.stream().allMatch(beneficiary -> beneficiary.getRankNumber() != null && beneficiary.getSplit() != null)) {
			errors.addAll(beneficiaryValidationService.checkPart(beneficiaries));
		}

	}

	private void validateMandateExceptBroker(AppFormDTO appForm, List<String> errors) {
		if (appForm.getMandate() == null) {
			errors.add(MANDATE_EXCEPT_BROKER_REQUIRED);
		}
	}

	private void validateOrderFax(AppFormDTO appForm, List<String> errors) {
		if (appForm.getOrderByFax() == null) {
			errors.add(ORDER_BY_FAX_REQUIRED);
		}
	}

	private void validateCoolingOffPeriodRules(AppFormDTO analysis, List<String> errors) {
		if (NLProductCountryEnum.FRA.name().equalsIgnoreCase(analysis.getProductCountryCd())
				|| NLProductCountryEnum.LUX.name().equalsIgnoreCase(analysis.getProductCountryCd())) {

			if (analysis.getNoCoolOff() == null) {
				errors.add("The Cooling-off period is mandatory.");
			}
		}
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return false;
	}

}
