package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface LiabilityProductLineService {

	/**
	 * Get product lines according to the product id and the control types.
	 * 
	 * @param prdId The product id.
	 * @param controls The control types.
	 * @return The product lines.
	 */
	Collection<ProductLineDTO> getProductLines(String prdId, List<String> controls);

	Collection<ProductLineDTO> getFilterProductLines(String prdId, List<String> controls);

	/**
	 * Get product line according to the product line id.
	 * 
	 * @param prlId The product line id.
	 * @return The product line.
	 */
	ProductLineDTO getProductLine(String prlId);

	/**
	 * Get the first matching product line with the given subscription form data
	 * 
	 * @param appForm
	 * @return
	 */
	ProductLineDTO findMatchingProductLine(AppFormDTO appForm);

	/**
	 * Get the first matching product line with the given additional form data
	 * 
	 * @param appForm
	 * @return
	 */
	ProductLineDTO findMatchingAdditionalProductLine(AppFormDTO appForm);
}
