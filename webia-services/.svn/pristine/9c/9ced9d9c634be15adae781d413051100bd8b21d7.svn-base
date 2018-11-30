package lu.wealins.webia.services.core.service.validations.beneficiarychangeform.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.validations.BeneficiaryValidationService;
import lu.wealins.webia.services.core.service.validations.CpsValidationService;
import lu.wealins.webia.services.core.service.validations.beneficiarychangeform.BeneficiaryChangeFormValidationStepService;
import lu.wealins.webia.services.core.service.validations.beneficiarychangeform.BeneficiaryClausesValidationService;

@Service(value = "BeneficiaryChangeAnalysisValidation")
public class AnalysisValidationServiceImpl extends BeneficiaryChangeFormValidationStepService {

	private static final String CLIENT_MANDATORY_FOR_OTHER_RELATIONSHIP = "Client is mandatory for other Relationship.";

	@Autowired
	private BeneficiaryValidationService beneficiaryValidationService;

	@Autowired
	private BeneficiaryClausesValidationService beneficiaryClausesValidationService;

	@Autowired
	private CpsValidationService cpsValidationService;

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.ANALYSIS);
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		BeneficiaryChangeFormDTO beneficiaryChangeForm = getFormData(step);
		List<String> errors = super.validateBeforeComplete(step);

		cpsValidationService.validateCpsUsers(beneficiaryChangeForm.getFirstCpsUser(), beneficiaryChangeForm.getSecondCpsUser(), errors);

		return errors;
	}

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		List<String> errors = super.validateBeforeSave(step);

		BeneficiaryChangeFormDTO beneficiaryChangeForm = getFormData(step);
		if (CollectionUtils.isNotEmpty(errors) && StringUtils.isBlank(beneficiaryChangeForm.getPolicyId())) {
			return errors;
		}

		validateBeneficiaryRuleAndPart(beneficiaryChangeForm, errors);
		validateOtherRelationships(beneficiaryChangeForm, errors);
		validateLifeBeneficiaries(beneficiaryChangeForm, errors);
		validateDeathBeneficiaries(beneficiaryChangeForm, errors);

		return errors;
	}

	private void validateOtherRelationships(BeneficiaryChangeFormDTO beneficiaryChangeForm, List<String> errors) {

		beneficiaryChangeForm.getOtherClients().forEach(x -> {
			if (x.getClientId() == null) {
				errors.add(CLIENT_MANDATORY_FOR_OTHER_RELATIONSHIP);
			}
			if (x.getClientRelationTp() == null) {
				errors.add("Type is mandatory for other Relationship.");
			}
		});
	}

	private void validateLifeBeneficiaries(BeneficiaryChangeFormDTO beneficiaryChangeForm, List<String> errors) {

		beneficiaryChangeForm.getLifeBeneficiaries().forEach(x -> {
			if (x.getClientId() == null) {
				errors.add("Client is mandatory for Beneficiary of maturity.");
			}
		});
	}

	private void validateDeathBeneficiaries(BeneficiaryChangeFormDTO beneficiaryChangeForm, List<String> errors) {

		beneficiaryChangeForm.getDeathBeneficiaries().forEach(x -> {
			if (x.getClientId() == null) {
				errors.add("Client is mandatory for Beneficiary of death.");
			}
		});
	}

	private void validateBeneficiaryRuleAndPart(BeneficiaryChangeFormDTO beneficiaryChangeForm, List<String> errors) {

		validateBeneficiacyRules(beneficiaryChangeForm.getDeathBeneficiaries(), errors);
		validateBeneficiacyRules(beneficiaryChangeForm.getLifeBeneficiaries(), errors);
		errors.addAll(beneficiaryClausesValidationService.validateClausesRules(
				beneficiaryChangeForm.getDeathBeneficiaries(), beneficiaryChangeForm.getDeathBenefClauseForms()));
		errors.addAll(beneficiaryClausesValidationService.validateClausesRules(
				beneficiaryChangeForm.getLifeBeneficiaries(), beneficiaryChangeForm.getLifeBenefClauseForms()));

	}

	/**
	 * Verify if the beneficiaries met the condition below. If a rule is not
	 * verified, an error message is added to the returned list.<br>
	 * <ul>
	 * <li><b>Rule1</b>: if beneficiary exists then clause should exist
	 * also.</li>
	 * <li><b>Rule2</b>: rank, part should be filled in.</li>
	 * <li><b>Rule3</b>: The beneficiary and clause rank should be in a sequence
	 * order.</li>
	 * <li><b>Rule4</b>: The sum of beneficiary part should be equal to
	 * 100%</li>
	 * 
	 * </ul>
	 * 
	 * @param beneficiaries
	 *            a set of beneficiary
	 * @param clauses
	 *            a set of clauses
	 * @return a list of error message. Empty if all rules are verified
	 */
	private void validateBeneficiacyRules(
			Collection<BeneficiaryFormDTO> beneficiaries, List<String> errors) {
		beneficiaryValidationService.validateRankNotNull(beneficiaries, errors);
		beneficiaryValidationService.validatePartNotNullFromRank(beneficiaries, 1, errors);

		// part are all assumed to be filled in
		if (CollectionUtils.isNotEmpty(beneficiaries) && beneficiaries.stream()
				.allMatch(beneficiary -> beneficiary.getRankNumber() != null && beneficiary.getSplit() != null)) {
			errors.addAll(beneficiaryValidationService.checkPart(beneficiaries));
		}
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
