/**
* 
*/
package lu.wealins.webia.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;
import lu.wealins.webia.core.service.AssetManagerStrategyService;
import lu.wealins.webia.ws.rest.AssetManagerStrategyRESTService;

/**
 * The implementation of the asset manager rest service.
 * 
 * @author oro
 *
 */
@Component
public class AssetManagerStrategyRESTServiceImpl implements AssetManagerStrategyRESTService {

	@Autowired
	private AssetManagerStrategyService managerStrategyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.AssetManagerStrategyRESTService#save(javax.ws.rs.core.SecurityContext, lu.wealins.webia.ws.rest.dto.AssetManagerStrategyDTO)
	 */
	@Override
	public AssetManagerStrategyDTO save(SecurityContext context, AssetManagerStrategyDTO strategy) {
		return managerStrategyService.save(strategy);
	}

}
