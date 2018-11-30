package lu.wealins.utils;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class RestCallUtils {
	private static Log logger = LogFactory.getLog(RestCallUtils.class);

	/**
	 * get method
	 * 
	 * @return
	 */
	private static <T, U> ResponseEntity<U> get(String url, MultiValueMap<String, String> params, Class<T> classType, ParameterizedTypeReference<U> typeRef,
			KeycloakUtils keycloackUtils) {
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		RestTemplate template = new RestTemplate();
		logger.debug("Trying to get");

		// get URI and query parameters

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build();

		ResponseEntity<?> response = template.exchange(uriComponents.toUri(), HttpMethod.GET, entity, typeRef);
		logger.debug("Successfully got ");
		return (ResponseEntity<U>) response;
	}

	public static <T, U> ResponseEntity<U> get(String url, MultiValueMap<String, String> params, Class<T> classType, ParameterizedTypeReference<U> typeRef,
			KeycloakUtils keycloackUtils, Log logger) {
		RestCallUtils.logger = logger;
		return get(url, params, classType, typeRef, keycloackUtils);
	}

	public static <T, U> ResponseEntity<U> postRest(String url, Collection<T> datas, Class<T> classType, ParameterizedTypeReference<U> returnClassType, KeycloakUtils keycloackUtils, Log logger) {
		RestCallUtils.logger = logger;
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Collection<T>> entity = new HttpEntity<Collection<T>>(datas, headers);

		RestTemplate template = new RestTemplate();
		logger.debug("Trying to post ...");
		ResponseEntity<?> response = template.exchange(url, HttpMethod.POST, entity, returnClassType);
		logger.debug("Sucessfully posted ");
		return (ResponseEntity<U>) response;
	}

	public static <T, U> ResponseEntity<U> postRest(String url, T datas, Class<T> classType, ParameterizedTypeReference<U> returnClassType, KeycloakUtils keycloackUtils, Log logger) {
		RestCallUtils.logger = logger;
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<T> entity = new HttpEntity<T>(datas, headers);

		RestTemplate template = new RestTemplate();
		logger.debug("Trying to post ...");
		ResponseEntity<?> response = template.exchange(url, HttpMethod.POST, entity, returnClassType);
		logger.debug("Sucessfully posted ");
		return (ResponseEntity<U>) response;
	}
}
