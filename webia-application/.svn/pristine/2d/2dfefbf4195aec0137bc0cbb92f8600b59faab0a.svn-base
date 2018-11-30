package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.LiabilityPolicyCoverageService;
import lu.wealins.webia.core.service.LiabilityProductLineService;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityProductValueServiceImpl implements LiabilityProductValueService {
	private static final String POLICY_ID = "policyId";
	private static final String COVERAGE = "coverage";
	private static final String MXCLFA = "MXCLFA";

	private static final String PRODUCT_LINE_CANNOT_BE_FOUND = "Product line cannot be found.";
	private static final String FUND_CANNOT_BE_NULL = "Fund cannot be null.";
	private static final String POLICY_CANNOT_BE_NULL = "Policy cannot be null.";
	private static final String APPFORM_CANNOT_BE_NULL = "AppForm cannot be null.";
	private static final String PRODUCT_LINE_ID_CANNOT_BE_NULL = "Product Line id cannot be null.";
	private static final String LIABILITY_LOAD_PRODUCT_VALUE = "liability/productValue/";
	private static final String MANAGER_FINANCIAL_FEES = LIABILITY_LOAD_PRODUCT_VALUE + "fundManagementFee/";
	private static final String CONTRACT_MANAGEMENT_FEES = LIABILITY_LOAD_PRODUCT_VALUE + "policyManagementFee/";
	private static final String C13_RAT_FEES = LIABILITY_LOAD_PRODUCT_VALUE + "policyC13RatFee/";
	private static final String POLICY_FEES = LIABILITY_LOAD_PRODUCT_VALUE + "policyFee/";
	private static final String SURRENDER_FEES = LIABILITY_LOAD_PRODUCT_VALUE + "surrenderFee/";
	private static final String SWITCH_FEES = LIABILITY_LOAD_PRODUCT_VALUE + "switchFee/";
	private static final String FEE_BASIS = LIABILITY_LOAD_PRODUCT_VALUE + "feeBasis/";
	private static final String WITHDRAWAL_FEES = LIABILITY_LOAD_PRODUCT_VALUE + "withdrawalFee/";
	private static final String DEATH_COVERAGE_AMOUNT_OR_PERCENTAGE = LIABILITY_LOAD_PRODUCT_VALUE + "deathCoverageAmountOrPercentage/";
	private static final String PRODUCT_LINE = LIABILITY_LOAD_PRODUCT_VALUE + "productLine/";
	private static final BigDecimal TWO = new BigDecimal(2);

	@Autowired
	private LiabilityProductLineService productLineService;
	@Autowired
	private LiabilityProductValueService productValueService;
	@Autowired
	private LiabilityPolicyCoverageService policyCoverageService;

	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(LiabilityProductValueServiceImpl.class);

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public ProductValueDTO getManagerFinancialFees(String fundId) {
		Assert.notNull(fundId, FUND_CANNOT_BE_NULL);
		ProductValueDTO productValue = restClientUtils.get(MANAGER_FINANCIAL_FEES + fundId, "", ProductValueDTO.class);

		return productValue;
	}

	@Override
	public ProductValueDTO getContractManagementFees(String policyId, String productLineId, Integer coverage) {
		Assert.notNull(policyId, POLICY_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = getPolicyCoverageParams(policyId, coverage);

		return restClientUtils.get(CONTRACT_MANAGEMENT_FEES, productLineId, params, ProductValueDTO.class);

	}

	@Override
	public ProductValueDTO getContractManagementFees(String policyId) {
		PolicyCoverageDTO firstPolicyCoverage = getFirstPolicyCoverage(policyId);

		return getContractManagementFees(policyId, firstPolicyCoverage.getProductLine(), firstPolicyCoverage.getCoverage());

	}

	@Override
	public ProductValueDTO getC13RatFees(String policyId) {
		PolicyCoverageDTO firstPolicyCoverage = getFirstPolicyCoverage(policyId);

		return getC13RatFees(policyId, firstPolicyCoverage.getProductLine(), firstPolicyCoverage.getCoverage());
	}

	private PolicyCoverageDTO getFirstPolicyCoverage(String policyId) {
		Assert.notNull(policyId, POLICY_CANNOT_BE_NULL);

		return policyCoverageService.getFirstPolicyCoverage(policyId);
	}

	private ProductValueDTO getC13RatFees(String policyId, String productLineId, Integer coverage) {
		Assert.notNull(policyId, POLICY_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = getPolicyCoverageParams(policyId, coverage);

		return restClientUtils.get(C13_RAT_FEES, productLineId, params, ProductValueDTO.class);

	}

	private MultivaluedMap<String, Object> getPolicyCoverageParams(String policyId, Integer coverage) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);
		params.add(COVERAGE, coverage);
		return params;
	}

	@Override
	public ProductValueDTO getPolicyFees(String policyId, String productLineId, Integer coverage) {

		MultivaluedMap<String, Object> params = getPolicyCoverageParams(policyId, coverage);

		return restClientUtils.get(POLICY_FEES, productLineId, params, ProductValueDTO.class);
	}

	@Override
	public ProductValueDTO getSurrenderFees(String policyId, String productLineId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(SURRENDER_FEES, productLineId, params, ProductValueDTO.class);
	}

	@Override
	public ProductValueDTO getFeeBasis(String productLineId) {
		return restClientUtils.get(FEE_BASIS, productLineId, ProductValueDTO.class);
	}

	@Override
	public boolean isPercentagePolicyFee(String productLineId) {
		ProductValueDTO feeBasis = getFeeBasis(productLineId);
		if (feeBasis != null) {
			BigDecimal numericValue = feeBasis.getNumericValue();
			return numericValue != null && TWO.compareTo(numericValue) == 0;
		}
		return false;
	}

	@Override
	public ProductValueDTO getSwitchFees(String policyId, String productLineId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(SWITCH_FEES, productLineId, params, ProductValueDTO.class);
	}

	@Override
	public ProductValueDTO getWithdrawalFees(String policyId, String productLineId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(WITHDRAWAL_FEES, productLineId, params, ProductValueDTO.class);
	}

	@Override
	public ProductValueDTO getDeathCoverageAmountOrPercentage(String policyId, String productLineId, String controlType) {
		Assert.notNull(policyId, POLICY_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(DEATH_COVERAGE_AMOUNT_OR_PERCENTAGE, productLineId + "/" + controlType, params, ProductValueDTO.class);
	}

	@Override
	public BigDecimal getMaxMultiplerAlphaValue(AppFormDTO appForm) {
		Assert.notNull(appForm, APPFORM_CANNOT_BE_NULL);

		ProductLineDTO findMatchingProductLine = productLineService.findMatchingProductLine(appForm);

		if (findMatchingProductLine == null) {
			throw new IllegalStateException(PRODUCT_LINE_CANNOT_BE_FOUND);
		}

		if (findMatchingProductLine.getProductValues() != null) {
			ProductValueDTO maxProductValue = findMatchingProductLine.getProductValues().stream().filter(x -> MXCLFA.equals(x.getControl())).findFirst().orElse(null);
			String max = productValueService.getAlphaValue(maxProductValue);

			if (max != null) {
				return new BigDecimal(max);
			}
		}

		log.warn("There is no product value '" + MXCLFA + "' defined on the product line " + findMatchingProductLine.getPrlId() + ".");

		return null;
	}

	@Override
	public String getAlphaValue(ProductLineDTO productLine, String name) {
		if (productLine == null) {
			return null;
		}
		ProductValueDTO productValue = productLine.getProductValues().stream().filter(x -> name.equals(x.getControl())).findFirst().orElse(null);
		if (productValue == null) {
			return null;
		}
		return getAlphaValue(productValue);
	}

	@Override
	public String getAlphaValue(ProductValueDTO productValue) {
		if (productValue == null) {
			return null;
		}
		String alphaValue = productValue.getAlphaValue();
		if (StringUtils.isNotBlank(alphaValue)) {
			return alphaValue;
		}
		BigDecimal numericValue = productValue.getNumericValue();
		if (numericValue != null) {
			return numericValue.setScale(2, BigDecimal.ROUND_HALF_UP) + "";
		}
		throw new IllegalStateException("No alpha value and no numeric value defined on the product value");
	}

	@Override
	public BigDecimal getNumericValue(ProductValueDTO productValue) {
		String alphaValue = getAlphaValue(productValue);

		if (StringUtils.isNotBlank(alphaValue)) {
			return new BigDecimal(alphaValue);
		}

		return null;
	}

	@Override
	public String getAlphaValue(String productLineId, String name) {
		Assert.notNull(productLineId, PRODUCT_LINE_ID_CANNOT_BE_NULL);
		ProductValueDTO productValue = restClientUtils.get(PRODUCT_LINE, productLineId + "/" + name, ProductValueDTO.class);
		return getAlphaValue(productValue);
	}

	@Override
	public PolicyEntryFeesDto getLastCoverageFees(String policyId) {
		Assert.notNull(policyId, POLICY_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(POLICY_ID, policyId);

		return restClientUtils.get(POLICY_FEES, "", params, PolicyEntryFeesDto.class);
	}
}
