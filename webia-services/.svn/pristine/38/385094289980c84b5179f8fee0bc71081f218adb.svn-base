package lu.wealins.webia.services.core.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.core.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private static final String PRODUCT_CAPI = "PRODUCT_CAPI";

	@Autowired
	private ApplicationParameterService applicationParameterService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ProductService#isCapiProduct(java.lang.String)
	 */
	@Override
	public boolean isCapiProduct(String productCode) {
		Assert.notNull(productCode, "Product code cannot be null.");
		return StringUtils.isNotEmpty(productCode) && getCapiProducts().contains(productCode);
	}

	private Set<String> getCapiProducts() {
		return applicationParameterService.getStringValues(PRODUCT_CAPI).stream().collect(Collectors.toSet());
	}

}
