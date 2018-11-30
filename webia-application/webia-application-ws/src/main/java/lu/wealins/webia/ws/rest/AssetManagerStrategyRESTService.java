/**
* 
*/
package lu.wealins.webia.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

/**
 * This interface exposes the functionalities of asset manager strategy
 * 
 * @author oro
 *
 */
@Path("assetManagerStrategy")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface AssetManagerStrategyRESTService {

	/**
	 * Save the asset manager strategy.
	 * 
	 * @param strategy the model to save
	 * @return an identifiable object.
	 */
	@Path("save")
	@POST
	AssetManagerStrategyDTO save(@Context SecurityContext context, AssetManagerStrategyDTO strategy);
}
