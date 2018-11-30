package lu.wealins.liability.services.core.business;

import java.math.BigDecimal;
import java.util.Collection;

import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.ProductValueDTO;

public interface ProductValueService {

	/**
	 * Get the contract management fee defined at policy level if these ones are overridden otherwise get the contract management fees defined at product level.
	 * 
	 * @param policyId The policy id.
	 * @param prdId The product id.
	 * @return The contract management fee.
	 */
	ProductValueDTO getContractManagementFee(String policyId, String prdId, Integer coverage);

	ProductValueDTO getC13RatFee(String policyId, String productLineId, Integer coverage);

	/**
	 * Get the surrender fee defined at policy level if these ones are overridden otherwise get the contract management fees defined at product level.
	 * 
	 * @param policyId The policy id.
	 * @param prdId The product id.
	 * @return The surrender fee.
	 */
	ProductValueDTO getSurrenderFee(String policyId, String prdId);

	/**
	 * Get the entry fee defined at policy level if these ones are overridden otherwise get the contract management fees defined at product level.
	 * 
	 * @param policyId The policy id.
	 * @param prdId The product id.
	 * @return The entry fee.
	 */
	ProductValueDTO getPolicyFee(String policyId, String prdId, Integer coverage);

	/**
	 * Get the fee basis defined at the product level.
	 * 
	 * @param prdId The product id.
	 * @return The entry fee.
	 */
	ProductValueDTO getFeeBasis(String productLineId);

	/**
	 * Get the switch fee defined at policy level if these ones are overridden otherwise get the contract management fees defined at product level.
	 * 
	 * @param policyId The policy id.
	 * @param prdId The product id.
	 * @return The switch fee.
	 */
	ProductValueDTO getSwitchFee(String policyId, String prdId);

	/**
	 * Get the withdrawal fee defined at policy level if these ones are overridden otherwise get the contract management fees defined at product level.
	 * 
	 * @param policyId The policy id.
	 * @param prdId The product id.
	 * @return The withdrawal fee.
	 */
	ProductValueDTO getWithdrawalFee(String policyId, String prdId);

	/**
	 * Get the fund management fee.
	 * 
	 * @param fundId The fund id.
	 * @return The fund management fee.
	 */
	ProductValueDTO getFundManagementFee(String fundId);

	/**
	 * Get the death coverage available for a given product.
	 * 
	 * @param prdId The product id.
	 * @return a collection of the death coverage available.
	 */
	Collection<ProductValueDTO> getDeathCoverage(String prdId);

	/**
	 * Get the value for a given product Line and a given control name.
	 * 
	 * @param productLineId The product Line Id.
	 * @param control The control code.
	 * @return the corresponding value.
	 */
	ProductValueDTO getProductValueByProductLineAndControl(String productLineId, String control);

	/**
	 * Get the policy death coverage amount
	 * 
	 * @param policyId
	 * @param prlId product line id
	 * @return
	 */
	ProductValueDTO getDeathCoverageAmountOrPercentage(String policyId, String prlId, String controlType);

	/**
	 * Retrieve the policy entry fees ( entry fees and broker entry fees )
	 * 
	 * @param policyId the policy id
	 * @return an object containing the entry fees
	 */
	PolicyEntryFeesDto getPolicyEntryFees(String policyId);

	ProductValueDTO createOrUpdateProductValue(String control, String polId, Integer coverage, BigDecimal numericValue);

	ProductValueDTO getProductValue(String control, String polId, Integer coverage);
}
