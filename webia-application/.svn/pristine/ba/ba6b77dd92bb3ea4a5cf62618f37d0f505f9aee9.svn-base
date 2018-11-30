package lu.wealins.webia.core.service;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.StepDTO;

public interface LiabilityStepService {

	/**
	 * Update the step
	 * 
	 * @param step The step.
	 * @param usrId The user id.
	 * @return The updated step.
	 */
	StepDTO updateStep(StepDTO step, String usrId);

	/**
	 * Enrich the step with liability data.
	 * 
	 * @param step The step.
	 * @param userId The user id.
	 * @return The enriched step.
	 */
	StepDTO enrichStep(StepDTO step, String userId);

	/**
	 * Complete the step with liability data.
	 * 
	 * @param step The step.
	 * @param context The security context.
	 * @return The completed step.
	 */
	StepDTO complete(StepDTO step, SecurityContext context);

	void goToNextStep(StepDTO step, String userId);

	StepDTO preComplete(StepDTO step, SecurityContext context, String userId);

	/**
	 * Go to previous step with liability data.
	 * 
	 * @param step The step.
	 * @param context The security context.
	 * @return The completed step.
	 */
	StepDTO previous(StepDTO step, SecurityContext context);

	/**
	 * Abort the step with liability data.
	 * 
	 * @param step The step.
	 * @param context The security context.
	 * @return The aborted step.
	 */
	StepDTO abort(StepDTO step, SecurityContext context);
}
