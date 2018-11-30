package lu.wealins.webia.core.service;

import java.util.List;

import lu.wealins.common.dto.liability.services.OptionDetailDTO;

public interface LiabilityOptionDetailService {

	/**
	 * Get all the client policy relationship roles
	 * 
	 * @return The CPR Roles.
	 * 
	 */
	List<OptionDetailDTO> getCPRRoles();

	List<OptionDetailDTO> getCPRRoles(String productId, boolean productCapi, boolean yearTerm);

	/**
	 * Get the pricing frequency where FK_OPTIONSOPT_ID = 'PRICING_FREQUENCY'
	 * 
	 * @return set of pricing frequency
	 */
	List<OptionDetailDTO> getContextualizedPricingFrequencies();

}
