package lu.wealins.liability.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.GetProductSpecificitiesRequest;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.ProductLightDTO;
import lu.wealins.liability.services.core.business.ProductService;
import lu.wealins.liability.services.ws.rest.ProductRESTService;
import lu.wealins.liability.services.ws.rest.exception.ObjectNotFoundException;

@Component
public class ProductRESTServiceImpl implements ProductRESTService {

	@Autowired
	private ProductService productService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductRESTService#getActiveProductLights(javax.ws.rs.core.SecurityContext)
	 */
	@Override
	public List<ProductLightDTO> getActiveProductLights(SecurityContext context) {

		return productService.getActiveLightProducts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductRESTService#getProduct(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public ProductDTO getProduct(SecurityContext context, String prdId) {

		ProductDTO product = productService.getProduct(prdId);

		if (product == null) {
			throw new ObjectNotFoundException(ProductDTO.class, prdId);
		}

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductRESTService#getCountryCode(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public String getCountryCode(SecurityContext context, String productId) {
		return productService.getCountryCode(productId);
	}

	@Override
	public String getProductSpecificities(SecurityContext context, GetProductSpecificitiesRequest request) {
		return productService.getProductSpecificities(request);
	}

}
