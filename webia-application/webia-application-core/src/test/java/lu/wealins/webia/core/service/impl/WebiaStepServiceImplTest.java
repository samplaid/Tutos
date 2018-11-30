/**
 * 
 */
package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.InsuredFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.utils.Constantes;

/**
 * @author oro
 */
@ActiveProfiles(value = "unit-test")
@RunWith(PowerMockRunner.class)
public class WebiaStepServiceImplTest {

	@InjectMocks
	private WebiaStepServiceImpl webiaStepService;

	@Mock
	private LiabilityClientServiceImpl liabilityClientService;

	@Test
	@Ignore // FIXME : remove this reflection based tests which hides compilation issues and the implementations are already removed!!!!.
	public void validateGdpr() throws Exception {
		// Test for true: case1: Medical questionnaire yes, medical acceptance anything, isClientGdprConsentAccepted == false

		Collection<InsuredFormDTO> insureds = new ArrayList<>();
		insureds.add(PowerMockito.mock(InsuredFormDTO.class));
		insureds.add(PowerMockito.mock(InsuredFormDTO.class));
		AppFormDTO appForm = new AppFormDTO();
		appForm.setInsureds(insureds);

		PowerMockito.doReturn(false).when(liabilityClientService).isClientGdprConsentAccepted(ArgumentMatchers.anyInt());
		PowerMockito.doReturn(PowerMockito.mock(ClientDTO.class)).when(liabilityClientService).getClient(ArgumentMatchers.anyInt());

		List<String> errors = new ArrayList<>();
		StepDTO step = createStepForGdprValidation(Constantes.YES, Constantes.NA);
		step.setFormData(appForm);

		// Assert case1
		Whitebox.invokeMethod(webiaStepService, "validateGdpr", step, errors);
		Assert.notEmpty(errors);

		// Test for true: case2: Medical questionnaire no, medical acceptance yes, isClientGdprConsentAccepted == false
		errors = new ArrayList<>();
		step = createStepForGdprValidation(Constantes.NO, Constantes.YES);
		step.setFormData(appForm);
		// Assert case2
		Whitebox.invokeMethod(webiaStepService, "validateGdpr", step, errors);
		Assert.notEmpty(errors);

		// Test for true: case3: Medical questionnaire no, medical acceptance no, isClientGdprConsentAccepted == false
		errors = new ArrayList<>();
		step = createStepForGdprValidation(Constantes.NO, Constantes.NO);
		step.setFormData(appForm);
		// Assert case3
		Whitebox.invokeMethod(webiaStepService, "validateGdpr", step, errors);
		Assert.notEmpty(errors);

	}

	/**
	 * @return
	 */
	private StepDTO createStepForGdprValidation(String medicalQuestionaireResponse, String medicalAcceptanceResponse) {
		StepDTO step = new StepDTO();
		step.setStepWorkflow(StepTypeDTO.ANALYSIS.getvalue());
		Collection<CheckStepDTO> stepQuestions = new ArrayList<>();

		CheckDataDTO response = new CheckDataDTO();
		response.setCheckDataId(1);
		response.setCheckId(Constantes.MEDICAL_QUESTIONNAIRE);
		response.setDataValueYesNoNa(medicalQuestionaireResponse);
		CheckWorkflowDTO question = new CheckWorkflowDTO();
		question.setCheckId(Constantes.MEDICAL_QUESTIONNAIRE);
		question.setCheckData(response);
		CheckStepDTO stepQuestion = new CheckStepDTO();
		stepQuestion.setCheck(question);
		stepQuestions.add(stepQuestion);

		response = new CheckDataDTO();
		response.setCheckDataId(2);
		response.setCheckId(Constantes.MEDICAL_ACCEPTANCE_EXECUTED);
		response.setDataValueYesNoNa(medicalAcceptanceResponse);
		question = new CheckWorkflowDTO();
		question.setCheckId(Constantes.MEDICAL_ACCEPTANCE_EXECUTED);
		question.setCheckData(response);
		stepQuestion = new CheckStepDTO();
		stepQuestion.setCheck(question);
		stepQuestions.add(stepQuestion);

		step.setCheckSteps(stepQuestions);
		return step;
	}
}
