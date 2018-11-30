package lu.wealins.webia.core.service.validations.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.webia.core.service.LiabilityControlService;
import lu.wealins.webia.core.service.validations.RelaunchStepValidationService;

@Service
public class RelaunchStepValidationServiceImpl implements RelaunchStepValidationService {

	private static final String IN_FORCE = "IN_FORCE";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private LiabilityControlService controlService;

	@Override
	public void validateInForceStatus(FormDataDTO formData, String workflowAction, List<String> errors) {
		if (!IN_FORCE.equals(formData.getStatusCd())) {
			errors.add(
					"Cannot relaunch " + workflowAction + " action for the workflow item id = " + formData.getWorkflowItemId() + " because the policy is not active (status: " + formData.getStatusCd()
							+ ").");
		}
	}

	@Override
	public void validateEffectiveDate(FormDataDTO formData, String workflowAction, Date effectiveDate, List<String> errors) {

		Date systemDate = controlService.getSystemDate();
		if (systemDate.before(effectiveDate)) {
			errors.add(
					"Cannot relaunch " + workflowAction + " action for the workflow item id =  " + formData.getWorkflowItemId() + " because the effective date (" + DATE_FORMAT.format(effectiveDate)
							+ ") is after the LISSIA system date (" + DATE_FORMAT.format(systemDate) + ").");

		}
	}
}
