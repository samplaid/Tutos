package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.CheckStepDTO;

public interface CheckStepService {

	Collection<CheckStepDTO> getCheckSteps(String userId, Integer workflowItemId, String stepWorkflow);

	boolean isMedicalQuestionsChecked(Collection<CheckStepDTO> checkStepDtos);

	CheckStepDTO getCheckStep(Collection<CheckStepDTO> checkSteps, Metadata metadata);

	boolean isNo(Collection<CheckStepDTO> checkStepDtos, Metadata metadata);

	boolean isYes(Collection<CheckStepDTO> checkStepDtos, Metadata metadata);
}
