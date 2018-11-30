package lu.wealins.webia.services.core.service.validations.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.services.core.service.PartnerFormService;
import lu.wealins.webia.services.core.service.validations.BrokerValidationService;

@Service
public class BrokerValidationServiceImpl implements BrokerValidationService {

	private static final String MANDATE_BROKER_REQUIRED = "The mandate transmission is mandatory.";

	@Autowired
	private PartnerFormService partnerFormService;

	@Override
	public void validateMandatoryBroker(PartnerFormDTO broker, List<String> errors) {
		if (broker == null || StringUtils.isEmpty(broker.getPartnerId())) {
			errors.add("Broker is mandatory.");
		}
	}

	@Override
	public void validateDifferentBroker(PartnerFormDTO brokerA, String partnerId, List<String> errors) {
		if (brokerA != null) {
			if (brokerA.getPartnerId() != null && brokerA.getPartnerId().equals(partnerId)) {
				errors.add("The broker must be different.");
			}
		}
	}

	@Override
	public void validateMandatoryFieldsForBroker(PartnerFormDTO broker, List<String> errors) {
		if (broker == null || StringUtils.isEmpty(broker.getPartnerId())) {
			errors.add("Broker is mandatory.");
		} else {
			if (broker.getEntryFeesPct() == null && broker.getEntryFeesAmt() == null) {
				errors.add("Broker entry fees is mandatory.");
			}
			if (broker.getMngtFeesPct() == null) {
				errors.add("Broker admin fees is mandatory.");
			}
			if (BooleanUtils.isTrue(broker.getIsOverridedFees()) && StringUtils.isEmpty(broker.getExplainOverFees())) {
				errors.add("The derogation reason is mandatory.");
			}
		}
	}

	@Override
	public void validateBrokerContractMngFees(PartnerFormDTO broker, BigDecimal contractMngtFeesPct, List<String> errors) {
		if (broker != null) {
			validateBrokerContractMngFees(broker.getMngtFeesPct(), contractMngtFeesPct, errors);
		}
	}

	@Override
	public void validateMandatoryCommissionRates(List<PolicyValuationHoldingDTO> holdings, List<String> errors) {
		holdings.forEach(x -> validateMandatoryCommissionRates(x, errors));
	}

	@Override
	public void validateCommissionsRatesFeesRates(List<PolicyValuationHoldingDTO> holdings, List<String> errors) {
		holdings.forEach(x -> validateCommissionsAndFeesRates(x.getCommissionRate(), x.getFeeRate(), errors));

	}

	@Override
	public void validateMandatoryBrokerContractMngFees(List<PolicyValuationHoldingDTO> holdings, List<String> errors) {
		holdings.forEach(x -> validateMandatoryBrokerContractMngFees(x, errors));
	}

	public void validateMandatoryBrokerContractMngFees(PolicyValuationHoldingDTO holding, List<String> errors) {
		if (holding != null && holding.getFeeRate() == null) {
			errors.add("Broker admin fees is mandatory for the fund " + holding.getFundId());
		}
	}

	public void validateMandatoryCommissionRates(PolicyValuationHoldingDTO holding, List<String> errors) {
		if (holding != null && holding.getCommissionRate() == null) {
			errors.add("Commission rate is mandatory for the fund " + holding.getFundId());
		}
	}

	private void validateBrokerContractMngFees(BigDecimal brokerMngtFeesPct, BigDecimal contractMngtFeesPct, List<String> errors) {
		if (contractMngtFeesPct != null
				&& brokerMngtFeesPct != null
				&& brokerMngtFeesPct.compareTo(contractMngtFeesPct) > 0) {
			errors.add("The broker contract management fees (" + brokerMngtFeesPct + "%) should not be greater than the contract management fees (" + contractMngtFeesPct + "%).");
		}
	}

	private void validateCommissionsAndFeesRates(BigDecimal commissionRate, BigDecimal feeRate, List<String> errors) {
		if (commissionRate != null && feeRate != null
				&& commissionRate.compareTo(feeRate) > 0) {
			errors.add("The commission rate (" + commissionRate + "%) should not be greater than the admin fees rates (" + feeRate + "%).");
		}
	}

	public void validateMandatoryBrokerAdminFees(BigDecimal brokerMngtFeesPct, BigDecimal contractMngtFeesPct, List<String> errors) {
		if (contractMngtFeesPct != null
				&& brokerMngtFeesPct != null
				&& brokerMngtFeesPct.compareTo(contractMngtFeesPct) > 0) {
			errors.add("The broker contract management fees should not be greater than the contract management fees.");
		}
	}

	@Override
	public void validateWealinsBroker(PartnerFormDTO broker, PartnerFormDTO businessIntroducer, PartnerFormDTO brokerContact, List<String> errors) {
		if (partnerFormService.isWealinsBroker(broker)) {
			if (businessIntroducer == null || StringUtils.isEmpty(businessIntroducer.getPartnerId())) {
				errors.add("Business Introducer is mandatory.");
			}
			if (brokerContact != null) {
				errors.add("The broker contact should not be set if the broker is a wealins.");
			}
		} else {
			if (businessIntroducer != null) {
				errors.add("The business introducer must be set if the broker is not wealins.");
			}
		}
	}

	@Override
	public void validateMandateTransmission(PartnerFormDTO broker, List<String> errors) {

		if (broker != null && !partnerFormService.isWealinsBroker(broker) && broker.getPartnerAuthorized() == null) {
			errors.add(MANDATE_BROKER_REQUIRED);
		}
	}

}
