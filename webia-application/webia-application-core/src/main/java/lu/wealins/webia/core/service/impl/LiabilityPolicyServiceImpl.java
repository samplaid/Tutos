package lu.wealins.webia.core.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ActivatePolicyRequest;
import lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest;
import lu.wealins.common.dto.liability.services.NewBusinessDTO;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyClientRoleViewRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;
import lu.wealins.webia.core.mapper.CreatePolicyCashSuspenseRequestMapper;
import lu.wealins.webia.core.mapper.NewBusinessMapper;
import lu.wealins.webia.core.service.LiabilityOptionDetailService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.WebiaBenefClauseStdService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.impl.exception.PolicyCreationException;
import lu.wealins.webia.ws.rest.request.PolicyScoreRequest;

@Service
public class LiabilityPolicyServiceImpl implements LiabilityPolicyService {

	private static final String COVERAGE_CANNOT_BE_NULL = "The coverage can't be null";
	private static final String FRANCAIS = "FRA";
	private static final String ENGLISH = "ENG";

	private static final String POLICY_CANNOT_BE_NULL = "Policy cannot be null.";
	private static final String SURRENDER_DTO_CANNOT_BE_NULL = "The surrender dto cannot be null.";
	private static final String SURRENDER_DATE_CANNOT_BE_NULL = "The surrender date cannot be null.";
	private static final String APP_FORM_CANNOT_BE_NULL = "Application form cannot be null.";
	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null.";
	private static final String WORKFLOW_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String FUND_ID_CANNOT_BE_NULL = "Fund id cannot be null.";
	private static final String AGENT_ID_CANNOT_BE_NULL = "Agent id cannot be null.";
	private static final String CATEGORY_CANNOT_BE_NULL = "Agent category cannot be null.";
	private static final String LIABILITY_POLICY = "liability/policy";
	private static final String LIABILITY_LOAD_POLICY_LIGHT = LIABILITY_POLICY + "/policyLight";
	private static final String LIABILITY_LOAD_POLICY_LIGH_WITH_WORKFLOW_ITEM_ID = LIABILITY_POLICY + "/policyLight/workflowItemId/";
	private static final String LIABILITY_LOAD_POLICY_WITH_WORKFLOW_ITEM_ID = LIABILITY_POLICY + "/workflowItemId/";
	private static final String LIABILITY_EXIST_POLICY = LIABILITY_POLICY + "/exist?id=";
	private static final String LIABILITY_EXIST_POLICY_BY_WF = LIABILITY_POLICY + "/existByWorkflowItemId?workflowItemId=";
	private static final String LIABILITY_CREATE_POLICY = LIABILITY_POLICY + "/create";
	private static final String LIABILITY_ACTIVATE_POLICY = LIABILITY_POLICY + "/activate/";
	private static final String LIABILITY_INCOMPLETE_DETAILS = LIABILITY_POLICY + "/incompleteDetails";
	private static final String LIABILITY_ROLES_BY_POLICIES = LIABILITY_POLICY + "/rolesByPolicies/";
	private static final String LIABILITY_POLICIES_BY_FUND = LIABILITY_POLICY + "/fund/";
	private static final String LIABILITY_POLICY_COVERAGE_ABORT = LIABILITY_POLICY + "/abortCoverage/";
	private static final String LIABILITY_POLICY_SURRENDER = LIABILITY_POLICY + "/surrender";
	private static final String LIABILITY_POLICY_AGENT = LIABILITY_POLICY + "/agent";
	private static final String LIABILITY_CREATE_POLICY_CASH_SUSPENSE = "liability/policy/createPolicyCashSuspense";
	private static final String LIABILITY_POLICY_CLAUSE = "liability/policy/clauses?id=";
	private static final String LIABILITY_POLICY_SAVE_SCORE_NEW_BUSINESS = "liability/policy/saveScoreNewBusiness";
	private static final String LIABILITY_POLICY_SAVE_SCORE_LAST_TRANS = "liability/policy/saveScoreLastTrans";

	private static final String LIABILITY_POLICY_UPDATE_ADDITIONAL_PREMIUM = "liability/policy/updatePolicyAdditionalPremium/";
	private static final String LIABILITY_WITHDRAWAL = "liability/policy/createWithdrawal";
	private static final String LIABILITY_WITHDRAWALS = "liability/policy/createWithdrawals";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private NewBusinessMapper newBusinessMapper;

	@Autowired
	private LiabilityOptionDetailService optionDetailService;

	@Autowired
	private WebiaBenefClauseStdService webiaBenefClauseStdService;


