package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.GetProductSpecificitiesRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.AppFormPolicyDataService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityProductServiceImpl implements LiabilityProductService {

	private static final String PRODUCT_ID_CANNOT_BE_NULL = "Product id cannot be null.";
	private static final String LIABILITY_LOAD_PRODUCT = "liability/product/";
	private static final String LIABILITY_PRODUCT_SPECIFICITIES = "liability/product/productSpecificities";
	private static final String PRODUCT_CAPI = "PRODUCT_CAPI";

	private Set<String> capiProducts = new HashSet<>();

	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private WebiaApplicationParameterService applicationParameterService;

	@Autowired
	private AppFormPolicyDataService appFormPolicyDataService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityProductService#getProduct(java.lang.String)
	 */
	@Override
	public ProductDTO getProduct(String productId) {
		Assert.notNull(productId, PRODUCT_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_LOAD_PRODUCT, productId, ProductDTO.class);
	}

	@Override
	public String getCountryCode(String productCd) {
		if (StringUtils.isEmpty(productCd)) {
			return null;
		}

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("productId", productCd);

		return restClientUtils.get(LIABILITY_LOAD_PRODUCT, "countryCode", queryParams, String.class);
	}

	@Override
	public boolean isCapiProduct(String productId) {
		if (CollectionUtils.isEmpty(capiProducts)) {
			capiProducts = applicationParameterService.getApplicationParameters(PRODUCT_CAPI).stream().collect(Collectors.toSet());
		}
		return capiProducts.contains(productId);
	}

	@Override
	public String getGeneralCondition(PolicyDTO policy) {
		GetProductSpecificitiesRequest request = new GetProductSpecificitiesRequest();
		if (policy != null && policy.getProduct() != null) {
			request.setPrdId(policy.getProduct().getPrdId());
			Collection<AppFormDTO> appForms = appFormPolicyDataService.getAppFormByPolicy(policy.getPolId());
			if (appForms != null) {
				List<AppFormDTO> appFormsList = new ArrayList<AppFormDTO>(appForms);
				if (!appFormsList.isEmpty()) {
					request.setStartDate(appFormsList.get(0).getCreationDt());
				}
			}

		}
		return restClientUtils.post(LIABILITY_PRODUCT_SPECIFICITIES, request, String.class);
	}
}
