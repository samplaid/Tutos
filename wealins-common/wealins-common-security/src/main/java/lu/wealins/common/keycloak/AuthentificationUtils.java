package lu.wealins.common.keycloak;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Manage the authentification by web service
 * 
 * @author TEY
 *
 */
@Component
public class AuthentificationUtils {

	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(AuthentificationUtils.class);

	/**
	 * The keycloak
	 */
	private KeycloakUtils keycloak;

	/**
	 * Jasypt bean to manage password
	 */
	private StandardPBEStringEncryptor jasypt;

	/**
	 * Authentification by user and password
	 * 
	 * @param userLogin the user
	 * @param password the password
	 * @return the token in case of success
	 * @throws AuthentificationException in case of bad authentification
	 */
	public AccessTokenResponse authentification(String userLogin, String password) throws AuthentificationException {
		if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(userLogin)) {
			log.debug("Manage authentification for the user {}", userLogin);

			AccessTokenResponse accessToken = keycloak.getAccessToken(userLogin, password);
			if (accessToken != null) {
				log.debug("Authentification succeed for the user {}", userLogin);
				return accessToken;
			}
		}
		log.error("Authentification request has not the necessary information : password and login are required");
		throw new AuthentificationException("Missing information : password and login are required");
	}

	/**
	 * Authentification by user and encrypted password
	 * 
	 * @param userLogin the user
	 * @param password the encrypted password
	 * @return the token in case of success
	 * @throws AuthentificationException in case of bad authentification
	 */
	public AccessTokenResponse authentificationEncrypted(String userLogin, String password) throws AuthentificationException {
		if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(userLogin)) {

			log.debug("Manage authentification for the user {}", userLogin);
			AccessTokenResponse accessToken = keycloak.getAccessToken(userLogin, decrypt(password));
			if (accessToken != null) {
				log.debug("Authentitication succeed for the user {}", userLogin);
				return accessToken;
			}
		}
		log.error("Authentification request has not the necessary information : password and login are required");
		throw new AuthentificationException("Missing information : password and login are required");
	}

	/**
	 * Decrypt password with jasypt
	 * 
	 * @param password the password to decrypt
	 * @return the decrypted password
	 * @throws AuthentificationException in case of exception for the decryption
	 */
	private String decrypt(String password) throws AuthentificationException {
		try {
			return jasypt.decrypt(password);
		} catch (EncryptionOperationNotPossibleException | EncryptionInitializationException e) {
			log.error("Jasypt Decryption password failed : {}", e.getMessage());
			throw new AuthentificationException("Authentification failed");
		}
	}

	/**
	 * @return the keycloak
	 */
	public KeycloakUtils getKeycloak() {
		return keycloak;
	}

	/**
	 * @param keycloak the keycloak to set
	 */
	public void setKeycloak(KeycloakUtils keycloak) {
		this.keycloak = keycloak;
	}

	/**
	 * @return the jasypt
	 */
	public StandardPBEStringEncryptor getJasypt() {
		return jasypt;
	}

	/**
	 * @param jasypt the jasypt to set
	 */
	public void setJasypt(StandardPBEStringEncryptor jasypt) {
		this.jasypt = jasypt;
	}
}
