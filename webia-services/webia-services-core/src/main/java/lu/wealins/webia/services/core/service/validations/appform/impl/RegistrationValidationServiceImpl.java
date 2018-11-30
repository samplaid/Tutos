package lu.wealins.webia.services.core.service.validations.appform.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.core.service.validations.BrokerValidationService;
import lu.wealins.webia.services.core.service.validations.appform.AppFormValidationStepService;
import lu.wealins.webia.services.core.service.validations.appform.PaymentValidationService;

@Service(value = "RegistrationValidationService")
public class RegistrationValidationServiceImpl extends AppFormValidationStepService {
	private static final String MAX_INIT_CHARGE = "MAX_INIT_CHARGE";
	private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
	private static final BigDecimal ZERO = BigDecimal.valueOf(0);
	private static final String PRODUCT_IS_MANDATORY = "Product is mandatory.";

	private static final Set<StepTypeDTO> ENABLE_STEPS = new HashSet<>();

	static {
		ENABLE_STEPS.add(StepTypeDTO.REGISTRATION);
		ENABLE_STEPS.add(StepTypeDTO.ANALYSIS);
		ENABLE_STEPS.add(StepTypeDTO.UPDATE_INPUT);
	}

	@Autowired
	private ApplicationParameterService applicationParameterService;
	@Autowired
	private PaymentValidationService paymentValidationService;
	@Autowired
	private BrokerValidationService brokerValidationService;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		AppFormDTO registration = getFormData(step);
		List<String> errors = new ArrayList<>();

