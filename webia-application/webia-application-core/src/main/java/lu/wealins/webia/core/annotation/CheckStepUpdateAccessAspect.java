package lu.wealins.webia.core.annotation;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaWorkflowQueueService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.utils.SecurityStepAnnotationUtils;
import lu.wealins.webia.ws.rest.impl.exception.ReportExceptionHelper;
import lu.wealins.webia.ws.rest.impl.exception.StepException;

@Component
@Aspect
public class CheckStepUpdateAccessAspect {

	@Autowired
	private WebiaWorkflowUserService workflowUserService;
	@Autowired
	private WebiaWorkflowQueueService workflowQueueService;
	@Autowired
	private SecurityStepAnnotationUtils securityStepAnnotationUtils;
	@Autowired
	private WebiaApplicationParameterService applicationParameterService;
	@Autowired
	private LiabilityWorkflowService workflowService;

	private final Logger log = LoggerFactory.getLogger(CheckStepUpdateAccessAspect.class);

	@Before("@annotation(CheckStepUpdateAccess) && execution(* lu.wealins.webia.ws.rest.impl.*.*(..))")
	public void checkStepUpdateAccess(JoinPoint joinPoint) throws Throwable {
		StepDTO step = getStep(joinPoint);
		String usrId = securityStepAnnotationUtils.getUserId(joinPoint);
		String login = workflowUserService.getLogin(usrId);
		Set<String> superUsers = applicationParameterService.getLoginsByPassStepAccess().stream().map(x -> x.toLowerCase()).collect(Collectors.toSet());

		log.debug("Connected user: {}, super users: {}.", login, superUsers.toArray(new String[superUsers.size()]));

		String workflowItemId = step.getWorkflowItemId() + "";
		WorkflowGeneralInformationDTO workflowGeneralInformation = workflowService.getWorkflowGeneralInformation(workflowItemId, usrId);

		if (workflowGeneralInformation == null) {
			throw new IllegalStateException("Cannot retrieve workflow general information for workflow item " + workflowItemId + ".");
		}

		if (StringUtils.isBlank(step.getStepWorkflow()) || !step.getStepWorkflow().equalsIgnoreCase(workflowGeneralInformation.getCurrentStepName())) {
			ReportExceptionHelper.throwIfErrorsIsNotEmpty(Arrays.asList("The workflow has been moved to the step '" + workflowGeneralInformation.getCurrentStepName() + "'."), step,
					StepException.class);
		}

		if (login == null || superUsers.contains(login.toLowerCase())) {
			log.debug("User " + login + " is a super user.");
		} else {
			String workflowQueueId = workflowGeneralInformation.getQueueId();
			Boolean isUpdatable = workflowQueueService.isAssignTo(workflowQueueId, usrId);
			
			if (BooleanUtils.isFalse(isUpdatable)) {
				ReportExceptionHelper.throwIfErrorsIsNotEmpty(Arrays.asList("You are not allowed to update the step."), step, StepException.class);
			}
			
		}
	}

	private StepDTO getStep(JoinPoint joinPoint) {
		for (Object arg : joinPoint.getArgs()) {
			// Get the request object
			if (arg instanceof StepDTO) {
				return ((StepDTO) arg);
			}
		}

		throw new IllegalStateException("Step has not been found.");
	}

}
