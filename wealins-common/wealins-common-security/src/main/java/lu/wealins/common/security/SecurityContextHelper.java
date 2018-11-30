package lu.wealins.common.security;

import javax.ws.rs.core.SecurityContext;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

import lu.wealins.common.security.token.SecurityContextThreadLocal;

public final class SecurityContextHelper {

	private SecurityContextHelper() {
	}

	/**
	 * Get the preferred user name from the security context.
	 * 
	 * @param context The security context.
	 * @return The preferred user name.
	 */
	public static String getPreferredUsername(SecurityContext context) {
		@SuppressWarnings("unchecked")
		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) context.getUserPrincipal();
		return principal.getKeycloakSecurityContext().getToken().getPreferredUsername();
	}

	public static String getPreferredUsername() {
		return SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername();
	}
}
