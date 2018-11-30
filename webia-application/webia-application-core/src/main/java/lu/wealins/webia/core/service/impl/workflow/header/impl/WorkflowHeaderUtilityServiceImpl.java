package lu.wealins.webia.core.service.impl.workflow.header.impl;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FormDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;
import lu.wealins.common.dto.webia.services.enums.WorkflowType;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaWorkflowQueueService;
import lu.wealins.webia.core.service.impl.workflow.header.WorkflowHeaderUtilityService;

@Service
public class WorkflowHeaderUtilityServiceImpl implements WorkflowHeaderUtilityService {
	@Autowired
	private MetadataService metadataService;
	@Autowired
	private LiabilityPolicyService policyService;
	@Autowired
	private LiabilityProductService productService;
	@Autowired
	private LiabilityWorkflowService workflowService;
	@Autowired
	private WebiaWorkflowQueueService workflowQueueService;


	@Override
	public void setupHeader(StepDTO step, String usrId) {
		setupPolId(step);
		if (step.getPolId() != null && BooleanUtils.isTrue(policyService.isExist(step.getPolId()))) {
			PolicyDTO policy = policyService.getPolicy(step.getPolId());
			step.setApplicationForm(policy.getAdditionalId());
			setupProductHeader(step, policy.getProduct());
		} else {
			FormDataDTO formData = step.getFormData();
			if (WorkflowType.APP_FORM.getValue().intValue() == step.getWorkflowItemTypeId().intValue()) {
				AppFormDTO appForm = (AppFormDTO) formData;
				step.setApplicationForm(appForm.getApplicationForm());

				if (!StringUtils.isEmpty(appForm.getProductCd())) {
					setupProductHeader(step, productService.getProduct(appForm.getProductCd()));
				}
			}

		}
		setupFirstCpsUser(step, usrId);
		setupSecondCpsUser(step, usrId);
		setupAssignTo(step, usrId);
	}

	private void setupFirstCpsUser(StepDTO step, String usrId) {
		Integer workflowItemId = step.getWorkflowItemId();

		if (workflowItemId == null) {
			return;
		}
		MetadataDTO metadata = metadataService.getMetadata(workflowItemId + "", Metadata.FIRST_CPS_USER.getMetadata(), usrId);
		if (metadata != null && !StringUtils.isEmpty(metadata.getValue())) {
			step.setFirstCpsUser(metadata.getValue());
		} else {
			FormDataDTO formData = step.getFormData();
			if (formData != null) {
				step.setFirstCpsUser(formData.getFirstCpsUser());
			}
		}
	}

	private void setupAssignTo(StepDTO step, String usrId) {
		Integer workflowItemId = step.getWorkflowItemId();

		if (workflowItemId == null) {
			return;
		}
		WorkflowGeneralInformationDTO workflowGeneralInformation = workflowService.getWorkflowGeneralInformation(workflowItemId + "", usrId);

		if (workflowGeneralInformation != null) {
			String queueId = workflowGeneralInformation.getQueueId();
			if (!StringUtils.isEmpty(queueId)) {
				String name = workflowQueueService.getUserNameOrUserGroupName(workflowGeneralInformation.getQueueId());
				if (!StringUtils.isEmpty(name)) {
					step.setAssignedTo(name);
				}
			}
		}
	}

	private void setupSecondCpsUser(StepDTO step, String usrId) {
		Integer workflowItemId = step.getWorkflowItemId();

		if (workflowItemId == null) {
			return;
		}
		MetadataDTO metadata = metadataService.getMetadata(workflowItemId + "", Metadata.SECOND_CPS_USER.getMetadata(), usrId);
		if (metadata != null && !StringUtils.isEmpty(metadata.getValue())) {
			step.setSecondCpsUser(metadata.getValue());
		} else {
			FormDataDTO formData = step.getFormData();
			if (formData != null) {
				step.setSecondCpsUser(formData.getSecondCpsUser());
			}
		}
	}

	private void setupProductHeader(StepDTO step, ProductDTO product) {
		if (product != null) {
			step.setProductName(product.getName());
			step.setProductCd(product.getPrdId());
			step.setProductCountryCd(product.getNlCountry());
		}
	}

	private void setupPolId(StepDTO step) {
		String polId = step.getPolId();

		if (polId == null) {
			FormDataDTO formData = step.getFormData();

			if (formData != null) {
				polId = formData.getPolicyId();
			}

		}

		step.setPolId(polId);
	}

}
