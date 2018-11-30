package lu.wealins.webia.core.service;

import java.math.BigDecimal;

import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface LiabilityProductValueService {

	ProductValueDTO getContractManagementFees(String policyId, String productLineId, Integer coverage);

	ProductValueDTO getContractManagementFees(String policyId);

	ProductValueDTO getC13RatFees(String policyId);

	ProductValueDTO getManagerFinancialFees(String fundId);

	String getAlphaValue(ProductLineDTO productLine, String name);

	String getAlphaValue(ProductValueDTO productValue);

	BigDecimal getNumericValue(ProductValueDTO productValue);

	String getAlphaValue(String productLineId, String name);

	BigDecimal getMaxMultiplerAlphaValue(AppFormDTO appForm);

	ProductValueDTO getPolicyFees(String policyId, String productLineId, Integer coverage);

	ProductValueDTO getFeeBasis(String productLineId);

	ProductValueDTO getSurrenderFees(String policyId, String productLineId);

	boolean isPercentagePolicyFee(String productLineId);

	ProductValueDTO getSwitchFees(String policyId, String productLineId);

	ProductValueDTO getWithdrawalFees(String policyId, String productLineId);

	ProductValueDTO getDeathCoverageAmountOrPercentage(String policyId, String productLineId, String controlType);

	PolicyEntryFeesDto getLastCoverageFees(String policyId);

}
