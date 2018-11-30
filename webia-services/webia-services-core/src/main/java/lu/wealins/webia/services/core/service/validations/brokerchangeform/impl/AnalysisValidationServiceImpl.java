package lu.wealins.webia.services.core.service.validations.brokerchangeform.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.validations.BrokerValidationService;
import lu.wealins.webia.services.core.service.validations.CpsValidationService;
import lu.wealins.webia.services.core.service.validations.SendingRulesValidationService;
import lu.wealins.webia.services.core.service.validations.brokerchangeform.BrokerChangeFormValidationStepService;

@Service(value = "BrokerChangeAnalysisValidation")
public class AnalysisValidationServiceImpl extends BrokerChangeFormValidationStepService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	@Autowired
	private BrokerValidationService brokerValidationService;

	@Autowired
	private SendingRulesValidationService sendingRulesValidationService;

	@Autowired
	private CpsValidationService cpsValidationService;

	static {
		ENABLE_STEPS.add(StepTypeDTO.ANALYSIS);
	}

	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		List<String> errors = super.validateBeforeSave(step);
		BrokerChangeFormDTO brokerChangeForm = getFormData(step);

		PartnerFormDTO broker = brokerChangeForm.getBroker();

		brokerValidationService.validateMandatoryBroker(broker, errors);
		brokerValidationService.validateDifferentBroker(broker, brokerChangeForm.getOriginalPartnerId(), errors);
		brokerValidationService.validateMandateTransmission(broker, errors);
		
		List<PolicyValuationHoldingDTO> holdings = getHoldings(step);

		brokerValidationService.validateCommissionsRatesFeesRates(holdings, errors);
		brokerValidationService.validateMandatoryBrokerContractMngFees(holdings, errors);
		brokerValidationService.validateMandatoryCommissionRates(holdings, errors);
		sendingRulesValidationService.validateMailToAgentRules(brokerChangeForm.getSendingRules(), brokerChangeForm.getMailToAgent(), errors);

		return errors;
	}

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		List<String> errors = super.validateBeforeComplete(step);
		performCpsValidation(step, errors);
		return errors;
	}

	private void performCpsValidation(StepDTO step, List<String> errors) {
		BrokerChangeFormDTO brokerChangeForm = getFormData(step);
		String firstCpsUser = brokerChangeForm.getFirstCpsUser();
		String secondCpsUser = brokerChangeForm.getSecondCpsUser();
		cpsValidationService.validateFirstCps(firstCpsUser, errors);
		cpsValidationService.validateSecondCps(secondCpsUser, errors);
		cpsValidationService.validateFourEyes(firstCpsUser, secondCpsUser, errors);
	}

	private List<PolicyValuationHoldingDTO> getHoldings(StepDTO step) {
		BrokerChangeFormDTO brokerChangeForm = getFormData(step);
		PolicyValuationDTO policyValuation = brokerChangeForm.getPolicyValuation();

		if (policyValuation == null) {
			return new ArrayList<>();
		}

		return policyValuation.getHoldings();
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}


}
