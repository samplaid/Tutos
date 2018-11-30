package lu.wealins.webia.core.service.impl;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;

public abstract class LiabilityMoneyInServiceImpl extends AbstractWorkflowFormService<AppFormDTO> {

	protected static final String APP_FORM_CANNOT_BE_NULL = "App form cannot be null.";

	protected abstract void beforeCompleteFormData(AppFormDTO appForm, String stepWorkflow, String usrId);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityAppFormService#completeFormData(lu.wealins.webia.ws.rest.dto.AppFormDTO, java.lang.String, java.lang.String)
	 */
	@Override
	public AppFormDTO completeFormData(AppFormDTO appForm, String stepWorkflow, String usrId) {
		beforeCompleteFormData(appForm, stepWorkflow, usrId);
		super.completeFormData(appForm, stepWorkflow, usrId);

		return appForm;
	}

	@Override
	public void abortFormData(AppFormDTO appForm) {
	}

	@Override
	protected boolean isCps2Step(StepTypeDTO stepType) {
		return StepTypeDTO.CPS2_GROUP.contains(stepType);
	}
}
