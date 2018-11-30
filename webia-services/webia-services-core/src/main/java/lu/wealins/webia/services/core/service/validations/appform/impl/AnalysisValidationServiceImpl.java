package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.ClientFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.ClauseType;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.ProductService;
import lu.wealins.webia.services.core.service.validations.BeneficiaryValidationService;
import lu.wealins.webia.services.core.service.validations.appform.AppFormValidationStepService;
import lu.wealins.webia.services.core.service.validations.appform.ComAndSpecifitiesValidationService;
import lu.wealins.webia.services.core.service.validations.appform.InvestmentFundValidationService;
import lu.wealins.webia.services.core.service.validations.appform.OtherClientRelationshipValidationService;
import lu.wealins.webia.services.core.service.validations.appform.PaymentValidationService;
import lu.wealins.webia.services.core.utils.Constants;

@Service(value = "AppFormAnalysisValidationService")
public class AnalysisValidationServiceImpl extends AppFormValidationStepService {
	private static final String NEGATIVE_RANK_IS_NOT_ALLOWED = "Negative rank is not allowed.";
	private static final String INSURED_CLIENT_FOR_NON_CAPI_PRODUCT_MANDATORY = "The insured client is mandatory for non CAPI product.";
	private static final String DEATH_COVERAGE_IS_MANDATORY = "Death coverage is mandatory.";
	private static final String SINGLE_LIVE_ERRMSG = "The single live should not be selected as there are more selected insured.";
	private static final String CLAUSE_RANK_REQUIRED = "The clause rank cannot be empty. It was reverted to the previous value as it exists.";
	private static final String BENEF_CLAUSE_SAME_RANK = "The clauses and beneficiaries should not be in the same rank.";
	private static final String REQUIRED_CLAUSE = "The beneficiaries with rank {0} should have at least one textual clause after.";
	private static final String ONE_FREE_CLAUSE_IF_BENEF_RANK_0 = "The beneficiaries rank begins with number 0. A free clause is mandatory to report them.";
	private static final String POLICYHOLDER_REQ_ERRMSG = "The policy holder is mandatory.";
	private static final String FORM_ANALYSIS_IS_NOT_DEFINED = "The application form analysis is not  defined";
	private static final int SINGLE = 1;
	private static final String DUPLICATE_POLICYHOLDER = "There is a duplication in the policyholders.";
	private static final String DUPLICATE_INSURED = "There is a duplication in the insureds.";
	private static final String DUPLICATE_BENEFICIARY = "There is a duplication in the beneficiaries.";
	private static final String DUPLICATE_OTHER_CLIENT = "There is a duplication in the other clients.";
	private static final String NUMBER_OF_YEARS_REQUIRED = "The number of years is mandatory.";
	private static final String TERM_POLICY_NEED_LIFE_BENEF = "The policy need at least one beneficiary of maturity client or clause.";
	private static final String POLICY_NEED_DEATH_BENEF = "The policy need at least one beneficiary of death client or clause";

	@Autowired
	private OtherClientRelationshipValidationService otherClientRelationshipValidation;

	@Autowired
	private ProductService productService;

	@Autowired
	private BeneficiaryValidationService beneficiaryValidationService;

	@Autowired
	private InvestmentFundValidationService investmentFundValidationService;

	@Autowired
	private ComAndSpecifitiesValidationService comAndSpecifitiesValidationService;

