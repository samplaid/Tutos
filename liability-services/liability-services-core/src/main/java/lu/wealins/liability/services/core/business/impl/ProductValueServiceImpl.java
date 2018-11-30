package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyEntryFeesDto;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.enums.ControlDefinitionType;
import lu.wealins.liability.services.core.business.PolicyAgentShareService;
import lu.wealins.liability.services.core.business.PolicyCoverageService;
import lu.wealins.liability.services.core.business.ProductLineService;
import lu.wealins.liability.services.core.business.ProductValueService;
import lu.wealins.liability.services.core.mapper.ProductValueMapper;
import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageEntity;
import lu.wealins.liability.services.core.persistence.entity.ProductValueEntity;
import lu.wealins.liability.services.core.persistence.repository.ProductValueRepository;
import lu.wealins.liability.services.core.utils.HistoricManager;

@Service
public class ProductValueServiceImpl implements ProductValueService {

	private static final BigDecimal TWO = new BigDecimal(2);
	@Autowired
	private ProductValueRepository productValueRepository;

	@Autowired
	private ProductValueMapper productValueMapper;

	@Autowired
	private ProductLineService productLineService;

	@Autowired
	private PolicyCoverageService coverageService;

	@Autowired
	private PolicyAgentShareService policyAgentShareService;

