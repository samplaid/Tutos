package lu.wealins.liability.services.core.business;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.liability.services.ProductLineDTO;

public interface ProductLineService {

	/**
	 * Get product lines according to the product id and the control types.
	 * 
	 * @param prdId The product id.
	 * @param controls The control types.
	 * @return The product lines.
	 */
	Collection<ProductLineDTO> getProductLines(String prdId, List<String> controls);

	Collection<ProductLineDTO> getFilteredProductLines(String prdId, List<String> controls);
	/**
	 * Get product line according to the product line id..
	 * 
	 * @param prlId The product line id.
	 * @return The product line.
	 */
	ProductLineDTO getProductLine(String prlId);

	String getProductLine(String polId, Integer coverage);
}
