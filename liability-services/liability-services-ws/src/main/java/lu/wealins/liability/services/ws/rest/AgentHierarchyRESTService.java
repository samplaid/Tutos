package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AgentHierarchyDTO;
import lu.wealins.common.dto.liability.services.AgentHierarchyPageableRequest;
import lu.wealins.common.dto.liability.services.AgentHierarchyRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

@RolesAllowed("rest-user")
@Path("/agentHierarchy")
@Produces(MediaType.APPLICATION_JSON)
public interface AgentHierarchyRESTService {
	
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<AgentHierarchyDTO> search(@Context SecurityContext context, AgentHierarchyPageableRequest criteria);
	
	
	@POST
	@Path("/findByCriteria")
	@Consumes(MediaType.APPLICATION_JSON)
	Collection<AgentHierarchyDTO> findAgentHierarchies(@Context SecurityContext context, AgentHierarchyRequest criteria);
}
