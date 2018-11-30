package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.CheckDataContainerDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.webia.services.core.service.CheckDataService;
import lu.wealins.webia.services.ws.rest.CheckDataRESTService;

@Component
public class CheckDataRESTServiceImpl implements CheckDataRESTService {

	private static final String COMPLIANCE_FIRST_DECISION_CODE = "C_ACP";
	private static final String CHECK_ID_CANNOT_BE_NULL = "Check id cannot be null.";
	private static final String CHECK_CODES_CANNOT_BE_NULL = "Check codes cannot be null.";
	private static final String CHECK_CODE_CANNOT_BE_NULL = "Check code cannot be null.";
	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";
	private static final String SECURITY_CONTEXT_CANNOT_BE_NULL = "Security context cannot be null.";

	@Autowired
	private CheckDataService checkDataService;

	@Override
	public Map<String, CheckDataDTO> getCheckData(SecurityContext context, Integer workflowItemId, List<String> checkCodes) {
		Assert.notNull(context, SECURITY_CONTEXT_CANNOT_BE_NULL);
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(checkCodes, CHECK_CODES_CANNOT_BE_NULL);

		return checkDataService.getCheckData(workflowItemId, checkCodes);
	}

	@Override
	public CheckDataDTO getCheckData(SecurityContext context, Integer workflowItemId, String checkCode) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(checkCode, CHECK_CODE_CANNOT_BE_NULL);

		return checkDataService.getCheckData(workflowItemId, checkCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.CheckDataRESTService#update(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.CheckDataDTO)
	 */
	@Override
	public CheckDataDTO update(SecurityContext context, CheckDataDTO checkData) {
		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(checkData);

		return checkDataService.update(checkData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.CheckDataRESTService#update2(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.webia.services.CheckDataContainerDTO)
	 */
	@Override
	public CheckDataContainerDTO updateAll(SecurityContext context, CheckDataContainerDTO checkData) {
		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(checkData);

		return checkDataService.update(checkData);
	}

	@Override
	public CheckDataDTO getCheckData(SecurityContext context, String policyId, Integer checkId) {
		Assert.notNull(context, SECURITY_CONTEXT_CANNOT_BE_NULL);
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);
		Assert.notNull(checkId, CHECK_ID_CANNOT_BE_NULL);

		return checkDataService.getCheckData(policyId, checkId);
	}

	@Override
	public CheckDataDTO getAcceptanceDecision(Integer workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);

		return checkDataService.getCheckData(workflowItemId, COMPLIANCE_FIRST_DECISION_CODE);
	}
}
