package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.UpdateAppFormCoverageRequest;
import lu.wealins.common.dto.webia.services.UpdateAppFormPolicyDTO;
import lu.wealins.webia.services.core.service.AppFormService;
import lu.wealins.webia.services.ws.rest.AppFormRESTService;

@Component
public class AppFormRESTServiceImpl implements AppFormRESTService {

	@Autowired
	@Qualifier(value = "appFormService")
	private AppFormService appFormService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.AppFormRESTService#getAppForm(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public AppFormDTO getAppForm(SecurityContext context, Integer formId) {
		return appFormService.getAppForm(formId);
	}

	@Override
	public AppFormDTO getAppFormByWorkFlowItemId(SecurityContext context, Integer workflowItemId) {
		return appFormService.getFormData(workflowItemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.AppFormRESTService#updateCoverage(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.UpdateAppFormCoverageRequest)
	 */
	@Override
	public Boolean updateCoverage(SecurityContext context, UpdateAppFormCoverageRequest request) {
		return appFormService.updateCoverage(request.getFormId(), request.getCoverage());
	}

	@Override
	public AppFormDTO updatePolicy(SecurityContext context, UpdateAppFormPolicyDTO request) {
		return appFormService.updatePolicy(request);
	}

	@Override
	public Collection<AppFormDTO> getAppFormByPolicy(SecurityContext context, String policyId) {

		return appFormService.getAppFormsByPolicy(policyId);
	}

	@Override
	public AppFormDTO recreate(SecurityContext context, Integer workflowItemId) {
		return appFormService.recreate(workflowItemId);
	}

}
