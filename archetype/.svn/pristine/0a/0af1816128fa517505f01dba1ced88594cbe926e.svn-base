#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.security.ACLContext;

@Path("something")
@Produces(MediaType.APPLICATION_JSON)
public interface SomethingRESTService {

	@GET
	@Path("action")
	//@RolesAllowed("something_manager")
	Response action(@Context SecurityContext context, @Context ACLContext acl);

}
