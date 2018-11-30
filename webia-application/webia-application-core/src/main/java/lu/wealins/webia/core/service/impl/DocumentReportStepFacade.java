package lu.wealins.webia.core.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.webia.core.service.DocumentGenerationServiceBase;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;

@Component
public class DocumentReportStepFacade {
	@Autowired
	@Qualifier("FollowUpDocumentGeneratorService")
	DocumentGenerationServiceBase followUpDocumentService;

	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	@Qualifier("FollowUpReceptionPrimeDocumentGeneratorService")
	DocumentGenerationServiceBase followUpReceptionPrimeDocumentService;



	public void generateReport(SecurityContext context, AppFormDTO appForm) {
		StepTypeDTO currentStep = StepTypeDTO.getStepType(getStep(appForm));

		switch (currentStep) {
		case REGISTRATION:
			generateDocumentFromAppForm(context, appForm);
			break;
		case GENERATE_DOCUMENTATION:
			generateReceptionPrimeFromAppForm(context, appForm);
			break;
		default:
			break;
		}

	}

	private DocumentGenerationResponse generateDocumentFromAppForm(SecurityContext context, AppFormDTO appForm) {
		return followUpDocumentService.generateDocumentFromAppForm(context, appForm);
	}

	private DocumentGenerationResponse generateReceptionPrimeFromAppForm(SecurityContext context, AppFormDTO appForm) {
		return followUpReceptionPrimeDocumentService.generateDocumentFromAppForm(context, appForm);
	}
	private String getStep(AppFormDTO appFormDTO) {
		if (appFormDTO != null && appFormDTO.getWorkflowItemId() != null) {
			WorkflowGeneralInformationDTO workflowGeneralInformationDTO = getWorkflowInfo(
					appFormDTO.getWorkflowItemId().longValue());
			return workflowGeneralInformationDTO.getCurrentStepName();
		}
		return "";
	}

	private WorkflowGeneralInformationDTO getWorkflowInfo(Long workFlowItemId) {
		String usrId = SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
		return workflowService.getWorkflowGeneralInformation(workFlowItemId + "", usrId);
	}
}
