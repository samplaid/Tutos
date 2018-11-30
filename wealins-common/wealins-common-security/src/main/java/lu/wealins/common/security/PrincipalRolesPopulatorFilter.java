package lu.wealins.common.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessToken.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class PrincipalRolesPopulatorFilter implements ContainerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(PrincipalRolesPopulatorFilter.class);

	private String piaRootContextURL;

	/**
	 * Permit to know if the request comes from external or internal network
	 */
	@Value("${ENVIRONMENT}")
	private String jbossEnvironment;

	/**
	 * Hack for developer to display all functionalities
	 */
	@Value("${IT:NODEV}")
	private String jbossEnvironmentForDevelopper;

	public static final String EXTERN = "EXTERN";

	private final static String GET_USER_CONTEXT_ENDPOINT = "GetUserContext";

	private final static String HEADER_IMPERSONATED = "Impersonated";
	private final static String HEADER_IMPERSONATED_TYPE = "impersonatedType";

	private final static String FUNCTIONALITY_FROM_EXTERN_EMVIRONMENT = "_disabled";

	private final static String SUFFIX_EXTERN_FUNCTIONALITY = "_ext";

	@Override
	public void filter(ContainerRequestContext crc) throws IOException {
		@SuppressWarnings("unchecked")
		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) crc
				.getSecurityContext().getUserPrincipal();
		if (principal == null) {
			log.error("Principal is null");
			throw new RuntimeException("Principal must not be null");
		}
		KeycloakSecurityContext keycloakSecurityContext = principal.getKeycloakSecurityContext();
		AccessToken token = keycloakSecurityContext.getToken();
		Access realmAccess = token.getRealmAccess();
		String preferredUsername = token.getPreferredUsername();

		// Load ACL Context from acl-services
		String impersonatUser = null;
		String impersonatType = null;
		if (! EXTERN.equals(jbossEnvironment)){
			impersonatUser = crc.getHeaderString(HEADER_IMPERSONATED);
			impersonatType = crc.getHeaderString(HEADER_IMPERSONATED_TYPE);
		}
		
		ACLContext aclContext = getACLContext(keycloakSecurityContext, realmAccess, impersonatUser);
		ResteasyProviderFactory.pushContext(ACLContext.class, aclContext);

		log.debug("Pushed ACLContext into RestEasyPRoviderFactry.");

		// Add Role of ALContext coming from acl-services
		enrichRolesUser(realmAccess, preferredUsername, aclContext, impersonatType);
	}

	private void enrichRolesUser(Access realmAccess, String preferredUsername, ACLContext aclContext, String impersonatType) {
		if (aclContext.getFunctionalities() != null) {
			for (Functionality functionnality : aclContext.getFunctionalities()) {
				// HACK DEVELOPPER
				if (!("DEV").equals(jbossEnvironmentForDevelopper)) {
					// For extern environment
					if (jbossEnvironment.equals(EXTERN) && !functionnality.getName().contains(SUFFIX_EXTERN_FUNCTIONALITY)) {
						functionnality.setName(functionnality.getName() + FUNCTIONALITY_FROM_EXTERN_EMVIRONMENT);
					}
				}
				realmAccess.addRole(functionnality.getName());
				log.debug("Adding role {} to user {}", functionnality, preferredUsername);
			}
			// Add the jboss environment or extern impersonatType in order to know if the request is intern or extern
			String InternOrExtern = (impersonatType != null && EXTERN.equals(impersonatType)) ? impersonatType : jbossEnvironment;
			aclContext.getFunctionalities().add(new Functionality(InternOrExtern));
		}
	}

	private ACLContext getACLContext(KeycloakSecurityContext keycloakSecurityContext, Access realmAccess, String impersonated) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		Response response = null;

		// Add a header to impersonated only if this is necessary
		if (impersonated != null && !impersonated.isEmpty()) {
			response = client.target(getPiaRootContextURL() + GET_USER_CONTEXT_ENDPOINT).request()
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakSecurityContext.getTokenString())
					.header(HEADER_IMPERSONATED, impersonated)
					.get();
		} else {

			response = client.target(getPiaRootContextURL() + GET_USER_CONTEXT_ENDPOINT).request()
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakSecurityContext.getTokenString()).get();
		}
		if (response == null || !response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL)) {
			log.error("Impossible to get the user ACL context for user {}", keycloakSecurityContext.getToken().getPreferredUsername());
			throw new RuntimeException("Impossible to get the user ACL context for user " + keycloakSecurityContext.getToken().getPreferredUsername());
		}
		ACLContext aclContext = response.readEntity(new GenericType<ACLContext>() {
		});
		return aclContext;
	}

	/**
	 * @return the piaRootContextURL
	 */
	public String getPiaRootContextURL() {
		return piaRootContextURL;
	}

	/**
	 * @param piaRootContextURL the piaRootContextURL to set
	 */
	public void setPiaRootContextURL(String piaRootContextURL) {
		this.piaRootContextURL = piaRootContextURL;
	}

}
