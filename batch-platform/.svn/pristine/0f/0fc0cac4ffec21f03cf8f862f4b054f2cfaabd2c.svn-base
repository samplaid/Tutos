package lu.wealins.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lu.wealins.service.RestUtilityService;
import lu.wealins.utils.KeycloakUtils;

@Component
public class RestUtilityServiceImpl implements RestUtilityService {

	private Log logger = LogFactory.getLog(RestUtilityServiceImpl.class);

	@Autowired
	KeycloakUtils keycloackUtils;

	@Override
	public <Response, Request> Response post(String url, Request request, Class<Response> responseType) {
		logger.debug("Token ...");
		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();
		logger.debug("Token OK" + token);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Request> entity = new HttpEntity<Request>(request, headers);
		RestTemplate template = new RestTemplate();
		
		logger.debug("POST request " + entity.getClass().getName() + " - url: " + url + ".");
		return template.postForObject(url, entity, responseType);
	}

}
