package lu.wealins.liability.services.core.business;

import java.util.List;

import lu.wealins.common.dto.liability.services.GetProductSpecificitiesRequest;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.ProductLightDTO;

/**
 * ProductService is a service responsible to manipulate Product objects.
 *
 */
public interface ProductService {

	/**
	 * Get the active products.
	 * 
	 * @return The active products.
	 */
	List<ProductLightDTO> getActiveLightProducts();

	/**
	 * Get the product according its id.
	 * 
	 * @param prdId The product id.
	 * @return The product.
	 */
	ProductDTO getProduct(String prdId);

	String getCountryCode(String prdId);

	/**
	 * get product specificities
	 * 
	 * @param productId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	String getProductSpecificities(GetProductSpecificitiesRequest request);

}
