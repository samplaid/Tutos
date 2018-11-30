package lu.wealins.webia.core.annotation;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.service.WebiaWorkflowQueueService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.webia.core.utils.SecurityStepAnnotationUtils;


@Component
@Aspect
public class UpdateStepAccessAspect {

	private static final String RESULT_MUST_BE_AN_INSTANCE_OF_STEP_DTO = "Result must be an instance of StepDTO.";

	@Autowired
	private WebiaWorkflowUserService workflowUserService;
	@Autowired
	private WebiaWorkflowQueueService workflowQueueService;
	@Autowired
	private SecurityStepAnnotationUtils securityStepAnnotationUtils;
	@Autowired
	private WebiaApplicationParameterService applicationParameterService;

	private final Logger log = LoggerFactory.getLogger(UpdateStepAccessAspect.class);

	@AfterReturning(pointcut = "@annotation(UpdateStepAccess) && execution(* lu.wealins.webia.ws.rest.impl.*.*(..))", returning = "result")
	public void updateStepAccess(JoinPoint joinPoint, Object result) throws Throwable {

		if (!(result instanceof StepDTO)) {
			throw new IllegalArgumentException(RESULT_MUST_BE_AN_INSTANCE_OF_STEP_DTO);
		}

		StepDTO step = (StepDTO) result;
		String usrId = securityStepAnnotationUtils.getUserId(joinPoint);
		String login = workflowUserService.getLogin(usrId);
		
		Set<String> superUsers = applicationParameterService.getLoginsByPassStepAccess().stream().map(x -> x.toLowerCase()).collect(Collectors.toSet());

		log.debug("Connected user: {}, super users: {}.", login, superUsers.toArray(new String[superUsers.size()]));
		if (login == null || superUsers.contains(login.toLowerCase())) {
			step.setUpdatable(Boolean.TRUE);
		} else {
			String workflowQueueId = workflowQueueService.getWorkflowQueueId(step.getWorkflowItemId() + "", usrId);
			Boolean isUpdatable = BooleanUtils.isTrue(workflowQueueService.isAssignTo(workflowQueueId, usrId));
			if ( isUpdatable && isAutoValidationForCPS(step, usrId) ){
				isUpdatable = false;
				if (step.getErrors() == null){
					step.setErrors(new ArrayList<>());
				}
				step.getErrors().add("CPS 1 and CPS 2 must be different ! You cannot have edition access to this page. Please assign this item to someone else.");
			};
			step.setUpdatable(isUpdatable);
		}
	}

	/**
	 * Check if the current user is different of the first CPS user only on steps for CPS 2.
	 * 
	 * @param step The step.
	 * @param currentUser The current user.
	 * @return True, if successful.
	 */
	public static boolean isAutoValidationForCPS(StepDTO step, String currentUser) {

		StepTypeDTO stepType = StepTypeDTO.getStepType(step.getStepWorkflow());

		if (StepTypeDTO.CPS2_GROUP.contains(stepType)) {
			String firstCpsUser = step.getFirstCpsUser();
			if (StringUtils.isNotBlank(firstCpsUser)){
				return firstCpsUser.equalsIgnoreCase(currentUser);
			}
		}

		return false;
	}

}
