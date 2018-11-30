package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.UpdateAppFormPolicyDTO;

public interface AppFormService {

	/**
	 * Get the app form according to the form id
	 * 
	 * @param formId the formId
	 * @return The app form.
	 */
	AppFormDTO getAppForm(Integer formId);

	Boolean updateCoverage(Integer formId, Integer coverage);

	/**
	 * Get the app forms belonging to the policy.
	 * 
	 * @param PolicyId
	 *            the policy Id.
	 * @return The appForms
	 */
	Collection<AppFormDTO> getAppFormsByPolicy(String policyId);

	/**
	 * Simply perform an abort on the appform record (i.e. set the satatus to cancel).
	 * 
	 * @param appForm The app form.
	 * @return The updated app form.
	 */
	AppFormDTO abort(AppFormDTO appForm);

	AppFormDTO updatePolicy(UpdateAppFormPolicyDTO request);

	AppFormDTO recreate(Integer formId);

	AppFormDTO getFormData(Integer workflowItemId);
}