	@Autowired
	private PaymentValidationService paymentValidationService;

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.ANALYSIS);
		ENABLE_STEPS.add(StepTypeDTO.AWAITING_ACCOUNT_OPENING);
		ENABLE_STEPS.add(StepTypeDTO.ACCOUNT_OPENING_REQUEST);
		ENABLE_STEPS.add(StepTypeDTO.PREMIUM_TRANSFER_REQUEST);
		ENABLE_STEPS.add(StepTypeDTO.UPDATE_INPUT);
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO analysis = getFormData(step);
		Assert.notNull(analysis, FORM_ANALYSIS_IS_NOT_DEFINED);
		List<String> errors = new ArrayList<>();

		boolean isCapiProduct = productService.isCapiProduct(analysis.getProductCd());

		validateTerm(analysis, isCapiProduct, errors);
		validateClientDuplicate(analysis, errors);
		validateOtherClientRelationShip(analysis, errors);
		validatePolicyHolderRules(analysis, errors);
		validateInsuredRules(analysis, isCapiProduct, errors);
		validateLivesRules(analysis, errors);
		validateClauseAndBeneficiaryRules(analysis, isCapiProduct, errors);
		validateDeathCoverage(analysis, isCapiProduct, errors);
		validateCommunicationSpecifities(analysis, errors);
		validateInvestmentFund(analysis, errors);
		paymentValidationService.validateSubscriptionMandatoryFields(analysis, errors);
		errors.removeAll(Collections.singletonList(null));
		errors = errors.stream().distinct().collect(Collectors.toList());
		return errors;
	}

	private void validateOtherClientRelationShip(AppFormDTO analysis, List<String> errors) {
		otherClientRelationshipValidation.assertClientCessionPartsEq100(analysis.getOtherClients(), errors);
	}

	private void validateInvestmentFund(AppFormDTO analysis, List<String> errors) {
		investmentFundValidationService.validateFundNotNull(analysis, errors);
		investmentFundValidationService.validateFundPartEq100(analysis, errors);
	}

	private void validateCommunicationSpecifities(AppFormDTO analysis, List<String> errors) {
		comAndSpecifitiesValidationService.validateMailToAgentNullOverSendingRulesCodes(analysis,
				Arrays.asList(Constants.MAIL_AGENTS_CODE), errors);
	}

	public void validateClauseAndBeneficiaryRules(AppFormDTO analysis, boolean isCapiProduct, List<String> errors) {
		if (!isCapiProduct) {
			// if not WHOLE LIFE check a life Beneficiary client or clause is
			// set
			if (isTermSet(analysis) && CollectionUtils.isEmpty(analysis.getLifeBeneficiaries())
					&& CollectionUtils.isEmpty(analysis.getLifeBenefClauseForms())) {
				errors.add(TERM_POLICY_NEED_LIFE_BENEF);
			}

			if (CollectionUtils.isEmpty(analysis.getDeathBeneficiaries()) && CollectionUtils.isEmpty(analysis.getDeathBenefClauseForms())) {
				errors.add(POLICY_NEED_DEATH_BENEF);
			}

			errors.addAll(beneficiaryValidationService.checkPart(analysis.getDeathBeneficiaries()));
			errors.addAll(beneficiaryValidationService.checkPart(analysis.getLifeBeneficiaries()));
			beneficiaryValidationService.validateConstraintRankNumberClause(analysis.getDeathBenefClauseForms(),
					errors);
			beneficiaryValidationService.validateConstraintRankNumberClause(analysis.getLifeBenefClauseForms(), errors);
			validateDeathBenefClauses(analysis, errors);
			validateClausesRules(analysis.getDeathBeneficiaries(), analysis.getDeathBenefClauseForms(), errors);
			validateClausesRules(analysis.getLifeBeneficiaries(), analysis.getLifeBenefClauseForms(), errors);
		}
	}

	private void validateTerm(AppFormDTO appForm, boolean isCapiProduct, List<String> errors) {
		if (appForm.getTerm() == null) {
			errors.add(NUMBER_OF_YEARS_REQUIRED);
		} else if (isCapiProduct && !isTermSet(appForm)) {
			errors.add(NUMBER_OF_YEARS_REQUIRED);
		}
	}

	/**
	 * Term is set if its number is greater than to zero. (0 is not included.) 0
	 * means wholeLife
	 * 
	 * @param appForm
	 *            the application form
	 * @return true if the condition met
	 */
	private boolean isTermSet(AppFormDTO appForm) {
		return appForm.getTerm() != null && BigDecimal.ZERO.compareTo(appForm.getTerm()) < 0;
	}

	private void validateClientDuplicate(AppFormDTO analysis, List<String> errors) {
		// check duplicate clients
		if (hasDuplicateClient(analysis.getPolicyHolders())) {
			errors.add(DUPLICATE_POLICYHOLDER);
		}
		if (hasDuplicateClient(analysis.getInsureds())) {
			errors.add(DUPLICATE_INSURED);
		}
		if (hasDuplicateClient(analysis.getDeathBeneficiaries())) {
			errors.add(DUPLICATE_BENEFICIARY);
		}
		if (hasDuplicateClient(analysis.getLifeBeneficiaries())) {
			errors.add(DUPLICATE_BENEFICIARY);
		}
		if (hasDuplicateClientByRole(analysis.getOtherClients())) {
			errors.add(DUPLICATE_OTHER_CLIENT);
		}
	}

	private void validateDeathBenefClauses(AppFormDTO analysis, List<String> errors) {
		Collection<BenefClauseFormDTO> deathBenefclauses = analysis.getDeathBenefClauseForms();

		// If TWO beneficiaries of death have the same rank then, at least a
		// Std/Free clause type should exist after this rank.
		Collection<BeneficiaryFormDTO> deathBeneficiaries = analysis.getDeathBeneficiaries();
		if (CollectionUtils.isNotEmpty(deathBeneficiaries)) {
			deathBeneficiaries.stream().collect(Collectors.groupingBy((b) -> {
				if (b.getRankNumber() != null) {
					return b.getRankNumber();
				} else {
					return Integer.valueOf(0);
				}
			})).entrySet().stream().filter(entry -> entry.getValue().size() > 1).forEach(entry -> {
				if (CollectionUtils.isEmpty(deathBenefclauses)
						|| !deathBenefclauses.stream().anyMatch((c) -> c.getRankNumber() > entry.getKey())) {
					errors.add(MessageFormat.format(REQUIRED_CLAUSE, entry.getKey()));
				}
			});
		}

	}

	private void validateDeathCoverage(AppFormDTO appForm, boolean isCapiProduct, List<String> errors) {
		if (appForm.getDeathCoverageStd() != null && appForm.getDeathCoverageTp() == null
				&& !isCapiProduct) {
			errors.add(DEATH_COVERAGE_IS_MANDATORY);
		}
	}

	/**
	 * Executes the rule and check if the insured is a physical person. If a
	 * rule is not verified, an error message is added to the returned list.
	 * 
	 * @param analysis
	 *            the analysis model
	 * @return a list of error message. Empty if all rules are verified
	 */
	protected void validateInsuredRules(AppFormDTO analysis, boolean isCapiProduct, List<String> errors) {
		// Rule2: the insured is mandatory for non capi product.
		if (!isCapiProduct && CollectionUtils.isEmpty(analysis.getInsureds())) {
			errors.add(INSURED_CLIENT_FOR_NON_CAPI_PRODUCT_MANDATORY);
		}

	}

	/**
	 * Verify if the clauses met the condition below. If a rule is not verified,
	 * an error message is added to the returned list.<br>
	 * <ul>
	 * <li><b>Rule1</b>: rank, type should be filled in.</li>
	 * <li><b>Rule2</b>: If more than one beneficiary has been added then, at
	 * least a standard clause type should exist.</li>
	 * <li><b>Rule3</b>: Clauses and beneficiary should not be in the same
	 * rank</li>
	 * </ul>
	 * 
	 * @param beneficiaries
	 *            a set of beneficiary
	 * @param clauses
	 *            a set of clauses
	 * @return a list of error message. Empty if all rules are verified
	 */
	protected <B extends BeneficiaryFormDTO, C extends BenefClauseFormDTO> void validateClausesRules(
			Collection<B> beneficiaries, Collection<C> clauses, List<String> errors) {

		int[] sortedClauseRanks = {};
		int[] sortedBenefRank = {};

		// check to perform if clauses is empty
		if (CollectionUtils.isNotEmpty(beneficiaries)) {
			Supplier<Stream<Integer>> beneficiaryRankStream = () -> beneficiaries.stream()
					.map(beneficiary -> beneficiary.getRankNumber()).filter(rank -> rank != null);

			if (beneficiaryRankStream.get().count() > 0) {
				sortedBenefRank = beneficiaryRankStream.get().sorted().distinct().mapToInt(Integer::new).toArray();

				if (beneficiaryRankStream.get().anyMatch(rank -> rank < 0)) {
					errors.add(NEGATIVE_RANK_IS_NOT_ALLOWED);
				}

				// Check if beneficiary rank number begin with 0.
				int firstRank = beneficiaryRankStream.get().sorted().distinct().findFirst().get();

				if (firstRank == 0 && CollectionUtils.isEmpty(clauses)) {
					errors.add(ONE_FREE_CLAUSE_IF_BENEF_RANK_0);
				}
			}

		}

		// check to perform if clauses is not empty
		if (CollectionUtils.isNotEmpty(clauses)) {
			Supplier<Stream<Integer>> scRank = () -> clauses.stream().map(clause -> clause.getRankNumber());

			// Clause rank
			boolean nullClauseRank = clauses.stream().anyMatch(clause -> clause.getRankNumber() == null);

			if (nullClauseRank) {
				errors.add(CLAUSE_RANK_REQUIRED);
				return;
			}

			sortedClauseRanks = scRank.get().sorted().mapToInt(Integer::intValue).toArray();

			// In case of the beneficiary is not provided however the clause is
			// filled in, then the clause should begin with rank 1 (rank 1 is
			// important).
			if (CollectionUtils.isEmpty(beneficiaries) && !checkRankSequenceRules(sortedClauseRanks, 1, 1)) {
				errors.add("The clause rank should follow a sequence which begins with number 1.");
			}

			// Case of the beneficiary is provided.
			if (CollectionUtils.isNotEmpty(beneficiaries)) {

				// all clause ranks have been filled in and benef is supposed to
				// not contain a null rank.
				if (!nullClauseRank && sortedBenefRank.length > 0) {
					// If beneficiary rank begins with 0 then there should be at
					// least one free clause after to list them.
					if (isRankEq0AndContainsNoFreeClause(sortedBenefRank[0], clauses)) {
						errors.add(ONE_FREE_CLAUSE_IF_BENEF_RANK_0);
					}

					int maxRankBenef = sortedBenefRank[sortedBenefRank.length - 1];
					int minRankClause = sortedClauseRanks[0];

					if (minRankClause > maxRankBenef + 1) {
						errors.add("The clause rank is out of range of the beneficiary sequence.");
					}

				}

			}

			int[] sortedDistinctRanks = {};

			if (sortedBenefRank.length > 0) {
				int[] ranks = ArrayUtils.addAll(sortedClauseRanks, sortedBenefRank);
				sortedDistinctRanks = Arrays.stream(ranks).sorted().distinct().toArray();

				// Check if a duplicate ranks exists in clauses and/or
				// beneficiaries ranks.
				if (ranks.length != sortedDistinctRanks.length) {
					errors.add(BENEF_CLAUSE_SAME_RANK);
				}
			} else {
				sortedDistinctRanks = Arrays.stream(sortedClauseRanks).sorted().distinct().toArray();

				if (sortedClauseRanks.length != sortedDistinctRanks.length) {
					errors.add("The beneficiary clause contains a duplicate rank.");
				}
			}

			// Clauses and beneficiary rank should follow a sequence
			if (sortedClauseRanks.length > 1
					&& !checkRankSequenceRules(sortedDistinctRanks, sortedDistinctRanks[0], 1)) {
				errors.add("The ranks doesn't follow a sequence.");
			}
		}
	}

	private <T extends BenefClauseFormDTO> boolean isRankEq0AndContainsNoFreeClause(int rank, Collection<T> clauses) {
		return (rank == 0 && (CollectionUtils.isEmpty(clauses)
				|| clauses.stream().noneMatch(c -> ClauseType.FREE.getValue().equals(c.getClauseTp()))));
	}

	/**
	 * Determine if the difference between each number is equal to 1 an the
	 * giving array is in correct order.
	 * 
	 * @param ranks
	 *            the arrays to be checked.
	 * @param start
	 *            used to test if the value at the index 0 is equal to this
	 *            value.
	 * @param diff
	 *            the difference between two number.
	 * @return true if the condition is met.
	 */
	private boolean checkRankSequenceRules(int[] ranks, int start, int diff) {
		return IntStream.range(0, (int) (ranks.length)).allMatch(i -> {
			if (ranks.length == 1) {
				return ranks[0] == start;
			} else if (ranks.length == i + 1) {
				return true;
			} else {
				return ranks != null && ranks.length >= 1 && ranks[0] == start && (ranks[i + 1] == ranks[i] + diff);
			}
		});

	}

	/**
	 * If more than one insured physical person is added, the single live should
	 * not be selected.
	 * 
	 * @param analysis
	 *            the analysis model
	 * @return a list of error message. Empty if all condition are met.
	 */
	protected void validateLivesRules(AppFormDTO analysis, List<String> errors) {
		if (analysis.getInsureds() != null && analysis.getInsureds().size() > 1
				&& Integer.valueOf(SINGLE).equals(analysis.getLives())) {
			errors.add(SINGLE_LIVE_ERRMSG);
		}
	}

	/**
	 * Add error if the policy holder is not defined.
	 * 
	 * @param analysis
	 *            the analysis model
	 * @return true if the condition is met.
	 */
	protected void validatePolicyHolderRules(AppFormDTO analysis, List<String> errors) {
		if (CollectionUtils.isEmpty(analysis.getPolicyHolders())
				|| analysis.getPolicyHolders().stream().noneMatch(p -> p.getClientId() != null)) {
			errors.add(POLICYHOLDER_REQ_ERRMSG);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.AnalysisValidationService#
	 * validateBeforeSave(lu.wealins.webia.services.ws.rest.dto.AppFormDTO)
	 */
	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		AppFormDTO analysis = getFormData(step);
		List<String> errors = new ArrayList<>();
		// !!!! WARNING !!!
		// PUT only DB constraint here because it will stop any save of step
		// Every thing should be sauvable at this point except technical
		// constraints
		beneficiaryValidationService.validateConstraintRankNumberClause(analysis.getDeathBenefClauseForms(), errors);
		beneficiaryValidationService.validateConstraintRankNumberClause(analysis.getLifeBenefClauseForms(), errors);
		paymentValidationService.validatePaymentTransferRule(analysis, errors);

		return errors;
	}

	private <T extends ClientFormDTO> boolean hasDuplicateClient(Collection<T> list) {
		Set<Integer> uniq = list.stream().map(a -> a.getClientId()).collect(Collectors.toSet());
		return (uniq.size() < list.size());
	}

	private <T extends ClientFormDTO> boolean hasDuplicateClientByRole(Collection<T> list) {
		Set<Integer> roles = list.stream().map(a -> a.getClientRelationTp()).collect(Collectors.toSet());
		for (Integer role : roles) {
			List<T> subList = list.stream().filter(a -> a.getClientRelationTp() == role).collect(Collectors.toList());
			if (hasDuplicateClient(subList)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

}
