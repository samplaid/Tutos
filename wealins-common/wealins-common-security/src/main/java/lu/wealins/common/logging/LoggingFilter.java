package lu.wealins.common.logging;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Filter that adds current user name in the Mapped Diagnostic Context so the user name is propagated throughout the logs.
 * 
 * @author bqj9j
 */
@Provider
@Component
@Priority(Priorities.USER) // Obviously lower than Authentication filters
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

	protected static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	private static final String USER_MDC_KEY = "user";

	/**
	 * Adds user name from keycloak security context principal to MDC
	 * 
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		SecurityContext securityContext = requestContext.getSecurityContext();
		if (securityContext == null) {
			logger.warn("Security context is null for request '{}'", requestContext.getUriInfo().getAbsolutePath());
			return;
		}

		Principal userPrincipal = securityContext.getUserPrincipal();
		if (userPrincipal == null) {
			logger.warn("UserPrincipal is null for request '{}'", requestContext.getUriInfo().getAbsolutePath());
			return;
		}
		if (!(userPrincipal instanceof KeycloakPrincipal)) {
			logger.warn("UserPrincipal is not an instance of KeycloakPrincipal");
			return;
		}

		@SuppressWarnings("unchecked")
		KeycloakSecurityContext keycloakSecurityContext = ((KeycloakPrincipal<KeycloakSecurityContext>) userPrincipal)
				.getKeycloakSecurityContext();

		String username = keycloakSecurityContext.getToken().getPreferredUsername();
		if (!StringUtils.isEmpty(username)) {
			MDC.put(USER_MDC_KEY, username);
		}
	}

	/**
	 * Clears MDC after execution
	 * 
	 * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.ContainerRequestContext, javax.ws.rs.container.ContainerResponseContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		MDC.clear();
	}

}
