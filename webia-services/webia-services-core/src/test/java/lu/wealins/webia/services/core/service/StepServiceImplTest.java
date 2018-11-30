package lu.wealins.webia.services.core.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.webia.services.core.exceptions.StepException;
import lu.wealins.webia.services.core.service.impl.StepServiceImpl;
import lu.wealins.webia.services.core.service.validations.builder.WorkflowValidationBuilderService;

@ActiveProfiles(value = "dev-test")
@RunWith(MockitoJUnitRunner.class)
public class StepServiceImplTest {

	@InjectMocks
	private StepServiceImpl stepServiceImpl;

	@Mock
	private CheckDataService checkDataService;
	
	@Mock
	private WorkflowValidationBuilderService workflowValidationBuilderService;

	private StepDTO stepDTO;
	
	@Before
	public void setup() {
		stepDTO = new StepDTO();
		List<CheckStepDTO> checkSteps = new ArrayList<CheckStepDTO>();
		CheckStepDTO checkStepDTO = new CheckStepDTO();
		checkStepDTO.setIsMandatory(false);
		CheckWorkflowDTO check = new CheckWorkflowDTO();
		check.setCommentIf("YES");
		CheckDataDTO checkData = new CheckDataDTO();
		checkData.setDataValueYesNoNa("YES");
		checkData.setCommentIf(null);
		
		check.setCheckData(checkData);
		checkStepDTO.setCheck(check);
		checkSteps.add(checkStepDTO);
		stepDTO.setCheckSteps(checkSteps);
	}
	
	@Test
	public void checkCompleteStepOK() throws StepException {
		CheckStepDTO checkStepDTO = stepDTO.getCheckSteps().iterator().next();
		checkStepDTO.setIsMandatory(true);
		checkStepDTO.getCheck().setCommentIf("YES");
		checkStepDTO.getCheck().getCheckData().setCommentIf("a comment");	
		when(checkDataService.hasValue(checkStepDTO.getCheck().getCheckData())).thenReturn(true);

		stepServiceImpl.validateBeforeCompleteStep(stepDTO);
	}
	
	@Test
	public void checkCompleteStepCommitIfMissing() throws StepException {
		CheckStepDTO checkStepDTO = stepDTO.getCheckSteps().iterator().next();
		checkStepDTO.setIsMandatory(false);
		checkStepDTO.getCheck().setCommentIf("YES");
		checkStepDTO.getCheck().getCheckData().setDataValueYesNoNa("YES");
		checkStepDTO.getCheck().getCheckData().setCommentIf(null);	
		
		assertTrue(CollectionUtils.isNotEmpty(stepServiceImpl.validateBeforeCompleteStep(stepDTO)));
	}	
	
	@Test
	public void checkCompleteStepMandatoryMissing() throws StepException {
		CheckStepDTO checkStepDTO = stepDTO.getCheckSteps().iterator().next();
		checkStepDTO.setIsMandatory(true);
		when(checkDataService.hasValue(checkStepDTO.getCheck().getCheckData())).thenReturn(false);
		
		assertTrue(CollectionUtils.isNotEmpty(stepServiceImpl.validateBeforeCompleteStep(stepDTO)));
	}
}
