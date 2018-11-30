package lu.wealins.liability.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

@RolesAllowed("rest-user")
@Path("assetManagerStrategy")
@Produces(MediaType.APPLICATION_JSON)
public interface AssetManagerStrategyRESTService {

	/**
	 * Retrieve all agent categories.
	 * 
	 * @return a list of agent categories.
	 */
	@GET
	@Path("/assetManager/{assetManagerId}")
	Collection<AssetManagerStrategyDTO> getAssetManagerStrategies(@Context SecurityContext context, @PathParam("assetManagerId") String assetManagerId);
	
	@POST
	@Path("/assetManager/save")
	AssetManagerStrategyDTO save(@Context SecurityContext context, AssetManagerStrategyDTO request);
}
