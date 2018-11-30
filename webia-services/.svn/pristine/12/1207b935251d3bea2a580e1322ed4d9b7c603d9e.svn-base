package lu.wealins.webia.services.core.predicates;

import java.util.Arrays;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.webia.services.core.exceptions.InvalidGrammarExpression;
import lu.wealins.webia.services.core.service.CheckDataService;

@RunWith(MockitoJUnitRunner.class)
public class CheckStepVisibilityEvaluatorTest {

	@InjectMocks
	private CheckStepVisibilityEvaluator classUnderTest;

	@Mock
	private CheckDataService checkDataService;

	@Test
	public void testNullCheckSteps() {
		Assertions.assertThat(classUnderTest.filterVisbility(null, null)).isEmpty();
	}

	@Test
	public void testNominalCase() {
		CheckDataDTO yesCheckData = createYesNoCheckData("YES");
		CheckStepDTO checkStep1 = createCheckStep("ISNULL", yesCheckData);
		CheckStepDTO checkStep2 = createCheckStep("ISNULL", null);
		CheckStepDTO checkStep3 = createCheckStep("ISNOTNULL", yesCheckData);
		CheckStepDTO checkStep4 = createCheckStep("ISNOTNULL", null);
		CheckStepDTO checkStep5 = createCheckStep("YES", yesCheckData);
		CheckStepDTO checkStep6 = createCheckStep("NO", yesCheckData);

		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1, checkStep2, checkStep3, checkStep4, checkStep5, checkStep6);

		Assertions.assertThat(classUnderTest.filterVisbility(allCheckSteps, null)).containsExactlyInAnyOrder(checkStep2, checkStep3, checkStep5);
	}

	private CheckDataDTO createYesNoCheckData(String yesNoNa) {
		CheckDataDTO yesCheckData = new CheckDataDTO();
		yesCheckData.setDataValueYesNoNa(yesNoNa);
		return yesCheckData;
	}

	@Test
	public void testGrammarExpressions() {
		CheckStepDTO checkStep1 = createCheckStep("G|MOV10==YES", null);
		CheckStepDTO checkStep2 = createCheckStep("G|MOV10==NO", null);

		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1, checkStep2);

		CheckDataDTO checkDataMock = new CheckDataDTO();
		checkDataMock.setDataValueYesNoNa("YES");
		Mockito.when(checkDataService.getCheckData(1000, "MOV10")).thenReturn(checkDataMock);

		Assertions.assertThat(classUnderTest.filterVisbility(allCheckSteps, 1000)).containsExactlyInAnyOrder(checkStep1);
	}

	@Test
	public void testComplexGrammarExpressions() {
		CheckDataDTO noCheckData = new CheckDataDTO();
		noCheckData.setDataValueYesNoNa("NO");
		CheckStepDTO checkStep1 = createCheckStep("NO&&G|MOV10==YES", noCheckData);

		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1);

		CheckDataDTO checkDataMock = new CheckDataDTO();
		checkDataMock.setDataValueYesNoNa("YES");
		Mockito.when(checkDataService.getCheckData(1000, "MOV10")).thenReturn(checkDataMock);

		Assertions.assertThat(classUnderTest.filterVisbility(allCheckSteps, 1000)).containsExactlyInAnyOrder(checkStep1);
	}

	@Test(expected = InvalidGrammarExpression.class)
	public void testBadGrammarExpressions() {
		CheckStepDTO checkStep1 = createCheckStep("G|invalid==", null);
		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1);
		classUnderTest.filterVisbility(allCheckSteps, null);
	}

	@Test(expected = InvalidGrammarExpression.class)
	public void testBadGrammarExpressions2() {
		CheckStepDTO checkStep1 = createCheckStep("G|invalid", null);
		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1);
		classUnderTest.filterVisbility(allCheckSteps, null);
	}

	@Test(expected = InvalidGrammarExpression.class)
	public void testBadGrammarExpressions3() {
		CheckStepDTO checkStep1 = createCheckStep("G|==invalid", null);
		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1);
		classUnderTest.filterVisbility(allCheckSteps, null);
	}

	@Test(expected = InvalidGrammarExpression.class)
	public void testBadGrammarExpressions4() {
		CheckStepDTO checkStep1 = createCheckStep("invalidG|MOV12==true", null);
		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1);
		classUnderTest.filterVisbility(allCheckSteps, null);
	}

	@Test(expected = InvalidGrammarExpression.class)
	public void testBadGrammarExpressions5() {
		CheckStepDTO checkStep1 = createCheckStep("&&G|MOV12==true", null);
		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1);
		classUnderTest.filterVisbility(allCheckSteps, null);
	}

	@Test(expected = InvalidGrammarExpression.class)
	public void testBadGrammarExpressions6() {
		CheckStepDTO checkStep1 = createCheckStep("AB&&G|MOV12==true", null);
		Collection<CheckStepDTO> allCheckSteps = Arrays.asList(checkStep1);
		classUnderTest.filterVisbility(allCheckSteps, null);
	}

	private CheckStepDTO createCheckStep(String visibleIf, CheckDataDTO checkData) {
		CheckStepDTO checkStep = new CheckStepDTO();
		checkStep.setVisibleIf(visibleIf);
		checkStep.setCheck(new CheckWorkflowDTO());
		if (checkData != null) {
			checkStep.getCheck().setCheckData(checkData);
		}
		return checkStep;
	}

}
