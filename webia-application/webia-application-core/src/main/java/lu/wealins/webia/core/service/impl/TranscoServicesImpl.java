package lu.wealins.webia.core.service.impl;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lu.wealins.common.security.token.SecurityContextThreadLocal;
import lu.wealins.webia.core.service.TranscoServices;
import lu.wealins.webia.ws.rest.request.SearchTranscoRequest;
import lu.wealins.webia.ws.rest.request.SearchTranscoResponse;

/**
 * The NAVsServices implementation
 * 
 * @author bqv55
 *
 */
@Service
public class TranscoServicesImpl implements TranscoServices {
	private static final Logger log = LoggerFactory.getLogger(TranscoServicesImpl.class);

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	/**
	 * The name matching the url for searching transco
	 */
	private static final String SEARCH_TRANSCO_URL_REWRITE_NAME = "transcoSearch";

	@Override
	public SearchTranscoResponse searchTransco(SearchTranscoRequest searchTranscoRequest) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		Response response = client.target(piaRootContextURL + SEARCH_TRANSCO_URL_REWRITE_NAME).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityContextThreadLocal.get().getKeycloakSecurityContext().getTokenString())
				.post(Entity.entity(searchTranscoRequest, "application/json"));

		if (response == null || !response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL)) {
			log.error("Error during loading the NAV of a policiy's fund by {}", SecurityContextThreadLocal.get().getKeycloakSecurityContext().getTokenString());
			throw new RuntimeException("Error during loading the NAV of a policiy's fund by " + SecurityContextThreadLocal.get().getKeycloakSecurityContext().getToken().getPreferredUsername());
		}

		return response.readEntity(new GenericType<SearchTranscoResponse>() {
		});
	}

}
