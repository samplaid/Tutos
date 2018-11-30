package lu.wealins.webia.core.utils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.webia.ws.rest.impl.exception.EntrepriseServiceBusException;

@Component
public class RestClientUtils {

	private static final String APPLICATION_JSON = "application/json";

	private static final Logger log = LoggerFactory.getLogger(RestClientUtils.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	public <T> T post(String esbRoute, Object jsonRequest, Class<T> responseType) {
		Response response = _post(esbRoute, jsonRequest);
		return response.readEntity(responseType);
	}

	public <T> T post(String esbRoute, Object jsonRequest, GenericType<T> genericType) {
		Response response = _post(esbRoute, jsonRequest);
		return response.readEntity(genericType);
	}

	public void post(String esbRoute, Object jsonRequest) {
		_post(esbRoute, jsonRequest);
	}
	
	private Response _post(String esbRoute, Object jsonRequest) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		String uri = piaRootContextURL + esbRoute;
		Response response = client.target(uri).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityContextThreadLocal.get().getKeycloakSecurityContext().getTokenString())
				.post(Entity.entity(jsonRequest, APPLICATION_JSON));

		log.info("POST " + uri);

		if (!response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL)) {
			String errorMessage = response.readEntity(String.class);
			log.error("Error with POST " + uri + " " + errorMessage);
			throw new EntrepriseServiceBusException(response.getStatus(), errorMessage);
		}

		return response;
	}

	public <T> T get(String esbRoute, String url, MultivaluedMap<String, Object> queryParams, Class<T> responseType) {
		Response response = _get(esbRoute, url, queryParams);
		return response.readEntity(responseType);
	}

	public <T> T get(String esbRoute, String url, MultivaluedMap<String, Object> queryParams, GenericType<T> genericType) {
		Response response = _get(esbRoute, url, queryParams);
		return response.readEntity(genericType);
	}

	public <T> T get(String esbRoute, String url, Class<T> responseType) {
		return get(esbRoute, url, new MultivaluedMapImpl<>(), responseType);
	}

	private Response _get(String esbRoute, String url, MultivaluedMap<String, Object> queryParams) {

		ResteasyClient client = new ResteasyClientBuilder().build();
		String uri = piaRootContextURL + esbRoute + url;
		Response response = client.target(uri).queryParams(queryParams).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityContextThreadLocal.get().getKeycloakSecurityContext().getTokenString()).get();

		log.info("GET " + uri);

		if (!response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL)) {
			String errorMessage = response.readEntity(String.class);
			log.error("Error with GET " + uri + " " + errorMessage);
			throw new EntrepriseServiceBusException(response.getStatus(), errorMessage);
		}
		return response;
	}

}
