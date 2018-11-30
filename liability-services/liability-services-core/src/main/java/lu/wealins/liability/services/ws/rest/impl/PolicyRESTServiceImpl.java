package lu.wealins.liability.services.ws.rest.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.ActivatePolicyRequest;
import lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest;
import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.NewBusinessDTO;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpUseridUsers;
import lu.wealins.common.dto.liability.services.PoliciesForMathematicalReserveRequest;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyClientRoleViewRequest;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.PolicyIdsSearchDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicyRecreateResponse;
import lu.wealins.common.dto.liability.services.PolicyScoreRequest;
import lu.wealins.common.dto.liability.services.PolicySearchRequest;
import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.liability.services.PolicyTransactionValuationDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.InsuredService;
import lu.wealins.liability.services.core.business.PolicyClausesService;
import lu.wealins.liability.services.core.business.PolicyHolderService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyValuationService;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.SecurityContextUtils;
import lu.wealins.liability.services.ws.rest.PolicyRESTService;


@Component
public class PolicyRESTServiceImpl implements PolicyRESTService {

	private static final String REQUEST_CANNOT_BE_NULL = "Policy search request cannot be null";
	private static final String BROKERID_CANNOT_BE_NULL = "Broker id cannot be null";

	@Autowired
	private PolicyService policyService;
	@Autowired
	private PolicyValuationService valuationService;
	@Autowired
	private PolicyClausesService policyClausesService;
	@Autowired
	private SecurityContextUtils securityContextUtils;
	@Autowired
	protected InsuredService insuredService;
	@Autowired
	protected PolicyHolderService holderService;
	@Autowired
	private CalendarUtils calendarUtils;
	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;
	@Autowired
	private FundService fundService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getPolicy(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public PolicyDTO getPolicy(SecurityContext context, String id) {
		return policyService.getPolicy(id);
	}

	@Override
	public PolicyDTO getPolicy(SecurityContext context, PolicyClientRoleViewRequest policyClientRoleViewRequest) {
		return policyService.getPolicy(policyClientRoleViewRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getPolicy(javax.ws.rs.core.SecurityContext, java.lang.Long)
	 */
	@Override
	public PolicyDTO getPolicy(SecurityContext context, Long workflowItemId) {
		return policyService.getPolicy(workflowItemId, securityContextUtils.getPreferredUsername(context));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getPolicyLight(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public PolicyLightDTO getPolicyLight(SecurityContext context, String id) {
		return policyService.getPolicyLight(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getRolesByPolicies(javax.ws.rs.core.SecurityContext, java.lang.Integer, java.util.List)
	 */
	@Override
	public RolesByPoliciesDTO getRolesByPolicies(SecurityContext context, Integer clientId, List<Integer> cliPolRelationshipTypes) {
		return policyService.getRolesByPolicies(clientId, CliPolRelationshipType.toCliPolRelationshipTypes(cliPolRelationshipTypes));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getBrokerPolicies(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.PolicySearchRequest)
	 */
	@Override
	public SearchResult<PolicyLightDTO> getBrokerPolicies(SecurityContext context, PolicySearchRequest request) {
		Assert.notNull(request, REQUEST_CANNOT_BE_NULL);
		Assert.notNull(request.getBrokerId(), BROKERID_CANNOT_BE_NULL);
		return policyService.getBrokerPolicies(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getBrokerValuation(javax.ws.rs.core.SecurityContext, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PolicyValuationDTO getBrokerValuation(SecurityContext ctx, String id, String currency) {
		Assert.notNull(id, BROKERID_CANNOT_BE_NULL);

		currency = (StringUtils.hasText(currency)) ? "EUR" : currency;
		return policyService.getBrokerValuation(id, currency);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#isExist(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public Boolean isExist(SecurityContext context, String id) {
		return Boolean.valueOf(policyService.isExist(id));
	}
	
	@Override
	public Boolean isExistByWorkflowItemId(SecurityContext context, Long workflowItemId) {
		return policyService.isExistByWorkflowItemId(workflowItemId, securityContextUtils.getPreferredUsername(context));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getNewNumber(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public String getNewNumber(SecurityContext ctx, String product) {
		return policyService.getNewNumber(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#search(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.PolicySearchRequest)
	 */
	@Override
	public SearchResult<PolicyDTO> search(SecurityContext context, PolicySearchRequest request) {
		return policyService.searchPolicy(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getValuation(javax.ws.rs.core.SecurityContext, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PolicyValuationDTO getValuation(SecurityContext ctx, String id, String date, String cur) throws ParseException {

		Date dt = DateUtils.truncate(new Date(), Calendar.DATE);

		if (StringUtils.hasText(date)) {
			dt = calendarUtils.createDate(date);
		}

		return valuationService.getPolicyValuation(id, dt, cur);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#clauses(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public PolicyClausesDTO clauses(SecurityContext ctx, String policy) {
		return policyClausesService.getClauses(policy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#createPolicy(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.NewBusinessDTO)
	 */
	@Override
	public PolicyDTO createPolicy(SecurityContext ctx, NewBusinessDTO request) {
		if (request != null) {
			if (request.getImpUseridUsers() == null) {
				request.setImpUseridUsers(new ImpUseridUsers());
			}
			String preferredUsername = securityContextUtils.getPreferredUsername(ctx);
			request.getImpUseridUsers().setUsrId(preferredUsername);
		}
		return policyService.createPolicy(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#activatePolicy(javax.ws.rs.core.SecurityContext, lu.wealins.common.dto.liability.services.ActivatePolicyRequest)
	 */
	@Override
	public PolicyDTO activatePolicy(SecurityContext ctx, ActivatePolicyRequest request) {
		policyService.activatePolicy(request);
		return getPolicy(ctx, request.getId());
	}

	@Override
	public PolicyDTO updatePolicy(SecurityContext ctx, ActivatePolicyRequest request) {
		policyService.updatePolicy(request);
		return getPolicy(ctx, request.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#getPolicyIncompleteDetails(javax.ws.rs.core.SecurityContext, java.lang.String)
	 */
	@Override
	public List<String> getPolicyIncompleteDetails(SecurityContext ctx, String id) {
		return policyService.getPolicyIncompleteDetails(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.ws.rest.PolicyRESTService#createPolicyCashSuspense(javax.ws.rs.core.SecurityContext,
	 * lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest)
	 */
	@Override
	public Boolean createPolicyCashSuspense(SecurityContext ctx, CreatePolicyCashSuspenseRequest request) {
		return policyService.createPolicyCashSuspense(request);
	}

	@Override
	public SearchResult<PolicyDTO> getPoliciesForMathematicalReserve(SecurityContext context, PoliciesForMathematicalReserveRequest request) {
		return policyService.getPoliciesForMathematicalReserve(request);
	}


	@Override
	public Boolean saveScoreNewBusiness(SecurityContext ctx, PolicyScoreRequest scoreRequest) {
		if (scoreRequest == null) {
			return Boolean.FALSE;
		}
		return policyService.saveScoreNewBusiness(scoreRequest.getPolicyId(), scoreRequest.getScoreValue());
	}

	@Override
	public Boolean saveScoreLastTrans(SecurityContext ctx, PolicyScoreRequest scoreRequest) {
		if (scoreRequest == null) {
			return false;
		}
		return policyService.saveScoreLastTrans(scoreRequest.getPolicyId(), scoreRequest.getScoreValue());
	}


	@Override
	public Boolean abort(SecurityContext ctx, String policyId) {
		return policyService.abort(policyId);
	}

	@Override
	public PolicyRecreateResponse recreate(SecurityContext ctx, Long workflowItemId) {
		return policyService.recreate(workflowItemId, securityContextUtils.getPreferredUsername(ctx));
	}

	@Override
	public List<String> getPoliciesByFund(SecurityContext context, String fundId) {
		return policyService.getPoliciesByFund(fundId);
	}
	
	@Override
	public List<String> getPoliciesByAgent(SecurityContext context, String agtId , String category) throws Exception {
		if (!AgentCategory.getCategories().contains(category)){
			throw new Exception("Agent category not supported :" + category + ". Expected values AM, BK, DB.");
		}
		List<String> policies = new ArrayList<String>();
		if (AgentCategory.DEPOSIT_BANK.getCategory().equals(category) || AgentCategory.ASSET_MANAGER.getCategory().equals(category)){
			// find the fund linked to the Agent and then retrieve the policies linked to those funds
			policies = fundService.getFundIds(agtId).stream().collect(Collectors.toList());
		} else {
			// retrieve the policies linked to the agent
			policies = policyService.getAgentPolicies(agtId);
		}
		return policies;
	}	

	@Override
	public Collection<PolicyTransactionValuationDTO> getPolicyValuationAfterTransaction(SecurityContext context,
			Long transactionId) {
		return valuationService.getPolicyValuationAfterTransaction(transactionId);
	}
	@Override
	public Boolean abortCoverage(SecurityContext context, String policyId, Integer coverage) {
		return policyService.abortCoverage(policyId, coverage);
	}

	@Override
	public Collection<InsuredDTO> getDeceasedInsureds(SecurityContext context, String policyId) {
		return insuredService.getDeadInsureds(cliPolRelationshipService.findByPolicyId(policyId));
	}

	@Override
	public Collection<PolicyHolderDTO> getDeceasedHolders(SecurityContext context, String policyId) {
		return holderService.getDeadHolders(cliPolRelationshipService.findByPolicyId(policyId));
	}

	@Override
	public PolicyLightDTO getPolicyLight(SecurityContext context, Long workflowItemId) {
		return policyService.getPolicyLight(workflowItemId, securityContextUtils.getPreferredUsername(context));
	}

	@Override
	public Boolean createWithdrawal(SecurityContext ctx, WithdrawalInputDTO input) {
		return policyService.createWithdrawal(input);
	}

	@Override
	public Boolean createWithdrawals(SecurityContext ctx, List<WithdrawalInputDTO> inputs) {
		return policyService.createWithdrawal(inputs);
	}

	@Override
	public List<String> searchPolicyIds(PolicyIdsSearchDTO policyIdsSearchDTO) {
		return policyService.searchPolicyIds(policyIdsSearchDTO);
	}

	@Override
	public Boolean surrenderPolicy(PolicySurrenderDTO policySurrenderDTO) {
		return policyService.surrenderPolicy(policySurrenderDTO);
	}
}
