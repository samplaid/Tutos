package lu.wealins.liability.services.core.business.impl;


import static lu.wealins.liability.services.core.utils.constantes.Constantes.WS_LISSIA_EXPECTED_CODE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.tempuri.wsschsus.WSSCHSUS;
import org.tempuri.wsschsus.WsschsusImport;
import org.tempuri.wssinfpl.WSSINFPL;
import org.tempuri.wssinfpl.WssinfplExport;
import org.tempuri.wssinfpl.WssinfplImport;
import org.tempuri.wssnbset.WSSNBSET;
import org.tempuri.wssnbset.WssnbsetImport;
import org.tempuri.wsspfhwd.WSSPFHWD;
import org.tempuri.wsspfhwd.WsspfhwdExport;
import org.tempuri.wsspfhwd.WsspfhwdImport;
import org.tempuri.wsspolicd.WSSPOLICD;
import org.tempuri.wsspolicd.WsspolicdImport;
import org.tempuri.wsspscup.Exception_Exception;
import org.tempuri.wsspscup.WSSPSCUP;
import org.tempuri.wsspscup.WsspscupExport;
import org.tempuri.wsspscup.WsspscupImport;
import org.tempuri.wssrspol.WSSRSPOL;
import org.tempuri.wssrspol.WssrspolExport;

import lu.wealins.common.dto.liability.services.ActivatePolicyRequest;
import lu.wealins.common.dto.liability.services.CreatePolicyCashSuspenseRequest;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.NewBusinessDTO;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCpr;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;
import lu.wealins.common.dto.liability.services.PoliciesForMathematicalReserveRequest;
import lu.wealins.common.dto.liability.services.PolicyClientRoleViewRequest;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyIdsSearchDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.PolicyRecreateResponse;
import lu.wealins.common.dto.liability.services.PolicySearchRequest;
import lu.wealins.common.dto.liability.services.PolicySurrenderDTO;
import lu.wealins.common.dto.liability.services.PolicyValuationDTO;
import lu.wealins.common.dto.liability.services.RolesByPoliciesDTO;
import lu.wealins.common.dto.liability.services.RolesByPolicyDTO;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.TransactionDTO;
import lu.wealins.common.dto.liability.services.UpdateInputRequest;
import lu.wealins.common.dto.liability.services.WithdrawalInputDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.common.dto.liability.services.enums.ControlDefinitionType;
import lu.wealins.common.dto.liability.services.enums.EventType;
import lu.wealins.common.dto.liability.services.enums.PolicyActiveStatus;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareType;
import lu.wealins.common.dto.liability.services.enums.PolicyStatus;
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.business.OptionDetailService;
import lu.wealins.liability.services.core.business.PolicyAgentShareService;
import lu.wealins.liability.services.core.business.PolicyCoverageService;
import lu.wealins.liability.services.core.business.PolicyHolderService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.business.PolicyTransferService;
import lu.wealins.liability.services.core.business.PolicyValuationService;
import lu.wealins.liability.services.core.business.ProductValueService;
import lu.wealins.liability.services.core.business.TransactionService;
import lu.wealins.liability.services.core.business.WorkflowItemService;
import lu.wealins.liability.services.core.business.exceptions.CfiException;
import lu.wealins.liability.services.core.business.exceptions.PolicyException;
import lu.wealins.liability.services.core.business.exceptions.WebServiceInvocationException;
import lu.wealins.liability.services.core.mapper.NewBusinessMapper;
import lu.wealins.liability.services.core.mapper.PolicyMapper;
import lu.wealins.liability.services.core.mapper.PolicyWithClientRoleActivationMapper;
import lu.wealins.liability.services.core.mapper.ProductMapper;
import lu.wealins.liability.services.core.mapper.WsschsusImportMapper;
import lu.wealins.liability.services.core.mapper.WssinfplImportMapper;
import lu.wealins.liability.services.core.mapper.WsspfhwdImportMapper;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyTransferEntity;
import lu.wealins.liability.services.core.persistence.repository.CliPolRelationshipRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyAgentShareRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyRepository;
import lu.wealins.liability.services.core.persistence.repository.UoptDetailRepository;
import lu.wealins.liability.services.core.persistence.specification.PolicySpecifications;
import lu.wealins.liability.services.core.utils.HistoricManager;

@Service
public class PolicyServiceImpl implements PolicyService {

	private static final String BROKER_FEES_CANT_BE_NULL = "The broker fees can't be null";
	private static final String POLICY = "Policy";
	private static final String CANNOT_CREATE_POLICY_CASH_SUSPENSE_THE_POLICY_IS_EMPTY = "Cannot create policy cash suspense. The policy is empty.";
	private static final String CLIENT_ID_CANNOT_BE_NULL = "Client id cannot be null";
	private static final String CLI_POL_RELATIONSHIP_TYPES_ID_CANNOT_BE_NULL = "Client policy relationship types cannot be null";
	private static final String EUR = "EUR";
	private static final String POLICY_ID_CANNOT_BE_NULL = "Policy id cannot be null";
	private static final String WORKFLOW_ID_CANNOT_BE_NULL = "Workflow id cannot be null";
	private static final String POLICY_UPDATE_CALL_FUNCTION = "COV";
	private static final String POLICY_SURRENDER_DTO_NOT_NULL = "The surrender dto can't be null";	
	private static final String SURRENDER_DATE_NOT_NULL = "The surrender date can't be null";
	private static final String SURRENDER_FEES_NOT_NULL = "The surrender fees can't be null";
	private static final String SURRENDER_BROKER_FEES_NOT_NULL = "The surrender broker fees can't be null";

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	@Autowired
	private PolicyRepository policyRepository;

