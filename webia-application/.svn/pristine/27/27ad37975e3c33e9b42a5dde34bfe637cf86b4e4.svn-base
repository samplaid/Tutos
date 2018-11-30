package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.webia.services.AppFormEntryDTO;
import lu.wealins.common.dto.webia.services.OperationDTO;
import lu.wealins.common.security.SecurityContextHelper;
import lu.wealins.webia.core.mapper.OperationMapper;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.OperationService;
import lu.wealins.webia.core.service.WebiaAppFormEntryService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class OperationServiceImpl implements OperationService {

	private final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	private static final String PARTNER_ID_CANNOT_BE_NULL = "Partner id cannot be null.";
	private static final String PARTNER_CATEGORY_CANNOT_BE_NULL = "Partner category cannot be null.";
	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";

	private static final String WEBIA_POLICIES_OPERATIONS = "webia/operation/policies/opened";

	
	@Autowired
	private WebiaAppFormEntryService appFormEntryService;

	@Autowired
	private LiabilityPolicyService policyService;
	
	@Autowired
	private RestClientUtils restClientUtils;
	
	@Autowired
	private LiabilityWorkflowService workflowService;

	@Autowired
	private OperationMapper operationMapper;

	@Override
	public Collection<OperationDTO> getPartnerOperations(String partnerId, String partnerCategory, List<String> status) {
		Assert.notNull(partnerId, PARTNER_ID_CANNOT_BE_NULL);
		Assert.notNull(partnerCategory, PARTNER_CATEGORY_CANNOT_BE_NULL);
		List<String> policyIds = new ArrayList<String>();
		Collection<OperationDTO> operations = new ArrayList<OperationDTO>();
		
		List<String> lissiaPolicyIds = policyService.getPoliciesByAgent(partnerId, partnerCategory);
		if (CollectionUtils.isNotEmpty(lissiaPolicyIds)){
			policyIds.addAll(lissiaPolicyIds);
		}
		
		//we need to complete this above list with the subscription on going
		PageResult<AppFormEntryDTO> appFormEntries = appFormEntryService.getAppFormEntriesByPartner(1, 500, partnerId, partnerCategory, status);
		List<String> subscriptionPolicyIds = appFormEntries.getContent().stream().map(AppFormEntryDTO::getPolicyId).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(subscriptionPolicyIds)){
			policyIds.addAll(subscriptionPolicyIds);
		}
		
		//ask E-lissia the list of actions in progress
		if (CollectionUtils.isNotEmpty(policyIds)){
			logger.info("getPartnerOperations for policies : " + String.join(", ", policyIds));
			operations = restClientUtils.post(WEBIA_POLICIES_OPERATIONS, policyIds, new GenericType<Collection<OperationDTO>>() {});
		}
		
		enrichOperation(operations);
		return operations;
	}

	@Override
	public Collection<OperationDTO> getPolicyOperations(String policyId, List<String> status) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		Collection<OperationDTO> operations = restClientUtils.post(WEBIA_POLICIES_OPERATIONS, Arrays.asList(policyId), new GenericType<Collection<OperationDTO>>() {});
		enrichOperation(operations);
		
		return operations;
	}
	
	private void enrichOperation(Collection<OperationDTO> operations){
		for (OperationDTO operation : operations) {
			WorkflowGeneralInformationDTO workflowGeneralInformation = workflowService.getWorkflowGeneralInformation(operation.getWorkflowItemId(), SecurityContextHelper.getPreferredUsername());
			Assert.notNull(workflowGeneralInformation, "There is no workflow item " + operation.getWorkflowItemId() + ".");

			operation.setActionOther(workflowGeneralInformation.getActionOther());
			operation.setCurrentStepName(workflowGeneralInformation.getCurrentStepName());
		}
	}
	
}
