/**
 * 
 */
package lu.wealins.common.security.token;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

/**
 * @author bqj9j
 *
 */
public class SecurityContextThreadLocal {
	private static final ThreadLocal<KeycloakPrincipal<KeycloakSecurityContext>> principalThreadLocal = new ThreadLocal<KeycloakPrincipal<KeycloakSecurityContext>>();

	public static void set(KeycloakPrincipal<KeycloakSecurityContext> principal) {
		principalThreadLocal.set(principal);
	}

	public static void unset() {
		principalThreadLocal.remove();
	}

	public static KeycloakPrincipal<KeycloakSecurityContext> get() {
		return principalThreadLocal.get();
	}

}