	@Autowired
	private PolicyAgentShareRepository policyAgentShareRepository;

	@Autowired
	private PolicyAgentShareService policyAgentShareService;

	@Autowired
	private ProductValueService productValueService;

	@Autowired
	private CliPolRelationshipRepository cliPolRelationshipRepository;

	@Autowired
	private PolicyMapper policyMapper;

	@Autowired
	private PolicyWithClientRoleActivationMapper policyWithClientRoleActivationMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private WssinfplImportMapper wssinfplImportMapper;

	@Autowired
	private OptionDetailService optionDetailService;

	@Autowired
	private PolicyHolderService policyHolderService;

	@Autowired
	private PolicyCoverageService policyCoverageService;

	@Autowired
	private PolicyValuationService policyValuationService;

	@Autowired
	private WorkflowItemService workflowItemService;

	@Autowired
	private WSSRSPOL WSSRSPOL;

	@Autowired
	private WSSNBSET wssnbset;

	@Autowired
	private WSSINFPL wssinfpl;

	@Autowired
	private WSSCHSUS wsschsus;

	@Autowired
	private WSSPSCUP wsspscup;

	@Autowired
	private WSSPFHWD wsspfhwd;

	@Autowired
	private WSSPOLICD wsspolicd;

	@Autowired
	private NewBusinessMapper newBusinessMapper;

	@Autowired
	private WsschsusImportMapper wsschsusImportMapper;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private FundTransactionService fundTransactionService;

	@Autowired
	private UoptDetailRepository categoryRepository;

	@Autowired
	private HistoricManager historicManager;

	@Autowired
	private PolicyIdHelper policyIdHelper;

	@Autowired
	private PolicyTransferService policyTransferService;

	@Autowired
	private PolicyWsRequestHelper policyWsRequestHelper;

	@Autowired
	private WsspfhwdImportMapper wsspfhwdImportMapper;

