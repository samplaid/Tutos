package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.AssetManagerStrategyService;
import lu.wealins.liability.services.ws.rest.AssetManagerStrategyRESTService;
import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

@Component
public class AssetManagerStrategyRESTServiceImpl implements AssetManagerStrategyRESTService {
		
	@Autowired
	private AssetManagerStrategyService assetManagerStrategyService;

	@Override
	public Collection<AssetManagerStrategyDTO> getAssetManagerStrategies(SecurityContext context, String assetManagerId) {
		return assetManagerStrategyService.getAssetManagerStrategies(assetManagerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.AssetManagerStrategyRESTService#save(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO)
	 */
	@Override
	public AssetManagerStrategyDTO save(SecurityContext context, AssetManagerStrategyDTO request) {
		return assetManagerStrategyService.saveOrUpdate(request);
	}

}
