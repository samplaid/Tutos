package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.ProductLineService;
import lu.wealins.liability.services.ws.rest.ProductLineRESTService;
import lu.wealins.common.dto.liability.services.ProductLineDTO;

@Component
public class ProductLineRESTServiceImpl implements ProductLineRESTService {

	@Autowired
	private ProductLineService productLineService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductLineRESTService#getProductLines(java.lang.Integer, java.util.List)
	 */
	@Override
	public Collection<ProductLineDTO> getProductLines(String prdId, List<String> controls) {
		return productLineService.getProductLines(prdId, controls);
	}

	public Collection<ProductLineDTO> getFilteredProductLines(String prdId, List<String> controls) {
		return productLineService.getFilteredProductLines(prdId, controls);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductLineRESTService#getProductLine(java.lang.String)
	 */
	@Override
	public ProductLineDTO getProductLine(SecurityContext context, String prlId) {
		return productLineService.getProductLine(prlId);
	}

}
