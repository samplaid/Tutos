package lu.wealins.webia.services.core.predicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.webia.services.core.exceptions.InvalidGrammarExpression;
import lu.wealins.webia.services.core.service.CheckDataService;

@Component
public class CheckStepVisibilityEvaluator {
	
	private static final String ISNOTNULL = "ISNOTNULL";
	private static final String ISNULL = "ISNULL";
	private static final String EQUAL_OPERATOR = "==";
	private static final String GRAMMAR_DELIMITER = "\\|";
	private static final String CHECKCODE_REGEX = "^G" + GRAMMAR_DELIMITER + ".+" + EQUAL_OPERATOR + ".+";
	private static final List<String> ENUM_VALUES_VISIBLE = Arrays.asList("YES", "NO");
	private static final String EXPRESSION_AND_SEPARATOR = "&&";

	@Autowired
	private CheckDataService checkDataService;

	public Collection<CheckStepDTO> filterVisbility(Collection<CheckStepDTO> allCheckSteps, Integer workflowItemId) {
		if (allCheckSteps == null) {
			return Collections.emptyList();
		}

		return allCheckSteps.stream()
				.filter(checkStep -> isVisible(checkStep, workflowItemId))
				.collect(Collectors.toList());

	}

	private boolean isVisible(CheckStepDTO checkStep, Integer workflowItemId) {
		if (checkStep == null) {
			return false;
		}
		CheckWorkflowDTO check = checkStep.getCheck();
		if (check == null) {
			return false;
		}

		CheckDataDTO checkData = check.getCheckData();
		String visibleIf = checkStep.getVisibleIf();
		if (visibleIf == null) {
			return true;
		}

		return Arrays.stream(visibleIf.split(EXPRESSION_AND_SEPARATOR)).allMatch(expression -> isVisibleForExpression(workflowItemId, checkData, expression));
	}

	private boolean isVisibleForExpression(Integer workflowItemId, CheckDataDTO checkData, String visibleIf) {
		if (ISNULL.equals(visibleIf)) {
			return (checkData == null || !checkData.hasValue());
		} else if (ISNOTNULL.equals(visibleIf)) {
			return (checkData != null && checkData.hasValue());
		} else if (visibleIf.matches(CHECKCODE_REGEX)) {
			return isVisibleForGrammar(visibleIf, workflowItemId);
		} else if (ENUM_VALUES_VISIBLE.contains(visibleIf)) {
			return checkData != null && visibleIf.equals(checkData.getDataValueYesNoNa());
		} else {
			String errorMessage = String.format(
					"The visible if value '%s' is invalid. The following values are supported : 'YES', 'NO', 'ISNULL', 'ISNOTNULL', 'G|CHECKCODE==VALUE' or && separated values like 'YES&&G|CHECKCODE==VALUE'",
					visibleIf);
			throw new InvalidGrammarExpression(errorMessage);
		}
	}

	private boolean isVisibleForGrammar(String visibleIf, Integer workflowItemId) {

		String[] dbValue = visibleIf.split(GRAMMAR_DELIMITER);
		String[] grammarExpression = dbValue[1].split(EQUAL_OPERATOR);
		String targetCheckCode = grammarExpression[0];
		String targetCheckValue = grammarExpression[1];

		CheckDataDTO checkData = checkDataService.getCheckData(workflowItemId, targetCheckCode);
		return checkData != null && isCheckDataEquals(checkData, targetCheckValue);
	}

	private boolean isCheckDataEquals(CheckDataDTO checkData, String targetCheckValue) {
		// So far we only check the value against DATA_VALUE_YESNONA, later we could add DATA_VALUE_TEXT...etc
		return targetCheckValue.equals(checkData.getDataValueYesNoNa());
	}
}