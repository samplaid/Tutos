package lu.wealins.common.validations;

import java.util.ArrayList;
import java.util.List;

import lu.wealins.common.dto.webia.services.StepDTO;

public interface ValidationStepService extends WorkflowItemsSpecific {

	/**
	 * Validate the step before to complete it.
	 * 
	 * @param step The step.
	 * @return The list of errors.
	 */
	default List<String> validateBeforeComplete(StepDTO step) {
		return new ArrayList<>();
	}

	/**
	 * Validate the step before to save it.
	 * 
	 * @param step The step.
	 * @return The list of errors.
	 */
	default List<String> validateBeforeSave(StepDTO step) {
		return new ArrayList<>();
	}

	boolean needValidateBeforeComplete(StepDTO step);

	boolean needValidateBeforeSave(StepDTO step);
}
