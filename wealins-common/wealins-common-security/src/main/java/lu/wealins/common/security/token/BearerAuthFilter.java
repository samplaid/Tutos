package lu.wealins.common.security.token;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public class BearerAuthFilter implements ClientRequestFilter {

	public static final String AUTH_HEADER_PREFIX = "Bearer ";

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {

		String authHeader = SecurityContextThreadLocal.get().getKeycloakSecurityContext().getTokenString();
		if (!authHeader.startsWith(AUTH_HEADER_PREFIX)) {
			authHeader = AUTH_HEADER_PREFIX + authHeader;
		}
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, authHeader);
	}

}