	@Autowired
	private CreatePolicyCashSuspenseRequestMapper createPolicyCashSuspenseRequestMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#getPolicy(java.lang.String)
	 */
	@Override
	public PolicyDTO getPolicy(String policyId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_POLICY + "?id=", policyId, PolicyDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#getPolicyLight(java.lang.String)
	 */
	@Override
	public PolicyLightDTO getPolicyLight(String policyId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_LOAD_POLICY_LIGHT + "?id=", policyId, PolicyLightDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#getPolicy(lu.wealins.webia.ws.rest.dto.PolicyClientRoleViewRequest)
	 */
	@Override
	public PolicyDTO getPolicy(PolicyClientRoleViewRequest request) {
		Assert.notNull(request);

		return restClientUtils.post(LIABILITY_POLICY + "/clientRoleView", request, PolicyDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#getPolicy(java.lang.Long)
	 */
	@Override
	public PolicyDTO getPolicy(Long workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_LOAD_POLICY_WITH_WORKFLOW_ITEM_ID, workflowItemId.toString(), PolicyDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#isExist(java.lang.String)
	 */
	@Override
	public Boolean isExist(String policyId) {
		Assert.notNull(policyId, POLICY_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_EXIST_POLICY, policyId, Boolean.class);
	}

	@Override
	public Boolean isExistByWorkflowItemId(String workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_EXIST_POLICY_BY_WF, workflowItemId, Boolean.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#createPolicy(lu.wealins.webia.ws.rest.dto.AppFormDTO)
	 */
	@Override
	public PolicyDTO createPolicy(AppFormDTO appForm) {
		NewBusinessDTO request = newBusinessMapper.asNewBusinessDTO(appForm);
		if (isExist(appForm.getPolicyId())) {
			request.getImpCallMethodCommunications().setCallFunction("UPDATE");
		}
		return restClientUtils.post(LIABILITY_CREATE_POLICY, request, PolicyDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#activatePolicy(java.lang.String)
	 */
	@Override
	public PolicyDTO activatePolicy(String id, Date effectiveDate) {
		Assert.notNull(id, POLICY_ID_CANNOT_BE_NULL);
		ActivatePolicyRequest request = new ActivatePolicyRequest();

		request.setEffectiveDate(effectiveDate);
		request.setId(id);

		return restClientUtils.post(LIABILITY_ACTIVATE_POLICY, request, PolicyDTO.class);
	}

	@Override
	public PolicyDTO updatePolicyAdditionalPremium(String id, Date effectiveDate) {
		ActivatePolicyRequest request = new ActivatePolicyRequest();

		request.setEffectiveDate(effectiveDate);
		request.setId(id);

		return restClientUtils.post(LIABILITY_POLICY_UPDATE_ADDITIONAL_PREMIUM, request, PolicyDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#getPolicyIncompleteDetails(java.lang.String)
	 */
	@Override
	public Collection<String> getPolicyIncompleteDetails(String id) {
		Assert.notNull(id, POLICY_ID_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.put("id", Arrays.asList(id));

		return restClientUtils.get(LIABILITY_INCOMPLETE_DETAILS, "", params, new GenericType<Collection<String>>() {
			// nothing to do.
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#createPolicyCashSuspense(lu.wealins.common.dto.webia.services.AppFormDTO)
	 */
	@Override
	public Boolean createPolicyCashSuspense(AppFormDTO appForm) {
		Assert.notNull(appForm, APP_FORM_CANNOT_BE_NULL);

		CreatePolicyCashSuspenseRequest request = createPolicyCashSuspenseRequestMapper.asCreatePolicyCashSuspenseRequest(appForm);

		return restClientUtils.post(LIABILITY_CREATE_POLICY_CASH_SUSPENSE, request, Boolean.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#getRolesByPolicies(java.lang.Integer)
	 */
	@Override
	public RolesByPoliciesDTO getRolesByPolicies(Integer clientId) {
		List<OptionDetailDTO> cprRoles = optionDetailService.getCPRRoles();

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		cprRoles.forEach(x -> params.add("cliPolRelationshipTypes", Integer.valueOf(x.getNumber())));

		return restClientUtils.get(LIABILITY_ROLES_BY_POLICIES, clientId + "", params, RolesByPoliciesDTO.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.LiabilityPolicyService#clauses(java.lang.String)
	 */
	@Override
	public PolicyClausesDTO getClauses(String polId) {
		return restClientUtils.get(LIABILITY_POLICY_CLAUSE, polId, PolicyClausesDTO.class);
	}

	@Override
	public PolicyClausesDTO getClauses(String polId, String productCd, String lang) {
		PolicyClausesDTO clauses = getClauses(polId);
		return getTranslatedClauses(clauses,productCd,lang );
	}

	@Override
	public PolicyClausesDTO getTranslatedClauses(PolicyClausesDTO clauses, String productCd, String lang) {
		// find code text for standard clauses (only in death normally)
		Collection<BenefClauseStdDTO> benefClauseStd = webiaBenefClauseStdService.getBenefClauseStd(productCd);
		translateTextOfClauses(clauses.getDeath(), benefClauseStd, lang);
		translateTextOfClauses(clauses.getMaturity(), benefClauseStd, lang);
		return clauses;
	}
	
	private void translateTextOfClauses(List<PolicyBeneficiaryClauseDTO> policyBeneficiaryClauses, Collection<BenefClauseStdDTO> benefClauseStd, String lang) {
		policyBeneficiaryClauses.forEach(clause -> {
			if (StringUtils.isNotBlank(clause.getCode())) {
				Map<String, BenefClauseStdDTO> translates = benefClauseStd.stream()
						.filter(c -> c.getBenefClauseCd().equals(clause.getCode().trim()))
						.collect(Collectors.toMap(BenefClauseStdDTO::getLangCd, c -> c));
				BenefClauseStdDTO bcs = null;
				if (translates.containsKey(lang)) {
					bcs = translates.get(lang);
				} else if (translates.containsKey(ENGLISH)) {
					bcs = translates.get(ENGLISH);
				} else if (translates.containsKey(FRANCAIS)) {
					bcs = translates.get(FRANCAIS);
				} else {
					throw new PolicyCreationException("Impossible to find text of standard clause with code " + clause.getCode() + " in language '" + ENGLISH + "' or '" + FRANCAIS + "'");
				}
				clause.setTextOfClause(clause.getRank() + ". " + bcs.getBenefClauseText());
			}
		});
	}

	@Override
	public Boolean saveScoreNewBusiness(String policyId, Integer scoreValue) {
		PolicyScoreRequest scoreRequest = createPolicyScoreRequest(policyId, scoreValue);

		return restClientUtils.post(LIABILITY_POLICY_SAVE_SCORE_NEW_BUSINESS, scoreRequest, Boolean.class);
	}

	@Override
	public Boolean saveScoreLastTrans(String policyId, Integer scoreValue) {
		PolicyScoreRequest scoreRequest = createPolicyScoreRequest(policyId, scoreValue);

		return restClientUtils.post(LIABILITY_POLICY_SAVE_SCORE_LAST_TRANS, scoreRequest, Boolean.class);
	}

	private PolicyScoreRequest createPolicyScoreRequest(String policyId, Integer scoreValue) {
		PolicyScoreRequest scoreRequest = new PolicyScoreRequest();
		scoreRequest.setPolicyId(policyId);
		scoreRequest.setScoreValue(scoreValue);
		return scoreRequest;
	}

	@Override
	public boolean isActive(PolicyDTO policy) {
		Assert.notNull(policy, POLICY_CANNOT_BE_NULL);

		return "ACTIVE".equals(policy.getActiveStatus());
	}

	@Override
	public Collection<String> getPoliciesByFund(String fundId) {

		Assert.notNull(fundId, FUND_ID_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.put("fundId", Arrays.asList(fundId));

		return restClientUtils.get(LIABILITY_POLICIES_BY_FUND, "", params, new GenericType<Collection<String>>() {
		});
	}

	@Override
	public void abortCoverage(String policyId, Integer coverage) {
		Assert.notNull(policyId, POLICY_CANNOT_BE_NULL);
		Assert.notNull(coverage, COVERAGE_CANNOT_BE_NULL);

		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.put("policyId", Arrays.asList(policyId));
		params.put("coverage", Arrays.asList(coverage.toString()));

		restClientUtils.get(LIABILITY_POLICY_COVERAGE_ABORT, "", params, Boolean.class);
	}
	
	@Override
	public void surrenderPolicy(PolicySurrenderDTO surrenderDTO) {
		Assert.notNull(surrenderDTO, SURRENDER_DTO_CANNOT_BE_NULL);
		Assert.notNull(surrenderDTO.getPolicyId(), POLICY_CANNOT_BE_NULL);
		Assert.notNull(surrenderDTO.getEffectiveDate(), SURRENDER_DATE_CANNOT_BE_NULL);

		restClientUtils.post(LIABILITY_POLICY_SURRENDER, surrenderDTO, Boolean.class);
	}

	@Override
	public List<String> getPoliciesByAgent(String partnerId, String partnerCategory){
		Assert.notNull(partnerId, AGENT_ID_CANNOT_BE_NULL);
		Assert.notNull(partnerCategory, CATEGORY_CANNOT_BE_NULL);
		
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("agtId", partnerId);
		params.add("category", partnerCategory);
		return restClientUtils.get(LIABILITY_POLICY_AGENT, "", params, new GenericType<List<String>>() {});	
	}
	@Override
	public PolicyLightDTO getPolicyLight(Integer workflowItemId) {
		Assert.notNull(workflowItemId, WORKFLOW_ID_CANNOT_BE_NULL);

		return restClientUtils.get(LIABILITY_LOAD_POLICY_LIGH_WITH_WORKFLOW_ITEM_ID, workflowItemId.toString(), PolicyLightDTO.class);
	}

	@Override
	public Boolean createWithdrawal(WithdrawalInputDTO input) {
		return restClientUtils.post(LIABILITY_WITHDRAWAL, input, Boolean.class);
	}

	@Override
	public Boolean createWithdrawal(List<WithdrawalInputDTO> inputs) {
		return restClientUtils.post(LIABILITY_WITHDRAWALS, inputs, Boolean.class);
	}

}