	private final static Logger LOGGER = LoggerFactory.getLogger(PolicyServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#createPolicy(lu.wealins.common.dto.liability.services.NewBusinessDTO)
	 */
	@Override
	public PolicyDTO createPolicy(NewBusinessDTO request) {
		try {
			WssnbsetImport wssnbsetImport = newBusinessMapper.asWssnbsetImport(request);
			wssnbset.wssnbsetcall(wssnbsetImport);
		} catch (org.tempuri.wssnbset.Exception_Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}

		String polId = request.getImpPolicyPolicies().getPolId();
		PolicyEntity policyEntity = getPolicyEntity(polId);

		updatePercentageSplitForBeneficiaryOfZeroRank(request, policyEntity, CliPolRelationshipType.BENEFICIARY_AT_DEATH);
		updatePercentageSplitForBeneficiaryOfZeroRank(request, policyEntity, CliPolRelationshipType.BENEFICIARY_AT_MATURITY);
		updatePolicyTransfers(request, policyEntity);
		// Update the policy agent shares percentages after a complete step of validate input
		updatePolicyAgentSharesPercentage(request, policyEntity);

		return policyMapper.asPolicyDTO(policyEntity);
	}
	
	/**
	 * Update percentages for policy agent shares.
	 * 
	 * @param request the request which contain the new percentages
	 * @param policyEntity the policy to find the correct policy agent shares
	 */
	private void updatePolicyAgentSharesPercentage(NewBusinessDTO request, PolicyEntity policyEntity) {
		String policyId = policyEntity.getPolId();
		PolicyLightDTO policyLight = getPolicyLight(policyId);
		Integer coverage = policyLight.getFirstCoverage().getCoverage();
		String agentId = getBroker(policyLight);
		
		if (agentId != null) {
			policyAgentShareService.createOrUpdateAgentShare(agentId, PolicyAgentShareType.INITIAL_COMM_FEE, policyId, coverage, request.getEntryFeesPct());
			policyAgentShareService.createOrUpdateAgentShare(agentId, PolicyAgentShareType.ADVISOR_FEES, policyId, coverage, request.getMngtFeesPct());
		}
	}

	private void updatePolicyTransfers(NewBusinessDTO request, PolicyEntity policyEntity) {
		Collection<PolicyTransferEntity> policyTransfers = policyTransferService.update(request.getPolicyTransfers());

		policyEntity.setPolicyTransfers(policyTransfers.stream().collect(Collectors.toSet()));

	}

	/**
	 * It is possible to have null split for beneficiary having a rank of ZERO. However Lissia put Zero instead of null and we need to update this value after the webservice call. Moreover Lissia save
	 * rank 0 into rank 1.
	 * 
	 * @param request The new business request
	 * @param policyEntity The policy.
	 * @param beneficiaryType The Beneficiary type.
	 */
	private void updatePercentageSplitForBeneficiaryOfZeroRank(NewBusinessDTO request, PolicyEntity policyEntity, CliPolRelationshipType beneficiaryType) {
		Assert.notNull(request);
		Assert.notNull(policyEntity);
		Assert.notNull(beneficiaryType);

		if (!(beneficiaryType.equals(CliPolRelationshipType.BENEFICIARY_AT_DEATH) || beneficiaryType.equals(CliPolRelationshipType.BENEFICIARY_AT_MATURITY))) {
			throw new IllegalStateException("Unknown beneficary type.");
		}

		ImpGrpCpr impGrpCpr = request.getImpGrpCpr();
		if (impGrpCpr != null && impGrpCpr.getRows() != null) {
			List<ImpItmCprCliPolRelationships> impItmCprCliPolRelationships = impGrpCpr.getRows().stream().map(x -> x.getImpItmCprCliPolRelationships())
					.filter(x -> x.getType() == beneficiaryType.getValue() && x.getPercentageSplit() == null && x.getTypeNumber() == 0).collect(Collectors.toList());

			// Allow to save percentage split equals to null instead of 0 (after the Lissia WS call)
			impItmCprCliPolRelationships
					.forEach(x -> {
						CliPolRelationshipEntity cliPolRelationshipEntity = policyEntity.getCliPolRelationships().stream()
								.filter(y -> y.getType() == x.getType() && x.getClient() == y.getClientId() && y.getPercentageSplit() != null && y.getPercentageSplit().compareTo(BigDecimal.ZERO) == 0
										&& x.getPercentageSplit() == null)
								.findFirst()
								.orElse(null);
						if (cliPolRelationshipEntity != null) {
							cliPolRelationshipEntity.setPercentageSplit(null);
							cliPolRelationshipRepository.saveAndFlush(cliPolRelationshipEntity);
						}
					});

			// Allow to save type number equals to 0 instead of 1 (after the Lissia WS call)
			impItmCprCliPolRelationships
					.forEach(x -> {
						CliPolRelationshipEntity cliPolRelationshipEntity = policyEntity.getCliPolRelationships().stream()
								.filter(y -> y.getType() == x.getType() && x.getClient() == y.getClientId() && y.getTypeNumber() != null && y.getTypeNumber() == 1 && x.getTypeNumber() == 0)
								.findFirst()
								.orElse(null);
						if (cliPolRelationshipEntity != null) {
							cliPolRelationshipEntity.setTypeNumber(Integer.valueOf(0));
							cliPolRelationshipRepository.saveAndFlush(cliPolRelationshipEntity);
						}
					});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#activatePolicy(lu.wealins.common.dto.liability.services.ActivatePolicyRequest)
	 */
	@SuppressWarnings("boxing")
	@Override
	public void activatePolicy(ActivatePolicyRequest request) {
		PolicyEntity policy = getPolicyEntity(request.getId());
		PolicyCoverageDTO firstCoverage = policyCoverageService.getFirstPolicyCoverage(policy);
		if (firstCoverage.getStatus() == PolicyStatus.NEW_BUSINESS_ENTRY.getStatus() || firstCoverage.getStatus() == PolicyStatus.PENDING.getStatus()) {
			try {

				wssinfpl.wssinfplcall(wssinfplImportMapper.asWssinfplImport(request));

				policy = getPolicyEntity(request.getId());
				PolicyCoverageDTO updatedCoverage = policyCoverageService.getFirstPolicyCoverage(policy);
				if (updatedCoverage == null || (updatedCoverage.getStatus() != PolicyStatus.IN_FORCE.getStatus() && updatedCoverage.getStatus() != PolicyStatus.PENDING.getStatus())) {
					throw new PolicyException("Coverage cannot be actived !! Please check missing elements (insureds, transaction, fund valuation, premium...)!");
				}

			} catch (org.tempuri.wssinfpl.Exception_Exception e) {
				throw new WebServiceInvocationException(e.getMessage(), e);
			}
		}
	}

	@Override
	public void updatePolicy(ActivatePolicyRequest request) {
		try {
			WssinfplExport export = callUpdatePolicyWs(request);
			String policy = export.getExpPolicyIdPolicies().getPolId();
			LOGGER.info("Policy: " + policy + "now in force!");
		} catch (org.tempuri.wssinfpl.Exception_Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}
	}

	private WssinfplExport callUpdatePolicyWs(ActivatePolicyRequest request) throws org.tempuri.wssinfpl.Exception_Exception {
		WssinfplImport wssinfplImport = wssinfplImportMapper.asWssinfplImport(request);
		WssinfplImport.ImpCallMethodCommunications comm = new WssinfplImport.ImpCallMethodCommunications();
		comm.setCallFunction(POLICY_UPDATE_CALL_FUNCTION);
		wssinfplImport.setImpCallMethodCommunications(comm);
		wssinfplImport.setExitState(WS_LISSIA_EXPECTED_CODE);
		WssinfplExport wsResponse = wssinfpl.wssinfplcall(wssinfplImport);
		if (wsResponse.getExitState() != WS_LISSIA_EXPECTED_CODE) {
			throw new PolicyException(wsResponse.getExpErrorMessageBrowserFields().getErrorMessage());
		}
		return wsResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#getPolicyIncompleteDetails(java.lang.String)
	 */
	@Override
	public List<String> getPolicyIncompleteDetails(String id) {
		Assert.isTrue(!StringUtils.isEmpty(id), "Policy id cannot empty");
		try {
			org.tempuri.wsspolicd.WsspolicdImport wsspolicdImport = new org.tempuri.wsspolicd.WsspolicdImport();

			org.tempuri.wsspolicd.WsspolicdImport.ImpPolPolicies impPolPolicies = new org.tempuri.wsspolicd.WsspolicdImport.ImpPolPolicies();
			impPolPolicies.setPolId(id);
			wsspolicdImport.setImpPolPolicies(impPolPolicies);
			WsspolicdImport.ImpValidationUsers impValidationUsers = new WsspolicdImport.ImpValidationUsers();
			impValidationUsers.setLoginId(wsLoginCredential);
			impValidationUsers.setPassword(wsPasswordCredential);
			wsspolicdImport.setImpValidationUsers(impValidationUsers);

			org.tempuri.wsspolicd.WsspolicdExport export = wsspolicd.wsspolicdcall(wsspolicdImport);

			return export.getExpGrpEvc().getRows().stream().map(r -> r.getExpItmEvcEventConditions().getName()).collect(Collectors.toList());
			// export.get
		} catch (org.tempuri.wsspolicd.Exception_Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}
	}

	@Override
	public Boolean createPolicyCashSuspense(CreatePolicyCashSuspenseRequest request) {
		validateInput(request, CANNOT_CREATE_POLICY_CASH_SUSPENSE_THE_POLICY_IS_EMPTY);

		LOGGER.info("createPolicyCashSuspense");

		// FYI : cash received is not linked to a coverage (coverage = 0)
		Collection<TransactionDTO> transactions = transactionService.getTransaction(request.getPolId(), request.getAmount(), request.getCurrency(), request.getEffectiveDate(),
				Arrays.asList(EventType.CASH_RECEIVED.getEvtId()));

		// We used to throw an exception here to avoid duplicate cash received transactions.
		// After we added the CFI and the recreate feature, we remarked that the CASH RECEIVED stays the same in Lissia.
		// Instead the webservice creates a cancellation transaction without any link to the original CASH RECEIVED.
		if (CollectionUtils.isNotEmpty(transactions)) {
			LOGGER.warn("There are more than one 'CASH RECEIVED' transactions (policy: " + request.getPolId() + ", amount: " + request.getAmount() + ", currency: "
					+ request.getCurrency() + ", effectiveDate: " + request.getEffectiveDate() + ".");
		}

		try {
			WsschsusImport wsschsusImport = wsschsusImportMapper.asWsschsusImport(request);
			wsschsus.wsschsuscall(wsschsusImport);

		} catch (org.tempuri.wsschsus.Exception_Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}

		return Boolean.TRUE;
	}

	private void validateInput(Object object, String message) {
		if (object == null || (object instanceof String && "".equals(object))) {
			throw new PolicyException(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#getPoliciesForHolder(java.lang.Integer)
	 */
	@Override
	@SuppressWarnings("boxing")
	public Set<PolicyDTO> getPoliciesForHolder(Integer polycHolder) {
		List<CliPolRelationshipEntity> cliPolRelationship = cliPolRelationshipRepository.findActiveByClientIdAndTypesAndDate(polycHolder, CliPolRelationshipType.DEFAULT_POLICY_HOLDER_ROLES_TYPES,
				new Date());

		Set<PolicyDTO> policies = new HashSet<>();
		cliPolRelationship.forEach(c -> CollectionUtils.addIgnoreNull(policies, policyMapper.asPolicyDTO(c.getPolicy())));

		return policies;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#getPoliciesByFund(java.lang.String)
	 */
	@Override
	public int countPolicies(String fdsId) {
		return policyRepository.countPoliciesByFund(fdsId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#getPolicy(java.lang.String)
	 */
	@Override
	public PolicyDTO getPolicy(String id) {
		Assert.notNull(id, POLICY_ID_CANNOT_BE_NULL);

		return policyMapper.asPolicyDTO(getPolicyEntity(id));
	}

	@Override
	public PolicyDTO getPolicy(PolicyClientRoleViewRequest request) {
		Assert.notNull(request);

		PolicyEntity policyEntity = getPolicyEntity(request.getPolicyId());

		return policyWithClientRoleActivationMapper.asPolicyDTOWithClientRoleActivation(policyEntity, request.getClientRoleActivationFlagDTO());
	}

	@Override
	public PolicyEntity getPolicyEntity(String id) {
		Assert.notNull(id, POLICY_ID_CANNOT_BE_NULL);
		String policyId = org.apache.commons.lang.StringUtils.rightPad(id, 14, ' ');
		PolicyEntity policy = policyRepository.findOne(policyId);

		if (policy == null) {
			throw new PolicyException("The policy (" + id + ") does not exist in POLICIES table.");
		}
		return policy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#getPolicyLight(java.lang.String)
	 */
	@Override
	public PolicyLightDTO getPolicyLight(String id) {
		Assert.notNull(id, POLICY_ID_CANNOT_BE_NULL);

		return policyMapper.asPolicyLightDTO(getPolicyEntity(id));
	}

	@Override
	public PolicyLightDTO getPolicyLight(Long workflowItemId, String userId) {
		Assert.notNull(workflowItemId, WORKFLOW_ID_CANNOT_BE_NULL);

		return getPolicyIdByWorkflowId(workflowItemId, userId)
				.map(this::getPolicyLight)
				.orElseThrow(() -> new PolicyException(String.format("Policy associated to workflow id %s not found", workflowItemId)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#isExist(java.lang.String)
	 */
	@Override
	public boolean isExist(String id) {
		Assert.notNull(id, POLICY_ID_CANNOT_BE_NULL);
		return policyRepository.findOne(id) != null;
	}

	@Override
	public boolean isExistByWorkflowItemId(Long workflowItemId, String usrId) {

		Assert.notNull(workflowItemId);
		WorkflowItemDataDTO workflowItem = workflowItemService.getWorkflowItem(workflowItemId, usrId);

		MetadataDTO policyNumberMetadata = workflowItem.getMetadata().stream().filter(x -> POLICY.equals(x.getName())).findFirst().orElse(null);

		if (policyNumberMetadata == null || !StringUtils.isNotBlank(policyNumberMetadata.getValue())) {
			return false;
		}

		String policyId = org.apache.commons.lang.StringUtils.rightPad(policyNumberMetadata.getValue(), 14, ' ');
		return policyRepository.exists(policyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#getRolesByPolicies(java.lang.Integer, java.util.List)
	 */
	@Override
	public RolesByPoliciesDTO getRolesByPolicies(Integer clientId, List<CliPolRelationshipType> cliPolRelationshipTypes) {
		RolesByPoliciesDTO rolesByPoliciesDTO = new RolesByPoliciesDTO();
		rolesByPoliciesDTO.setCliId(clientId);
		rolesByPoliciesDTO.setPortfolio(policyHolderService.getPortfolioAmount(clientId, new Date(), EUR));

		Map<String, Set<Integer>> rolesByPolicies = getRolesByPoliciesMap(clientId, cliPolRelationshipTypes);
		List<RolesByPolicyDTO> rolesByPolicy = new ArrayList<>();
		rolesByPolicies.forEach((polId, roles) -> {
			RolesByPolicyDTO rolesByPolicyDTO = new RolesByPolicyDTO();
			rolesByPolicyDTO.setPolicy(getPolicyLight(polId));

			Collection<OptionDetailDTO> optionDetails = optionDetailService.getOptionDetails(optionDetailService.getClientPolicyRelationTypes(), roles);

			rolesByPolicyDTO.setRoles(sortOptionDetails(optionDetails));
			rolesByPolicy.add(rolesByPolicyDTO);
		});

		sortRolesByPolicy(rolesByPolicy);
		rolesByPoliciesDTO.setRolesByPolicy(rolesByPolicy);

		return rolesByPoliciesDTO;
	}

	@Override
	public SearchResult<PolicyLightDTO> getBrokerPolicies(PolicySearchRequest request) {

		Page<PolicyEntity> pageResult = searchPolicyEntity(request);

		List<PolicyLightDTO> content = policyMapper.asPolicyLightDTOs(pageResult.getContent());

		SearchResult<PolicyLightDTO> r = new SearchResult<PolicyLightDTO>();

		r.setPageSize(pageResult.getSize());
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.hasContent() ? pageResult.getNumber() + 1 : 1);
		r.setContent(content);

		return r;
	}
	
	@Override
	public List<String> getAgentPolicies(String agtId) {
		return policyRepository.policiesByAgent(agtId);
	}

	@Override
	public PolicyValuationDTO getBrokerValuation(String agtId, String currency) {
		PolicyValuationDTO brokerValuation = policyValuationService.getBrokerPolicyValuation(agtId, currency);
		return brokerValuation;
	}

	private void sortRolesByPolicy(List<RolesByPolicyDTO> rolesByPolicy) {
		rolesByPolicy.sort((r1, r2) -> {
			Date dateOfCommencent1 = r1.getPolicy().getDateOfCommencement();
			if (dateOfCommencent1 == null) {
				return 0;
			}
			Date dateOfCommencent2 = r2.getPolicy().getDateOfCommencement();
			if (dateOfCommencent2 == null) {
				return 1;
			}
			return dateOfCommencent1.compareTo(dateOfCommencent2);
		});
	}

	private List<OptionDetailDTO> sortOptionDetails(Collection<OptionDetailDTO> optionDetails) {
		List<OptionDetailDTO> sortedOptionDetails = new ArrayList<OptionDetailDTO>(optionDetails);

		sortedOptionDetails.sort((OptionDetailDTO o1, OptionDetailDTO o2) -> Integer.valueOf(o1.getNumber()).compareTo(Integer.valueOf(o2.getNumber())));

		return sortedOptionDetails;
	}

	@SuppressWarnings("boxing")
	private Map<String, Set<Integer>> getRolesByPoliciesMap(Integer clientId, List<CliPolRelationshipType> cliPolRelationshipTypes) {
		Assert.notNull(clientId, CLIENT_ID_CANNOT_BE_NULL);
		Assert.notNull(cliPolRelationshipTypes, CLI_POL_RELATIONSHIP_TYPES_ID_CANNOT_BE_NULL);

		List<CliPolRelationshipEntity> cliPolRelationships = cliPolRelationshipRepository.findActiveByClientIdAndDate(clientId, new Date());

		Map<String, Set<Integer>> policiesByRole = new HashMap<>();
		for (CliPolRelationshipEntity cliPolRelationship : cliPolRelationships) {
			CliPolRelationshipType cliPolRelationshipType = CliPolRelationshipType.toCliPolRelationshipType(cliPolRelationship.getType());

			if (!cliPolRelationshipTypes.contains(cliPolRelationshipType)) {
				continue;
			}

			String policyId = cliPolRelationship.getPolicyId().trim();
			if (StringUtils.isEmpty(policyId)) {
				continue;
			}

			Set<Integer> roles = policiesByRole.get(policyId);
			if (roles == null) {
				roles = new HashSet<>();
				policiesByRole.put(policyId, roles);
			}

			roles.add(cliPolRelationshipType.getValue());
		}

		return policiesByRole;
	}

	@Override
	public String getNewNumber(String product) {

		org.tempuri.wssrspol.WssrspolImport request = new org.tempuri.wssrspol.WssrspolImport();

		org.tempuri.wssrspol.WssrspolImport.ImpValidationUsers user = new org.tempuri.wssrspol.WssrspolImport.ImpValidationUsers();
		user.setLoginId(wsLoginCredential);
		user.setPassword(wsPasswordCredential);

		request.setImpValidationUsers(user);

		org.tempuri.wssrspol.WssrspolImport.ImpPolPolicies prod = new org.tempuri.wssrspol.WssrspolImport.ImpPolPolicies();
		prod.setProduct(product);
		request.setImpPolPolicies(prod);

		try {
			WssrspolExport response = WSSRSPOL.wssrspolcall(request);

			// business error
			if (response.getExpErrorMessageBrowserFields() != null
					&& StringUtils.isNotBlank(response.getExpErrorMessageBrowserFields().getErrorTxt())) {

				throw new WebServiceInvocationException(response.getExpErrorMessageBrowserFields().getErrorTxt());
			}
			if (response.getExpPolPolicies() != null
					&& StringUtils.isNotBlank(response.getExpPolPolicies().getPolId())) {

				return response.getExpPolPolicies().getPolId().trim();

			}

			throw new WebServiceInvocationException("WSSRSPOL didn't generate a policy id.");
		} catch (org.tempuri.wssrspol.Exception_Exception e) {
			// network error
			throw new WebServiceInvocationException(e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#searchPolicy(java.lang.String, java.lang.String,
	 * java.lang.String,java.lang.String,java.lang.Boolean,java.lang.Boolean,java.lang.Boolean java.lang.Integer, int, int)
	 */
	@Override
	public SearchResult<PolicyDTO> searchPolicy(PolicySearchRequest request) {

		Page<PolicyEntity> pageResult = searchPolicyEntity(request);

		SearchResult<PolicyDTO> r = new SearchResult<PolicyDTO>();

		r.setPageSize(pageResult.getSize());
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.hasContent() ? pageResult.getNumber() + 1 : 1);
		r.setContent(policyMapper.asPolicyDTOs(pageResult.getContent()));

		return r;
	}

	private Page<PolicyEntity> searchPolicyEntity(PolicySearchRequest request) {

		int page = request.getPageNum() == null
				|| request.getPageNum().intValue() < 1
						? 0 : request.getPageNum().intValue() - 1;

		int size = request.getPageSize() == null
				|| request.getPageSize().intValue() <= 1
				|| request.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE
						? SearchResult.DEFAULT_PAGE_SIZE : request.getPageSize().intValue();

		String polId = request.getPolId();
		String applicationForm = request.getApplicationForm();
		String brokerRefContract = request.getBrokerRefContract();
		String brokerId = request.getBrokerId();
		String fundId = request.getFundId();
		Boolean withActive = request.getWithActive();
		Boolean withPending = request.getWithPending();
		Boolean withInactive = request.getWithInactive();

		Pageable pagable = new PageRequest(page, size, Sort.Direction.DESC, "polId"); // dateOfCommencement

		Page<PolicyEntity> pageResult = new PageImpl<>(new ArrayList<PolicyEntity>());

		Specifications<PolicyEntity> specifs = Specifications.where(PolicySpecifications.initial());
		if (StringUtils.isNotBlank(polId)) {
			specifs = specifs.and(PolicySpecifications.withId(polId));
		}
		if (StringUtils.isNotBlank(applicationForm)) {
			specifs = specifs.and(PolicySpecifications.withAdditionalId(applicationForm));
		}
		if (StringUtils.isNotBlank(brokerRefContract)) {
			specifs = specifs.and(PolicySpecifications.withBrokerRefContract(brokerRefContract));
		}
		if (StringUtils.isNotBlank(brokerId)) {
			specifs = specifs.and(PolicySpecifications.withBrokerId(brokerId));
		}
		if (StringUtils.isNotBlank(fundId)) {
			specifs = specifs.and(PolicySpecifications.withFundId(fundId));
		}

		if (!(BooleanUtils.isNotTrue(withActive) && BooleanUtils.isNotTrue(withPending) && BooleanUtils.isNotTrue(withInactive))
				&& !(withActive && withPending && withInactive)) {

			Specifications<PolicyEntity> subSpecifs = Specifications.where(PolicySpecifications.withStatus((short) -1));
			if (withActive) {
				subSpecifs = subSpecifs.or(PolicySpecifications.withActive());
			}
			if (withPending) {
				subSpecifs = subSpecifs.or(PolicySpecifications.withPending());
			}
			if (withInactive) {
				subSpecifs = subSpecifs.or(PolicySpecifications.withTerminated());
			}
			specifs = specifs.and(subSpecifs);
		}

		specifs = specifs.and(PolicySpecifications.withNotEmptyProduct());
		specifs = specifs.and(PolicySpecifications.withoutStatus((short) 0));

		pageResult = policyRepository.findAll(specifs, pagable);
		return pageResult;
	}

	@Override
	public PolicyActiveStatus getPolicyActiveStatus(Integer status, Integer subStatus) {
		OptionDetailDTO policyStatus = optionDetailService.getOptionDetail(optionDetailService.getPolicyStatuses(status), subStatus);

		if (policyStatus == null) {
			return null;
		}

		PolicyActiveStatus policyActiveStatus = PolicyActiveStatus.getStatus(policyStatus);

		if (policyActiveStatus != null) {
			return policyActiveStatus;
		}

		return PolicyActiveStatus.getStatus(status);
	}

	@Override
	public SearchResult<PolicyDTO> getPoliciesForMathematicalReserve(PoliciesForMathematicalReserveRequest request) {
		Pageable page = new PageRequest(request.getPageNum(), request.getPageSize());
		Page<PolicyEntity> policies = policyRepository.findPoliciesForMathematicalReserveFunctionOfDate(request.getDate(), page);

		List<PolicyDTO> policiesMapped = new ArrayList<PolicyDTO>();
		// don't want the whole policy object
		for (PolicyEntity pe : policies.getContent()) {
			PolicyDTO policy = new PolicyDTO();
			policy.setPolId(pe.getPolId());
			if (pe.getProduct() != null && pe.getProduct().getPrdId() != null && !pe.getProduct().getPrdId().trim().equals(""))
				policy.setProduct(productMapper.asProductDTO(pe.getProduct()));
			else
				LOGGER.error("error on the product with policy " + pe.getPolId());

			policiesMapped.add(policy);
		}
		SearchResult<PolicyDTO> results = new SearchResult<PolicyDTO>();
		results.setContent(policiesMapped);
		results.setCurrentPage(request.getPageNum());
		results.setPageSize(policiesMapped.size());

		return results;
	}

	/**
	 * This method save the policy score and return if the policy has been modified.<br>
	 * 
	 * @return <code>true</code> if the policy has been modified, <code>false</code> otherwise.
	 */
	@Override
	@Transactional
	public Boolean saveScoreNewBusiness(String policyId, Integer scoreValue) {

		Assert.notNull(policyId);

		PolicyEntity policy = getPolicyEntity(policyId);
		policy.setScoreNewBusiness(scoreValue);

		save(policy);

		return Boolean.TRUE;
	}

	@Override
	@Transactional
	public Boolean saveScoreLastTrans(String policyId, Integer scoreValue) {

		Assert.notNull(policyId);

		PolicyEntity policy = getPolicyEntity(policyId);
		policy.setScoreLastTrans(scoreValue);

		save(policy);

		return Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.PolicyService#getPolicy(java.lang.Long, java.lang.String)
	 */
	@Override
	public PolicyDTO getPolicy(Long workflowItemId, String usrId) {
		return getPolicyIdByWorkflowId(workflowItemId, usrId).map(this::getPolicy).orElse(null);
	}

	private Optional<String> getPolicyIdByWorkflowId(Long workflowItemId, String usrId) {
		if (workflowItemId == null) {
			return Optional.empty();
		}

		MetadataDTO policyNumberMetadata = getPolicyIdMetadata(workflowItemId, usrId);

		if (policyNumberMetadata == null) {
			return Optional.empty();
		}

		return Optional.of(policyNumberMetadata.getValue());
	}

	private MetadataDTO getPolicyIdMetadata(Long workflowItemId, String usrId) {
		WorkflowItemDataDTO workflowItem = workflowItemService.getWorkflowItem(workflowItemId, usrId);

		if (workflowItem == null) {
			return null;
		}

		return workflowItem.getMetadata().stream().filter(x -> POLICY.equals(x.getName())).findFirst().orElse(null);
	}

	@Override
	public void updateForUpdateInputStep(UpdateInputRequest request, String user) {

		PolicyEntity policyEntity = getPolicyEntity(request.getPolicyId());

		Boolean brokerPartnerAuthorized = Boolean.valueOf(request.getBrokerPartnerAuthorized());

		for (PolicyAgentShareEntity pas : policyAgentShareRepository.findByPolIdAndType(request.getPolicyId(), PolicyAgentShareType.ADVISOR_FEES.getType())) {
			pas.setPartnerAuthorized(brokerPartnerAuthorized);
			policyAgentShareRepository.saveAndFlush(pas);
		}

		policyEntity.setBrokerRefContract(request.getBrokerRefContract());
		String sendingRules = request.getSendingRules();
		if (StringUtils.isNotBlank(sendingRules)) {
			policyEntity.setCategory(categoryRepository.findOne(sendingRules));
		}
		policyEntity.setMailToAgent(request.getMailToAgent());
		policyEntity.setOrderByFax(request.getOrderByFax());
		policyEntity.setNoCooloff(request.getNoCoolOff());
		policyEntity.setExPaymentTransfer(request.getPaymentTransfer());
		policyEntity.setExMandate(request.getMandate());

		save(policyEntity);
	}

	@Override
	public PolicyEntity save(PolicyEntity policyEntity) {
		Assert.notNull(policyEntity);

		boolean needHistorization = historicManager.historize(policyEntity);
		if (needHistorization) {
			return policyRepository.saveAndFlush(policyEntity);
		}

		return policyEntity;
	}

	@Override
	public Boolean abort(String policyId) {

		Assert.notNull(policyId);

		PolicyEntity policy = policyRepository.findOne(policyId);

		if (policy == null) {
			LOGGER.info("Unexisting policy {}, No abort needed.", policyId);
			return false;
		}

		abortCoverage(policyId, policy.getDateOfCommencement(), null);

		return true;
	}

	private void abortCoverage(String policyId, Date changeDate, Integer coverage) {
		WsspscupImport request = policyWsRequestHelper.createAbortRequest(policyId, changeDate, coverage);

		try {
			LOGGER.info("Calling the soap service to abort the policy {} and coverage {}", policyId, coverage);
			callWsscscup(request);
		} catch (Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}
	}

	private void callWsscscup(WsspscupImport request) throws Exception_Exception {

		WsspscupExport response = wsspscup.wsspscupcall(request);

		if (response.getExitState() != WS_LISSIA_EXPECTED_CODE) {
			throw new CfiException(response.getExpErrormessageBrowserFields().getErrorMessage());
		}
	}

	@Override
	public PolicyRecreateResponse recreate(Long workflowItemId, String usrId) {
		Assert.notNull(workflowItemId);
		Assert.notNull(usrId);

		MetadataDTO policyNumberMetadata = getPolicyIdMetadata(workflowItemId, usrId);

		String newPolicyId = createNewPolicyId(policyNumberMetadata.getValue());

		PolicyRecreateResponse response = new PolicyRecreateResponse();
		response.setPolicyId(newPolicyId);

		return response;
	}

	private String createNewPolicyId(String policyId) {
		String rootName = policyIdHelper.getPolicyRootId(policyId);

		List<String> allVersionsOfIds = policyRepository.findAllVersionOfPolicyId(rootName);

		return policyIdHelper.createNextId(allVersionsOfIds, rootName);
	}

	@Override
	public List<String> getPoliciesByFund(String fdsId) {
		return policyRepository.policiesByFund(fdsId);
	}

	@Override
	public String getBrokerRefContract(String polId) {
		Assert.notNull(polId, POLICY_ID_CANNOT_BE_NULL);
		return policyRepository.findBrokerRefContractWithPolicyId(polId);
	}

	@Override
	public String getPolicyCurrency(String polId) {
		Assert.notNull(polId, POLICY_ID_CANNOT_BE_NULL);
		return policyRepository.findPolicyCurrency(polId);
	}

	@Override
	public Boolean abortCoverage(String policyId, Integer coverage) {
		Assert.notNull(policyId);
		Assert.notNull(coverage);

		PolicyCoverageDTO policyCoverage = policyCoverageService.getCoverage(policyId, coverage);

		abortCoverage(policyId, policyCoverage.getDateCommenced(), coverage);

		return true;
	}

	@Override
	public Boolean createWithdrawal(WithdrawalInputDTO withdrawalInput) {
		Assert.notNull(withdrawalInput, "The withdrawal input can't be null");

		WsspfhwdImport input = wsspfhwdImportMapper.asWsspfhwdImport(withdrawalInput);
		try {
			callWithdrawalWs(input);
		} catch (org.tempuri.wsspfhwd.Exception_Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}
		return true;
	}

	private void callWithdrawalWs(WsspfhwdImport input) throws org.tempuri.wsspfhwd.Exception_Exception {
		WsspfhwdExport response = wsspfhwd.wsspfhwdcall(input);
		if (response.getExitState() != WS_LISSIA_EXPECTED_CODE) {
			String errorMessage = response.getExpErrormessageBrowserFields().getErrorMessage();
			throw new CfiException(String.format("The lissia webservice raised the error : %s", errorMessage));
		}
	}

	@Override
	public Boolean createWithdrawal(List<WithdrawalInputDTO> withdrawalInputs) {
		Boolean result = false;
		for (WithdrawalInputDTO input : withdrawalInputs) {
			result = createWithdrawal(input);
		}
		return result;
	}

	@Override
	public List<String> searchPolicyIds(PolicyIdsSearchDTO policyIdsSearchDTO) {
		Assert.notNull(policyIdsSearchDTO, "the search dto can't be null");
		Assert.hasText(policyIdsSearchDTO.getQuery(), "the query can't be empty");

		return policyRepository.findIdsByPolicyIdLike(policyIdsSearchDTO.getQuery(), new PageRequest(0, 10, Direction.ASC, "polId"));
	}

	@Override
	public Boolean surrenderPolicy(PolicySurrenderDTO policySurrenderDTO) {
		validateSurrenderDTO(policySurrenderDTO);

		String policyId = policySurrenderDTO.getPolicyId();
		PolicyLightDTO policyLight = getPolicyLight(policyId);
		String surrenderFeesAgentId = getBroker(policyLight);
		Date effectiveDate = policySurrenderDTO.getEffectiveDate();
		Integer coverage = policyLight.getFirstCoverage().getCoverage();
		BigDecimal brokerTransactionFees = policySurrenderDTO.getBrokerTransactionFees();
		BigDecimal transactionFees = policySurrenderDTO.getTransactionFees();
		
		if (surrenderFeesAgentId != null) {
			policyAgentShareService.createOrUpdateAgentShare(surrenderFeesAgentId, PolicyAgentShareType.SURRENDER_FEE, policyId, coverage, brokerTransactionFees);
		}
		productValueService.createOrUpdateProductValue(ControlDefinitionType.SURRENDER_FEE.getValue(), policyId, coverage, transactionFees);

		WsspscupImport request = policyWsRequestHelper.createSurrenderRequest(policyId, effectiveDate);

		try {
			LOGGER.info("Calling the soap service to surrender the policy {}", policyId);
			callWsscscup(request);
		} catch (Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}

		fundTransactionService.postSurrenderAwaitingFundTransactions(policyId);

		return true;
	}

	private String getBroker(PolicyLightDTO policyLight) {
		if (policyLight.getBroker() == null || policyLight.getBroker().getAgent() == null) {
			return null;
		}
		return policyLight.getBroker().getAgent().getAgtId();
	}

	private void validateSurrenderDTO(PolicySurrenderDTO policySurrenderDTO) {
		Assert.notNull(policySurrenderDTO, POLICY_SURRENDER_DTO_NOT_NULL);
		Assert.notNull(policySurrenderDTO.getPolicyId(), POLICY_ID_CANNOT_BE_NULL);
		Assert.notNull(policySurrenderDTO.getEffectiveDate(), SURRENDER_DATE_NOT_NULL);
		Assert.notNull(policySurrenderDTO.getTransactionFees(), SURRENDER_FEES_NOT_NULL);
		Assert.notNull(policySurrenderDTO.getBrokerTransactionFees(), SURRENDER_BROKER_FEES_NOT_NULL);
	}

}
