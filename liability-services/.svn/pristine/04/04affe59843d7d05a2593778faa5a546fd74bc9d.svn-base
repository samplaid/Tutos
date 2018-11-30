package lu.wealins.liability.services.core.utils;

import javax.ws.rs.core.SecurityContext;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Component;

import lu.wealins.common.security.token.SecurityContextThreadLocal;

@Component
public class SecurityContextUtils {

	/**
	 * Get the preferred user name from the security context.
	 * 
	 * @param context The security context.
	 * @return The preferred user name.
	 */
	public String getPreferredUsername(SecurityContext context) {
		@SuppressWarnings("unchecked")
		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) context.getUserPrincipal();
		return principal.getKeycloakSecurityContext().getToken().getPreferredUsername();
	}

	public String getPreferredUsername() {
		return SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
	}
}
