package lu.wealins.webia.core.service.validations.amendment;

import static lu.wealins.common.dto.webia.services.enums.StepTypeDTO.ANALYSIS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AmendmentFormDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.LiabilityPolicyService;

@Service(value = "AmendmentValidationService")
public class AmendmentValidationServiceImpl extends AmendmentValidationService {

	private static final String AMENDMENT_CHANGE_DATE_BEFORE_POLICY = "The change date of the amendment can't occur before the start of the policy";

	private static final Set<StepTypeDTO> ENABLE_STEPS = Stream.of(ANALYSIS).collect(Collectors.toSet());

	@Autowired
	private LiabilityPolicyService policyService;


	@Override
	public List<String> validateBeforeSave(StepDTO step) {

		List<String> errors = new ArrayList<>();
		AmendmentFormDTO formData = getFormData(step);

		if (formData.getPolicyId() != null) {
			Date policyStartDate = policyService.getPolicyLight(formData.getPolicyId()).getDateOfCommencement();

			if (policyStartDate != null && formData.getChangeDate() != null && formData.getChangeDate().before(policyStartDate)) {
				errors.add(AMENDMENT_CHANGE_DATE_BEFORE_POLICY);
			}
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
