package lu.wealins.liability.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.ProductValues;
import lu.wealins.liability.services.core.business.ProductValueService;
import lu.wealins.liability.services.ws.rest.ProductValueRESTService;

@Component
public class ProductValueRESTServiceImpl implements ProductValueRESTService {

	@Autowired
	private ProductValueService productValueService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductValueRESTService#getDeathCoverage(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public ProductValues getDeathCoverage(SecurityContext context, String prdId) {
		return new ProductValues(productValueService.getDeathCoverage(prdId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductValueRESTService#String prdId(javax.ws.rs.core.SecurityContext, java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getFundManagementFee(SecurityContext context, String fundId) {
		return productValueService.getFundManagementFee(fundId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.ProductValueRESTService#getProductValueByProductLineAndControl(javax.ws.rs.core.SecurityContext, java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getProductValueByProductLineAndControl(SecurityContext context, String productLineId, String control) {
		return productValueService.getProductValueByProductLineAndControl(productLineId, control);
	}

	@Override
	public ProductValueDTO getPolicyManagementFee(SecurityContext context, String policyId, String productLineId, Integer coverage) {
		return productValueService.getContractManagementFee(policyId, productLineId, coverage);
	}

	@Override
	public ProductValueDTO getPolicyC13RatFee(SecurityContext context, String policyId, String productLineId, Integer coverage) {
		return productValueService.getC13RatFee(policyId, productLineId, coverage);
	}

	@Override
	public ProductValueDTO getPolicyFee(SecurityContext context, String policyId, String productLineId, Integer coverage) {
		return productValueService.getPolicyFee(policyId, productLineId, coverage);
	}

	@Override
	public ProductValueDTO getFeeBasis(SecurityContext context, String productLineId) {
		return productValueService.getFeeBasis(productLineId);
	}

	@Override
	public ProductValueDTO getSurrenderFee(SecurityContext context, String policyId, String productLineId) {
		return productValueService.getSurrenderFee(policyId, productLineId);
	}

	@Override
	public ProductValueDTO getDeathCoverageAmountOrPercentage(SecurityContext context, String policyId, String productLineId, String controlType) {
		return productValueService.getDeathCoverageAmountOrPercentage(policyId, productLineId, controlType);
	}

	@Override
	public ProductValueDTO getSwitchFee(SecurityContext context, String policyId, String productLineId) {
		return productValueService.getSwitchFee(policyId, productLineId);
	}

	@Override
	public ProductValueDTO getWithdrawalFee(SecurityContext context, String policyId, String productLineId) {
		return productValueService.getWithdrawalFee(policyId, productLineId);
	}

	@Override
	public PolicyEntryFeesDto getEntryFees(SecurityContext ctx, String policyId) {
		return productValueService.getPolicyEntryFees(policyId);
	}

}
