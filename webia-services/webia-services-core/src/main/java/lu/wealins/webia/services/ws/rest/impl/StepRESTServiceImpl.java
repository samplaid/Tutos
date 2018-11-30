package lu.wealins.webia.services.ws.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.StepLightDTO;
import lu.wealins.webia.services.core.service.StepService;
import lu.wealins.webia.services.ws.rest.StepRESTService;

@Component
public class StepRESTServiceImpl implements StepRESTService {

	@Autowired
	private StepService stepService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.StepRESTService#getStep(javax.ws.rs.core.SecurityContext, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public StepDTO getStep(SecurityContext context, Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId) {
		return stepService.getStep(workflowItemId, stepWorkflow, workflowItemTypeId);
	}
	
	/* (non-Javadoc)
	 * @see lu.wealins.webia.services.ws.rest.StepRESTService#getStepsByWorkflowItemTypeId(javax.ws.rs.core.SecurityContext, java.lang.Integer)
	 */
	@Override
	public Collection<StepLightDTO> getStepsByWorkflowItemTypeId(SecurityContext context, Integer workflowItemTypeId) {
		return stepService.getStepsByWorkflowItemTypeId(workflowItemTypeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.StepRESTService#getCheckSteps(javax.ws.rs.core.SecurityContext, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Collection<CheckStepDTO> getCheckSteps(SecurityContext context, Integer workflowItemId, String stepWorkflow, Integer workflowItemTypeId) {
		return stepService.getCheckSteps(workflowItemId, stepWorkflow, workflowItemTypeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.StepRESTService#updateStep(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public StepDTO updateStep(SecurityContext context, StepDTO stepDTO) {
		return stepService.updateStep(stepDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.StepRESTService#validateBeforeSave(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public List<String> validateBeforeSave(SecurityContext context, StepDTO stepDTO) {
		return stepService.validateBeforeSave(stepDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.StepRESTService#completeStep(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public StepDTO completeStep(SecurityContext context, StepDTO stepDTO) {
		return stepService.completeStep(stepDTO);
	}
	
	@Override
	public StepDTO abortStep(SecurityContext context, StepDTO stepDTO) {
		return stepService.abortStep(stepDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.StepRESTService#validateBeforeCompleteStep(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.StepDTO)
	 */
	@Override
	public List<String> validateBeforeCompleteStep(SecurityContext context, StepDTO stepDTO) {
		List<String> errors = new ArrayList<String>();
		errors.addAll(stepService.validateBeforeSave(stepDTO));
		errors.addAll(stepService.validateBeforeCompleteStep(stepDTO));

		return errors;
	}
}
