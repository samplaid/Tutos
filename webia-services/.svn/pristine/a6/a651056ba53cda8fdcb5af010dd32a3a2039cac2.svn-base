package lu.wealins.webia.services.core.persistence.entity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lu.wealins.webia.services.core.exceptions.StepException;
import lu.wealins.webia.services.core.mapper.StepMapper;
import lu.wealins.webia.services.core.persistence.repository.StepRepository;
import lu.wealins.webia.services.core.service.StepService;
import lu.wealins.common.dto.webia.services.StepDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("unit-test")
@WebAppConfiguration
public class StepEntityTest {

	@Autowired
	private StepService stepService;

	@Autowired
	private StepRepository stepRepository;

	@Autowired
	private StepMapper stepMapper;

	@Test
	public void check() throws StepException {

		final String stepWorkflow = "Acceptance";
		final Integer workflowItemTypeId = 7;
		final Integer workflowItemId = 842;

		StepDTO stepDTO = stepService.getStep(workflowItemId, stepWorkflow, workflowItemTypeId);
		StepEntity stepFromDTO = stepMapper.asStepEntity(stepDTO);
		StepEntity stepFromDB = stepRepository.findFirstByStepWorkflowIgnoreCaseAndWorkflowItemTypeIdOrderByStepIdAsc(stepWorkflow, workflowItemTypeId);

		// order check steps by check id
		List<CheckStepEntity> CheckStepsFromDTO = new ArrayList<CheckStepEntity>(stepFromDTO.getCheckSteps());
		CheckStepsFromDTO.sort((cs1, cs2) -> cs1.getCheck().getCheckId().compareTo(cs2.getCheck().getCheckId()));
		stepFromDTO.setCheckSteps(CheckStepsFromDTO);

		List<CheckStepEntity> CheckStepsFromDB = new ArrayList<CheckStepEntity>(stepFromDB.getCheckSteps());
		CheckStepsFromDB.sort((cs1, cs2) -> cs1.getCheck().getCheckId().compareTo(cs2.getCheck().getCheckId()));
		stepFromDB.setCheckSteps(CheckStepsFromDB);

		List<String> excludedFields = Arrays.asList("checkSteps");
		assertTrue(EqualsBuilder.reflectionEquals(stepFromDB, stepFromDTO, excludedFields));

		compareCheckSteps(CheckStepsFromDTO, CheckStepsFromDB);

	}

	private void compareCheckSteps(List<CheckStepEntity> CheckStepsFromDTO, List<CheckStepEntity> CheckStepsFromDB) {
		List<String> excludedFields = Arrays.asList("check", "step", "label");
		for (int i = 0; i < CheckStepsFromDB.size(); i++) {
			CheckStepEntity lhs = CheckStepsFromDB.get(i);
			CheckStepEntity rhs = CheckStepsFromDTO.get(i);
			assertTrue(EqualsBuilder.reflectionEquals(lhs, rhs, excludedFields));
			assertTrue(EqualsBuilder.reflectionEquals(lhs.getCheck(), rhs.getCheck()));
			assertTrue(EqualsBuilder.reflectionEquals(lhs.getLabel(), rhs.getLabel()));
		}
	}

}
