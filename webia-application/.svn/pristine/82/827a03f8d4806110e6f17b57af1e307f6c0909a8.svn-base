package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductLineService;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityProductLineServiceImpl implements LiabilityProductLineService {

	private static final Logger log = LoggerFactory.getLogger(LiabilityProductLineServiceImpl.class);

	private static final String CONTROLS = "controls";
	private static final String PRD_ID = "prdId";
	private static final String LIABILITY_LOAD_PRODUCT_LINE = "liability/productLine/";
	private static final String LIABILITY_GET_PRODUCT_LINE = LIABILITY_LOAD_PRODUCT_LINE + "get/";

	private static final String SUBSCRIPTION = "ANBMUL";
	private static final String POLICY_FEE_BASE = "PFEBAS";
	private static final String TAX = "TX1RAT";
	private static final String DEATH_COVERAGE = "DTHCAL";
	private static final BigDecimal HUNDRED = new BigDecimal("100");
	private static final String STANDARD = "STANDARD";
	private static final String MXCLFA = "MXCLFA";

	private static final String PRODUCT_LINE_CANNOT_BE_NULL = "The product line id can not be null";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityProductValueService productValueService;

	@Autowired
	private LiabilityPolicyService policyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityProductLineService#getProductLines(java.lang.String, java.util.List)
	 */
	@Override
	public Collection<ProductLineDTO> getProductLines(String prdId, List<String> controls) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(PRD_ID, prdId);

		controls.forEach(x -> params.add(CONTROLS, x));

		return restClientUtils.get(LIABILITY_LOAD_PRODUCT_LINE, "", params, new GenericType<Collection<ProductLineDTO>>() {
		});
	}

	@Override
	public Collection<ProductLineDTO> getFilterProductLines(String prdId, List<String> controls) {

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add(PRD_ID, prdId);

		controls.forEach(x -> params.add(CONTROLS, x));

		return restClientUtils.get(LIABILITY_LOAD_PRODUCT_LINE, "filter", params, new GenericType<Collection<ProductLineDTO>>() {
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityProductLineService#getProductLine(java.lang.String)
	 */
	@Override
	public ProductLineDTO getProductLine(String prlId) {
		Assert.notNull(prlId, PRODUCT_LINE_CANNOT_BE_NULL);
		ProductLineDTO productLine = restClientUtils.get(LIABILITY_GET_PRODUCT_LINE + prlId, "", ProductLineDTO.class);

		return productLine;
	}

	@Override
	public ProductLineDTO findMatchingProductLine(AppFormDTO appForm) {
		ProductLineDTO matchingProductLine = null;
		BigDecimal fee_base = (appForm.getEntryFeesAmt() != null) ? new BigDecimal("1") : new BigDecimal("2");
		BigDecimal deathCoverageType = (appForm.getDeathCoverageTp() != null) ? new BigDecimal(appForm.getDeathCoverageTp()) : null;
		BigDecimal taxValue = appForm.getTax() ? appForm.getTaxRate().multiply(HUNDRED).divide(HUNDRED.add(appForm.getTaxRate()), new MathContext(7, RoundingMode.HALF_DOWN)) : BigDecimal.ZERO;
		// search the product lines available for a product for a subscription
		// with entry fees in percentage (2) or Amount(1)
		// with or without tax ( belgium 2% = 1,960784%) ... !! taxeValue=0 means tax is false !!
		// with the corresponding death coverage type (number) and format(Standard or alternative)

		Collection<ProductLineDTO> productLines = getFilterProductLines(appForm.getProductCd(), Arrays.asList(POLICY_FEE_BASE, SUBSCRIPTION, TAX, DEATH_COVERAGE, MXCLFA));

		for (ProductLineDTO pl : productLines) {
			if (pl.getProductValues().stream().anyMatch(pv -> POLICY_FEE_BASE.equals(pv.getControl()) && pv.getNumericValue().compareTo(fee_base) == 0)) {
				ProductValueDTO taxProductValue = getTaxProductValue(pl);
				BigDecimal taxProductValueNumericValue = productValueService.getNumericValue(taxProductValue);

				// If the value is defined is not defined in Lissia then we consider that the tax value is 0.
				if (taxProductValueNumericValue == null) {
					taxProductValueNumericValue = BigDecimal.ZERO;
				}

				if ((isTaxNotApplicable(appForm) && taxProductValueNumericValue.compareTo(BigDecimal.ZERO) == 0) || taxProductValueNumericValue.compareTo(taxValue) == 0) {
					ProductValueDTO deathCoverageProductValue = getDeathCoverageProductValue(pl);
					BigDecimal deathCoverageProductValueNumericValue = productValueService.getNumericValue(deathCoverageProductValue);

					if ((deathCoverageType == null && (deathCoverageProductValueNumericValue == null || deathCoverageProductValueNumericValue.compareTo(BigDecimal.ZERO) == 0)
							|| (deathCoverageProductValueNumericValue != null && deathCoverageProductValueNumericValue.compareTo(deathCoverageType) == 0
									&& pl.getName().toUpperCase().contains(STANDARD) == appForm.getDeathCoverageStd()))) {
						matchingProductLine = pl;
						break;
					}
				}
			}
		}
		log.info("find Matching ProductLine for policy '" + appForm.getPolicyId() + "' for product '" + appForm.getProductCd() + "' with fees in " +
				(BigDecimal.ONE.compareTo(fee_base) == 0 ? "Amount" : "Percent") + " with tax value " + taxValue.toPlainString() + "% and " +
				(appForm.getDeathCoverageStd() ? "Standard" : "Alternative") + " death coverage of type :'" + deathCoverageType + "'");
		return matchingProductLine;
	}

	@Override
	public ProductLineDTO findMatchingAdditionalProductLine(AppFormDTO appForm) {
		BigDecimal fee_base = (appForm.getEntryFeesAmt() != null) ? new BigDecimal("1") : new BigDecimal("2");
		if (appForm.getTax() == null) {
			appForm.setTax(false);
		}
		BigDecimal taxValue = appForm.getTax() ? appForm.getTaxRate().multiply(HUNDRED).divide(HUNDRED.add(appForm.getTaxRate()), new MathContext(7, RoundingMode.HALF_DOWN)) : BigDecimal.ZERO;
		// search the product lines available for a product for a subscription
		// with entry fees in percentage (2) or Amount(1)
		// with or without tax ( belgium 2% = 1,960784%) ... !! taxeValue=0 means tax is false !!

		
		PolicyDTO policy = policyService.getPolicy(appForm.getPolicyId());
		String productLine = policy.getFirstPolicyCoverages().getProductLine();
		ProductLineDTO productline = getProductLine(productLine);
		// get sub product lines with controls ANMBUL

		String subProductlines = productline.getProductValues().stream().filter(x -> x.getControl().equalsIgnoreCase(SUBSCRIPTION)).findFirst().get().getAlphaValue();
		String[] parts = subProductlines.split(";");

		if (parts.length < 1) {
			log.error("No matching productlines for the additional premium on the policy: " + appForm.getPolicyId());
			return null;
		}

		if (parts.length == 1) {
			return getProductLine(appForm, fee_base, taxValue, parts[0]);
		} else {	
			return Arrays.stream(parts)
					.map(part -> getProductLine(appForm, fee_base, taxValue, part))
					.filter(pl -> pl != null)
					.findFirst()
					.orElse(null);
		}
	}

	private ProductLineDTO getProductLine(AppFormDTO appForm, BigDecimal fee_base, BigDecimal taxValue, String part) {
		ProductLineDTO pl = getProductLine(part);
		boolean productValueMatch = pl.getProductValues().stream().anyMatch(pv -> POLICY_FEE_BASE.equals(pv.getControl()) && pv.getNumericValue().compareTo(fee_base) == 0);
		boolean taxMatch = false;
		if (productValueMatch) {
			ProductValueDTO taxProductValue = getTaxProductValue(pl);
			BigDecimal taxProductValueNumericValue = productValueService.getNumericValue(taxProductValue);

			// If the value is defined is not defined in Lissia then we consider that the tax value is 0.
			if (taxProductValueNumericValue == null) {
				taxProductValueNumericValue = BigDecimal.ZERO;
			}

			taxMatch = (isTaxNotApplicable(appForm) && taxProductValueNumericValue.compareTo(BigDecimal.ZERO) == 0) || taxProductValueNumericValue.compareTo(taxValue) == 0;
			if (taxMatch) {
				return pl;
			}
		}
		return null;
	}

	private boolean isTaxNotApplicable(AppFormDTO appForm) {

		BigDecimal taxValue = appForm.getTax() ? appForm.getTaxRate().multiply(HUNDRED).divide(HUNDRED.add(appForm.getTaxRate()), new MathContext(7, RoundingMode.HALF_DOWN)) : new BigDecimal("-1");

		return BooleanUtils.isFalse(appForm.getTax()) || BigDecimal.ZERO.compareTo(taxValue) >= 0;
	}

	private ProductValueDTO getDeathCoverageProductValue(ProductLineDTO pl) {
		return getProductValue(pl, DEATH_COVERAGE);
	}

	private ProductValueDTO getTaxProductValue(ProductLineDTO pl) {
		return getProductValue(pl, TAX);
	}

	private ProductValueDTO getProductValue(ProductLineDTO pl, String productValue) {
		return pl.getProductValues().stream().filter(pv -> productValue.equals(pv.getControl())).findFirst().orElse(null);
	}

}
