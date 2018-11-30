package lu.wealins.webia.core.utils;

import javax.ws.rs.core.SecurityContext;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.core.service.WebiaWorkflowUserService;

@Component
public class SecurityStepAnnotationUtils {

	@Autowired
	private WebiaWorkflowUserService workflowUserService;

	public String getUserId(JoinPoint joinPoint) {
		for (Object arg : joinPoint.getArgs()) {
			// Get the request object
			if (arg instanceof SecurityContext) {
				return workflowUserService.getUserId((SecurityContext) arg);
			}
		}

		throw new IllegalStateException("User id has not been found.");
	}

}
