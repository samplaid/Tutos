package lu.wealins.webia.core.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.StepEnricher;

@Component("stepEnricherFactory")
public class StepEnricherFactory {

	@Autowired
	@Qualifier("lissiaStepEnricher")
	private StepEnricher lissiaStepEnricher;

	@Autowired
	@Qualifier("webiaStepEnricher")
	private StepEnricher webiaStepEnricher;

	public StepEnricher getInstance(String stepWorkflow) {

		boolean isAfterOrEqualsCheckDocumentation = StepTypeDTO.getStepType(stepWorkflow).isAfterOrEquals(StepTypeDTO.CHECK_DOCUMENTATION);

		if (isAfterOrEqualsCheckDocumentation) {
			return lissiaStepEnricher;
		}

		return webiaStepEnricher;
	}
}