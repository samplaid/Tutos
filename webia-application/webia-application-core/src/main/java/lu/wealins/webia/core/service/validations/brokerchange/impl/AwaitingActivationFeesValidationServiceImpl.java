package lu.wealins.webia.core.service.validations.brokerchange.impl;

import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.AWAITING_ACTIVATION_FEES;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AdminFeeChangeDTO;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.webia.services.BrokerChangeFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.mapper.BrokerChangeFormMapper;
import lu.wealins.webia.core.service.LiabilityBrokerChangeService;
import lu.wealins.webia.core.service.LiabilityTransactionService;
import lu.wealins.webia.core.service.validations.RelaunchStepValidationService;
import lu.wealins.webia.core.service.validations.brokerchange.BrokerChangeFormValidationService;

@Service(value = "BrokerChangeFormAwaitingActivationFeesValidationService")
public class AwaitingActivationFeesValidationServiceImpl extends BrokerChangeFormValidationService {

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(AWAITING_ACTIVATION_FEES).collect(Collectors.toSet());
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private LiabilityTransactionService transactionService;

	@Autowired
	private LiabilityBrokerChangeService brokerChangeService;

	@Autowired
	private RelaunchStepValidationService relaunchStepValidationService;

	@Autowired
	private BrokerChangeFormMapper brokerChangeFormMapper;

	@Override
	public List<String> validateBeforeComplete(StepDTO step) {
		BrokerChangeFormDTO formData = getFormData(step);
		List<String> errors = new ArrayList<>();

		relaunchStepValidationService.validateEffectiveDate(formData, AWAITING_ACTIVATION_FEES.getvalue(), formData.getChangeDate(), errors);

		String policyId = formData.getPolicyId();
		Collection<TransactionDTO> awaitingAdministrationFees = transactionService.getAwaitingAdministrationFees(policyId, formData.getChangeDate());

		if (CollectionUtils.isNotEmpty(awaitingAdministrationFees)) {
			errors.add("There are awaiting administration fees linked to the policy " + policyId + " and the workflow item id " + formData.getWorkflowItemId() + ".");
		}

		BrokerChangeDTO brokerChange = brokerChangeFormMapper.asBrokerChangeDTO(formData);
		Date currentDate = new Date();
		Collection<AdminFeeChangeDTO> adminFeeChanges = brokerChangeService.getAdminFeeChanges(brokerChange);

		Set<String> awaitingAdminFeesErrors = new HashSet<>();
		adminFeeChanges.forEach(x -> {
			Date effectiveDateForAdminFees = x.getEffectiveDate();
			if (!currentDate.after(effectiveDateForAdminFees)) {
				awaitingAdminFeesErrors
						.add("The admin fees linked to the policy " + policyId + " and the workflow item id " + formData.getWorkflowItemId() + " will be applied the "
								+ DATE_FORMAT.format(x.getEffectiveDate()) + ".");
			}
		});

		errors.addAll(awaitingAdminFeesErrors);

		return errors;
	}

	@Override
	public boolean needValidateBeforeComplete(StepDTO step) {
		return ENABLE_STEPS.contains(StepTypeDTO.getStepType(step.getStepWorkflow()));
	}

	@Override
	public boolean needValidateBeforeSave(StepDTO step) {
		return false;
	}

}