		checkMandatoryFields(registration, errors);
		checkCompanyFees(registration, errors);
		checkCountryManagers(registration, errors);
		paymentValidationService.validateEntryFees(registration, errors);
		checkContractMngFees(registration, errors);
		brokerValidationService.validateBrokerContractMngFees(registration.getBroker(), registration.getMngtFeesPct(), errors);
		checkFeesRange(registration, errors);
		errors.removeAll(Collections.singletonList(null));
		return errors;
	}

	private void checkContractMngFees(AppFormDTO appForm, List<String> errors) {
		String percentMaxInitCharge = applicationParameterService.getStringValue(MAX_INIT_CHARGE);
		if (appForm.getMngtFeesPct() != null && appForm.getMngtFeesPct().compareTo(new BigDecimal(percentMaxInitCharge)) > 0) {
			errors.add("The contract management fees should not be greater than " + percentMaxInitCharge + " %");
		}
	}

	private void checkMandatoryFields(AppFormDTO r, List<String> errors) {
		if (StringUtils.isEmpty(r.getProductCd())) {
			errors.add(PRODUCT_IS_MANDATORY);
		}
		if (r.getApplicationForm() == null) {
			errors.add("Application Nr is mandatory.");
		}
		if (r.getClientName() == null) {
			errors.add("Client is mandatory.");
		}
		if (r.getCountryCd() == null) {
			errors.add("Country of residence is mandatory.");
		}
		if (r.getExpectedPremium() == null) {
			errors.add("Expected Premium is mandatory.");
		}
		if (r.getContractCurrency() == null) {
			errors.add("Currency of contract is mandatory.");
		}
		if (r.getEntryFeesPct() == null && r.getEntryFeesAmt() == null) {
			errors.add("Contract entry fees is mandatory.");
		}
		if (r.getMngtFeesPct() == null) {
			errors.add("Admin fees is mandatory.");
		}
		if (r.getCompanyMngtFeesPct() == null) {
			errors.add("Company contract management fees cannot be null");
		}
		if (r.getCompanyEntryFeesPct() == null && r.getCompanyEntryFeesAmt() == null) {
			errors.add("Company entry fees cannot be null");
		}

		PartnerFormDTO broker = r.getBroker();
		brokerValidationService.validateMandatoryFieldsForBroker(broker, errors);
		brokerValidationService.validateWealinsBroker(broker, r.getBusinessIntroducer(), r.getBrokerContact(), errors);

		if (r.getCountryManagers() == null || r.getCountryManagers().size() == 0) {
			errors.add("Country Manager is mandatory.");
		}

		// no new fund requested and none of the existing funds used.
		if ((r.getFundForms() == null || r.getFundForms().size() == 0)
				&& (BooleanUtils.isFalse(r.getExistedFas()) || r.getExistedFas() == null)
				&& (BooleanUtils.isFalse(r.getExistedFe()) || r.getExistedFe() == null)
				&& (BooleanUtils.isFalse(r.getExistedFic()) || r.getExistedFic() == null)
				&& (BooleanUtils.isFalse(r.getExistedFid()) || r.getExistedFid() == null)) {
			errors.add("Investment is mandatory.");
		}

		if (r.getPolicyId() == null) {
			errors.add("Policy number is mandatory.");
		}
		if (r.getWorkflowItemId() == null) {
			errors.add("WorkflowItem is mandatory.");
		}
				
	}

	private void checkCountryManagers(AppFormDTO registration, List<String> errors) {
		checkCountryManagersSplit(registration, errors);
		checkDuplicateCountryManagers(registration, errors);
	}

	private void checkDuplicateCountryManagers(AppFormDTO registration, List<String> errors) {
		Set<String> partnerIds = new HashSet<>();
		for (PartnerFormDTO countryManager : registration.getCountryManagers()) {
			String partnerId = countryManager.getPartnerId();
			if (partnerIds.contains(partnerId)) {
				errors.add("Duplicate country manager " + partnerId + ".");
			}
			partnerIds.add(partnerId);
		}
	}

	private void checkCountryManagersSplit(AppFormDTO registration, List<String> errors) {
		if (CollectionUtils.isNotEmpty(registration.getCountryManagers())) {
			// check split total between the Country managers if more than one
			if (registration.getCountryManagers().size() > 1) {
				BigDecimal sumOfSplit = BigDecimal.ZERO;
				for (PartnerFormDTO partnerForm : registration.getCountryManagers()) {
					BigDecimal split = partnerForm.getSplit();
					if (split == null) {
						errors.add("Split is mandatory.");
					} else {
						sumOfSplit = sumOfSplit.add(split);
					}
				}
				if (ONE_HUNDRED.compareTo(sumOfSplit) != 0) {
					errors.add("The sum of splits must be equals to 100.");
				}
			} else if (registration.getCountryManagers().size() == 1) { // set default value to 100 if only one Country managers ???
				PartnerFormDTO CM = registration.getCountryManagers().iterator().next();
				CM.setSplit(ONE_HUNDRED);
			}
		}

	}

	private void checkCompanyFees(AppFormDTO registration, List<String> errors) {
		BigDecimal percent = registration.getCompanyEntryFeesPct();
		BigDecimal amount = registration.getCompanyEntryFeesAmt();

		if ((amount != null && amount.compareTo(ZERO) < 0) || (percent != null && percent.compareTo(ZERO) < 0)) {
			errors.add("Broker entry fees cannot be greater than the contract entry fees");
		}

	}

	private void checkFeesRange(AppFormDTO registration, List<String> errors) {
		// amount
		// paymentValidationService.validateMinFeesAmount(registration.getEntryFeesAmt(), "contract entry", errors);
		// paymentValidationService.validateMaxFeesPercentage(registration.getEntryFeesPct(), "contract entry", errors);
		paymentValidationService.validateMaxFeesPercentage(registration.getMngtFeesPct(), "contract management", errors);
		paymentValidationService.validateBrokerEntryFees(registration.getBroker(), registration.getEntryFeesPct(), registration.getEntryFeesAmt(), errors);
		PartnerFormDTO businessIntroducer = registration.getBusinessIntroducer();
		if (businessIntroducer != null) {
			paymentValidationService.validateMaxFeesPercentage(businessIntroducer.getEntryFeesPct(), "business introducer entry", errors);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.RegistrationValidationService#validateBeforeSave(lu.wealins.common.dto.webia.services.AppFormDTO)
	 */
	@Override
	public List<String> validateBeforeSave(StepDTO step) {
		AppFormDTO subscription = getFormData(step);
		List<String> errors = new ArrayList<>();
		// !!!! WARNING !!!
		// PUT only DB constraint here because it will stop any save of step
		// Every thing should be sauvable at this point except technical constraints
		if (subscription.getProductCd() == null) {
			errors.add(PRODUCT_IS_MANDATORY);
		}

		return errors;
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