	@Autowired
	private HistoricManager historicManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getContractManagementFee(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getContractManagementFee(String policyId, String productLineId, Integer coverage) {
		return extractFirstElement(getByPolicyAndProductLineAndControlsAndCoverage(policyId, productLineId, Arrays.asList(ControlDefinitionType.CONTRACT_MANAGEMENT_FEE), coverage));
	}

	@Override
	public ProductValueDTO getC13RatFee(String policyId, String productLineId, Integer coverage) {
		return extractFirstElement(getByPolicyAndProductLineAndControlsAndCoverage(policyId, productLineId, Arrays.asList(ControlDefinitionType.C13RAT), coverage));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getSurrenderFee(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getSurrenderFee(String policyId, String productLineId) {
		return extractFirstElement(getPolicyAndProductLineProductValue(policyId, productLineId, Arrays.asList(ControlDefinitionType.SURRENDER_FEE)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getPolicyFee(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getPolicyFee(String policyId, String productLineId, Integer coverage) {
		return extractFirstElement(getByPolicyAndProductLineAndControlsAndCoverage(policyId, productLineId, Arrays.asList(ControlDefinitionType.POLICY_FEE), coverage));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getSwitchFee(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getSwitchFee(String policyId, String productLineId) {
		return extractFirstElement(getPolicyAndProductLineProductValue(policyId, productLineId, Arrays.asList(ControlDefinitionType.SWITCH_FEE)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getWithdrawalFee(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getWithdrawalFee(String policyId, String productLineId) {
		return extractFirstElement(getPolicyAndProductLineProductValue(policyId, productLineId, Arrays.asList(ControlDefinitionType.WITHDRAWAL_FEE)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getFeeBasis(java.lang.String)
	 */
	@Override
	public ProductValueDTO getFeeBasis(String productLineId) {
		return getProductValueByProductLineAndControl(productLineId, ControlDefinitionType.PFEBAS.name());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getFundManagementFee(java.lang.String)
	 */
	@Override
	public ProductValueDTO getFundManagementFee(String fundId) {
		return extractFirstElement(getFundFees(fundId, Arrays.asList(ControlDefinitionType.FUND_MANAGEMENT_FEE)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getFundManagementFee(java.lang.String)
	 */
	@Override
	public ProductValueDTO getDeathCoverageAmountOrPercentage(String policyId, String prlId, String controlType) {
		return extractFirstElementWithoutNullAlphaValue(
				getPolicyAndProductLineProductValue(policyId, prlId, Arrays.asList(ControlDefinitionType.valueOf(controlType))));
	}

	private Collection<ProductValueDTO> getFundFees(String fundId, Collection<ControlDefinitionType> controlDefinitionTypes) {
		List<String> controlDefinitionTypeValues = mapControlsToString(controlDefinitionTypes);
		// Fee defined at policy level
		return productValueMapper.asProductValueDTOs(productValueRepository.findByFundAndControls(fundId, controlDefinitionTypeValues));
	}

	private Collection<ProductValueDTO> getPolicyAndProductLineProductValue(String policyId, String prlId, Collection<ControlDefinitionType> controlDefinitionTypes) {
		List<String> controlDefinitionTypeValues = mapControlsToString(controlDefinitionTypes);

		// productValue defined at policy level
		List<ProductValueEntity> productValues = productValueRepository.findByPolicyAndControls(policyId, controlDefinitionTypeValues);

		return getOptionallyByProductLine(prlId, controlDefinitionTypeValues, productValues);

	}

	private Collection<ProductValueDTO> getByPolicyAndProductLineAndControlsAndCoverage(String policyId, String prlId, Collection<ControlDefinitionType> controlDefinitionTypes, Integer coverage) {
		List<String> controlDefinitionTypeValues = mapControlsToString(controlDefinitionTypes);

		// productValue defined at policy level
		List<ProductValueEntity> productValues = productValueRepository.findByPolicyAndControlsAndCoverage(policyId, controlDefinitionTypeValues, coverage);

		return getOptionallyByProductLine(prlId, controlDefinitionTypeValues, productValues);

	}

	private Collection<ProductValueDTO> getOptionallyByProductLine(String prlId, List<String> controlDefinitionTypeValues, List<ProductValueEntity> productValues) {
		// productValue defined at product level
		if (CollectionUtils.isEmpty(productValues)) {
			productValues = productValueRepository.findByProductLineAndControls(prlId, controlDefinitionTypeValues);
		}

		return productValueMapper.asProductValueDTOs(productValues);
	}

	private List<String> mapControlsToString(Collection<ControlDefinitionType> controlDefinitionTypes) {
		List<String> controlDefinitionTypeValues = controlDefinitionTypes.stream().map(x -> x.getValue()).collect(Collectors.toList());
		return controlDefinitionTypeValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getDeathCoverage(java.lang.String)
	 */
	@Override
	public Collection<ProductValueDTO> getDeathCoverage(String prdId) {
		List<String> controls = new ArrayList<String>();
		controls.add(ControlDefinitionType.DEATH_COVERAGE.getValue());
		return productValueMapper.asProductValueDTOs(productValueRepository.findByProductAndControls(prdId, controls));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.ProductValueService#getProductValueByProductLineAndControl(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductValueDTO getProductValueByProductLineAndControl(String productLineId, String control) {
		List<ProductValueEntity> productValues = productValueRepository.findByProductLineAndControls(productLineId, Arrays.asList(control));
		if (CollectionUtils.isNotEmpty(productValues)) {
			return productValueMapper.asProductValueDTO(productValues.iterator().next());
		}
		return null;
	}

	@Override
	public ProductValueDTO createOrUpdateProductValue(String control, String polId, Integer coverage, BigDecimal numericValue) {
		Assert.notNull(control);
		Assert.notNull(polId);
		Assert.notNull(coverage);

		String prcId = control.toUpperCase() + "_" + polId.toUpperCase() + "_" + coverage;

		ProductValueEntity productValue = getProductValueEntity(control, polId, coverage);

		if (productValue == null) {
			return null;
		}

		// Reset values from product
		if (!prcId.equals(productValue.getPrcId())) {
			productValue = productValueMapper.asProductValueEntity(productValue);

			String productLine = productLineService.getProductLine(polId, coverage);

			productValue.setPrcId(prcId);
			productValue.setProductLine(null);
			productValue.setProductLineId(productLine);
			productValue.setDataType(18);
			PolicyCoverageEntity policyCoverageTemp = new PolicyCoverageEntity();
			policyCoverageTemp.setPocId(polId + "_" + coverage);
			productValue.setPolicyCoverage(policyCoverageTemp);
			productValue.setUseage(Integer.valueOf(0));
		}

		boolean isEquals = (productValue.getNumericValue() == null && numericValue == null) || (productValue.getNumericValue() != null && productValue.getNumericValue().compareTo(numericValue) == 0);
		if (!isEquals) {
			productValue.setNumericValue(numericValue);
			productValue.setAlphaValue(numericValue.toString());
		}

		if (historicManager.historize(productValue)) {
			productValue = productValueRepository.save(productValue);
		}

		return productValueMapper.asProductValueDTO(productValue);
	}

	@Override
	public ProductValueDTO getProductValue(String control, String polId, Integer coverage) {
		return productValueMapper.asProductValueDTO(getProductValueEntity(control, polId, coverage));
	}

	private ProductValueEntity getProductValueEntity(String control, String polId, Integer coverage) {
		Assert.notNull(control);
		Assert.notNull(polId);
		Assert.notNull(coverage);

		String prcId = control.toUpperCase() + "_" + polId.toUpperCase() + "_" + coverage;

		ProductValueEntity productValue = productValueRepository.findOne(prcId);

		if (productValue == null) {
			String productLine = productLineService.getProductLine(polId, coverage);

			if (StringUtils.isBlank(productLine)) {
				throw new IllegalStateException("Cannot find the product line linked to the policy id " + polId + " and the coverage " + coverage + ".");
			}

			String prcIdFromProduct = control.toUpperCase() + "_" + productLine;

			productValue = productValueRepository.findOne(prcIdFromProduct);
			Assert.notNull(productValue, "There is no product value " + prcIdFromProduct + ".");
		}

		return productValue;
	}

	private ProductValueDTO extractFirstElement(Collection<ProductValueDTO> productValueDTOs) {
		if (CollectionUtils.isNotEmpty(productValueDTOs)) {
			return productValueDTOs.iterator().next();
		}

		return null;
	}

	private ProductValueDTO extractFirstElementWithoutNullAlphaValue(Collection<ProductValueDTO> productValueDTOs) {
		if (CollectionUtils.isNotEmpty(productValueDTOs)) {
			for (ProductValueDTO productValueDTO : productValueDTOs) {
				if (productValueDTO.getAlphaValue() != null) {
					return productValueDTO;
				}
			}
		}

		return null;
	}

	@Override
	public PolicyEntryFeesDto getPolicyEntryFees(String policyId) {

		PolicyEntryFeesDto policyFees = new PolicyEntryFeesDto();
		PolicyCoverageDTO maxCoverage = coverageService.getLastPolicyCoverage(policyId);
		String productLineId = maxCoverage.getProductLine();
		Integer coverage = maxCoverage.getCoverage();
		ProductValueDTO entryFees = getPolicyFee(policyId, productLineId, coverage);
		boolean isPercentage = isPercentagePolicyFee(maxCoverage.getProductLine());
		PolicyAgentShareDTO policyAgentShareDTO = policyAgentShareService.getLastOperationEntryFees(policyId, coverage);
		BigDecimal brokerEntryFees = policyAgentShareDTO == null ? BigDecimal.ZERO : (isPercentage ? policyAgentShareDTO.getPercentage() : policyAgentShareDTO.getSpecificIce());
		policyFees.setBrokerEntryFees(brokerEntryFees);
		policyFees.setEntryFees(entryFees.getNumericValue());
		policyFees.setIsPercentage(isPercentage);

		return policyFees;
	}

	private boolean isPercentagePolicyFee(String productLineId) {
		ProductValueDTO feeBasis = getFeeBasis(productLineId);
		if (feeBasis != null) {
			BigDecimal numericValue = feeBasis.getNumericValue();
			return numericValue != null && TWO.compareTo(numericValue) == 0;
		}
		return false;
	}
}
