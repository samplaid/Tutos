package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;

public interface AssetManagerStrategyService {

	/**
	 * Get asset manager strategies according to the asset manager.
	 * 
	 * @param assetManagerId The asset manager id.
	 * @return The asset manager ids.
	 */
	Collection<AssetManagerStrategyDTO> getAssetManagerStrategies(String assetManagerId);
	
	/**
	 * Save or update the asset manager strategy
	 * @param managerStrategy the object to save or update
	 * @return an updated asset manager strategy
	 */
	AssetManagerStrategyDTO saveOrUpdate(AssetManagerStrategyDTO managerStrategy);
	
	/**
	 * Bulk save or update a set of the asset manager strategies
	 * @param managerStrategies a set of the asset manager strategies
	 * @return a set of the asset manager strategies.
	 */
	Collection<AssetManagerStrategyDTO> bulkSaveOrUpdate(Collection<AssetManagerStrategyDTO> managerStrategies);

	Collection<AssetManagerStrategyDTO> bulkSaveOrUpdate(Collection<AssetManagerStrategyDTO> managerStrategies, String agentId);
}
