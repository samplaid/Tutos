package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.ScoreUtilityService;
import lu.wealins.webia.core.service.WebiaCheckDataService;

@Service
public class ScoreUtilityServiceImpl implements ScoreUtilityService {

	private static final String SCORE = "SCORE";

	@Autowired
	private WebiaCheckDataService webiaCheckDataService;

	@Autowired
	private LiabilityPolicyService liabilityPolicyService;

	@Override
	public void saveScoreInLissia(StepDTO step) {
		FormDataDTO formData = step.getFormData();
		if (formData == null) {
			return;
		}
		String policyId = formData.getPolicyId();
		if (!StringUtils.isEmpty(policyId)) {
			Integer workflowItemId = formData.getWorkflowItemId();

			if (workflowItemId == null) {
				return;
			}

			CheckDataDTO checkData = webiaCheckDataService.getCheckData(workflowItemId, SCORE);
			if (checkData != null && checkData.getDataValueNumber() != null) {
				WorkflowType workflowType = WorkflowType.getType(step.getWorkflowItemTypeId());

				int scoreValue = checkData.getDataValueNumber().intValue();
				if (workflowType == WorkflowType.APP_FORM) {
					liabilityPolicyService.saveScoreNewBusiness(policyId, scoreValue);
				} else {
					liabilityPolicyService.saveScoreLastTrans(policyId, scoreValue);
				}

			}
		}
	}

}
