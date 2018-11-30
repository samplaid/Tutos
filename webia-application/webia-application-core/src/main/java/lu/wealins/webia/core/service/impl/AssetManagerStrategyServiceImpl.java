/**
* 
*/
package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.AssetManagerStrategyDTO;
import lu.wealins.webia.core.service.AssetManagerStrategyService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.impl.exception.SequenceException;

/**
 * The implementation of the asset manager strategy
 * 
 * @author oro
 *
 */
@Service
public class AssetManagerStrategyServiceImpl implements AssetManagerStrategyService {

	private static final String WEBIA_SERVICES_NEXT_ID = "webia/sequence/";
	private static final String LIABILITY_SAVE_STRATEGY = "liability/assetManagerStrategy/assetManager/save";
	private static final String ASSET_MANAGER_STRATEGY = "AMS";

	@Autowired
	private RestClientUtils restClientUtils;

	/* (non-Javadoc)
	 * @see lu.wealins.webia.core.service.AssetManagerStrategyService#save(lu.wealins.webia.ws.rest.dto.AssetManagerStrategyDTO)
	 */
	@Override
	public AssetManagerStrategyDTO save(AssetManagerStrategyDTO strategy) {
		Assert.notNull(strategy);
		setId(strategy);
		return restClientUtils.post(LIABILITY_SAVE_STRATEGY, strategy, AssetManagerStrategyDTO.class);
	}

	@Override
	public void setId(AssetManagerStrategyDTO managerStrategy) {
		if (managerStrategy.getAmsId() == null) {
			String fdsId = restClientUtils.get(WEBIA_SERVICES_NEXT_ID, ASSET_MANAGER_STRATEGY, String.class);
			try {
				managerStrategy.setAmsId(Long.valueOf(fdsId));
			} catch (NumberFormatException e) {
				throw new SequenceException("Cannot convert the generated sequence to a number. Generated sequence is " + fdsId);
			}
		}
	}

}
