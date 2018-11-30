package lu.wealins.utils;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakUtils {

	@Value("${user.realm}")
	private String realm;

	@Value("${user.clientId}")
	private String clientId;

	@Value("${user.serverUrl}")
	private String serverUrl;

	@Value("${user.login}")
	private String userLogin;

	@Value("${user.password}")
	private String password;

	@Value("${user.clientSecret}")
	private String clientSecret;

	private Keycloak keycloak;

	public Keycloak getInstance() {
		if (keycloak == null) {
			keycloak = Keycloak.getInstance(serverUrl, realm, userLogin, password, clientId, clientSecret);
		}
		return keycloak;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KeycloakUtils [realm=" + realm + ", clientId=" + clientId + ", serverUrl=" + serverUrl + ", userLogin=" + userLogin + ", password=" + password + ", clientSecret=" + clientSecret + "]";
	}

	public AccessTokenResponse getAccessToken() {
		return getInstance().tokenManager().grantToken();
	}

}