#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.security.ACLContext;
import ${package}.core.persistence.entity.SomethingEntity;
import ${package}.core.service.SomethingService;
import ${package}.ws.rest.SomethingRESTService;

@Component
public class SomethingRESTServiceImpl implements SomethingRESTService {

	@Autowired
	private SomethingService somethingService;

	@Override
	public Response action(SecurityContext context, ACLContext aclContext) {
		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) context
				.getUserPrincipal();

		String name = principal.getName();
		System.out.println("Name: " + name);
		System.out.println("aclContext: " + aclContext.getUser());

		List<SomethingEntity> entities = somethingService.doSomething();

		// TODO Mapper entities --> DTO ?
		return Response.ok(entities).build();
	}

}
