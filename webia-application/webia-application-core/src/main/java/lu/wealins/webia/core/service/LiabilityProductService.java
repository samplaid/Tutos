package lu.wealins.webia.core.service;

import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;

public interface LiabilityProductService {

	ProductDTO getProduct(String productId);

	/**
	 * return if the product is a capitalization one
	 * 
	 * @return true or false
	 */
	boolean isCapiProduct(String productId);

	String getCountryCode(String productCd);

	String getGeneralCondition(PolicyDTO policy);
}
