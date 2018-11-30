package lu.wealins.common.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeycloakUtils {

	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(KeycloakUtils.class);

	private String realm;

	private String clientId;

	private String serverUrl;

	private String userLogin;

	private String password;

	private String clientSecret;

	private Keycloak keycloak;

	public Keycloak getInstance() {
		if (keycloak == null) {
			log.debug("Get keycloak instance for the user login {}", userLogin);
			keycloak = Keycloak.getInstance(serverUrl, realm, userLogin, password, clientId, clientSecret);
		}
		return keycloak;
	}

	public Keycloak getInstance(String userLogin, String password) {
		if (keycloak == null) {
			log.debug("Get keycloak instance for the user login {}", userLogin);
			keycloak = Keycloak.getInstance(serverUrl, realm, userLogin, password, clientId, clientSecret);
		}
		return keycloak;
	}

	public AccessTokenResponse getAccessToken() {
		return getInstance().tokenManager().grantToken();
	}

	public AccessTokenResponse getAccessToken(String userLogin, String password) {
		return getInstance(userLogin, password).tokenManager().grantToken();
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

	/**
	 * @return the realm
	 */
	public String getRealm() {
		return realm;
	}

	/**
	 * @param realm the realm to set
	 */
	public void setRealm(String realm) {
		this.realm = realm;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * @param serverUrl the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

}