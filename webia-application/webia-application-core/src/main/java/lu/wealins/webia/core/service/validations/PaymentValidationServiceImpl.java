package lu.wealins.webia.core.service.validations;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.enums.ControlDefinitionType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.webia.core.service.LiabilityProductLineService;
import lu.wealins.webia.core.service.LiabilityProductValueService;

@Service
public class PaymentValidationServiceImpl implements PaymentValidationService {

	@Autowired
	private LiabilityProductLineService productLineService;

	@Autowired
	private LiabilityProductValueService productValueService;

	public void validatePremiumRange(AppFormDTO appForm, List<String> errors) {

		ProductLineDTO productLine = productLineService.findMatchingProductLine(appForm);
		if (productLine != null) {
			CollectionUtils.addIgnoreNull(errors, validateMaximumPremium(appForm, productLine));
			CollectionUtils.addIgnoreNull(errors, validateMinimumPremium(appForm, productLine));
		}

	}

	private String validateMaximumPremium(AppFormDTO appForm, ProductLineDTO productLine) {
		BigDecimal maximumPremiumProductValue = getBigDecimalAlphaValue(productLine, ControlDefinitionType.MAXIMUM_PREMIUM);
		if (maximumPremiumProductValue != null && appForm.getPaymentAmt() != null && maximumPremiumProductValue.compareTo(appForm.getPaymentAmt()) < 0) {
			return "The expected premium is higher than the 'Maximum Premium' product value (" + maximumPremiumProductValue + ").";
		}
		return null;
	}

	private String validateMinimumPremium(AppFormDTO appForm, ProductLineDTO productLine) {
		BigDecimal minimumPremiumProductValue = getBigDecimalAlphaValue(productLine, ControlDefinitionType.MINIMUM_PREMIUM);
		if (minimumPremiumProductValue != null && appForm.getPaymentAmt() != null && minimumPremiumProductValue.compareTo(appForm.getPaymentAmt()) > 0) {
			return "The expected premium is lower than the 'Minimum Premium' product value (" + minimumPremiumProductValue + ").";
		}
		return null;
	}

	private BigDecimal getBigDecimalAlphaValue(ProductLineDTO productLine, ControlDefinitionType controlDefinitionType) {
		ProductValueDTO productValue = productLine.getProductValues().stream().filter(x -> controlDefinitionType.getValue().equals(x.getControl())).findFirst().orElse(null);
		if (productValue != null) {
			String alphaValue = productValueService.getAlphaValue(productValue);
			if (StringUtils.isNotBlank(alphaValue)) {
				return new BigDecimal(alphaValue);
			}
		}

		return null;
	}

}
