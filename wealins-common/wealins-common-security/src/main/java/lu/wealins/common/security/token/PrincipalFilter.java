package lu.wealins.common.security.token;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class PrincipalFilter implements ContainerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(PrincipalFilter.class);


	@SuppressWarnings("unchecked")
	@Override
	public void filter(ContainerRequestContext crc) throws IOException {
		SecurityContextThreadLocal.unset();
		

		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) crc
				.getSecurityContext().getUserPrincipal();
		SecurityContextThreadLocal.set(principal);
		log.debug("Added principal {} into threadLocal", principal.getKeycloakSecurityContext().getToken().getPreferredUsername());
	}

}
