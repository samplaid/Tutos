package lu.wealins.liability.services.core.business.impl;

import static lu.wealins.liability.services.core.business.impl.PolicyClausesServiceImpl.DEATH_CLAUSE_TYPE;
import static lu.wealins.liability.services.core.business.impl.PolicyClausesServiceImpl.LIFE_CLAUSE_TYPE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.ApplyBeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.ClientRoleActivationFlagDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;
import lu.wealins.common.dto.liability.services.UpdateBeneficiaryChangeRequest;
import lu.wealins.common.dto.liability.services.enums.ClauseType;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.common.dto.liability.services.enums.CountryCodeEnum;
import lu.wealins.common.dto.liability.services.enums.DeathCoverageLives;
import lu.wealins.common.dto.liability.services.enums.PolicyBeneficiaryClauseStatus;
import lu.wealins.liability.services.core.business.BeneficiaryChangeService;
import lu.wealins.liability.services.core.business.BeneficiaryService;
import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.OtherClientService;
import lu.wealins.liability.services.core.business.PolicyClausesService;
import lu.wealins.liability.services.core.business.PolicyCoverageService;
import lu.wealins.liability.services.core.business.PolicyHolderService;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyBeneficiaryClauseEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;
import lu.wealins.liability.services.core.persistence.repository.CliPolRelationshipRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyBeneficiaryClausesRepository;
@Service
public class BeneficiaryChangeServiceImpl implements BeneficiaryChangeService {

	private static final String CHANGE_BENEF_AVAILABLE_SUB_ROLES = "CHANGE_BENEF_AVAILABLE_SUB_ROLES";

	@Autowired
	private BeneficiaryService beneficiaryService;

	@Autowired
	private OtherClientService otherClientService;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private PolicyClausesService policyClausesService;

	@Autowired
	private CliPolRelationshipRepository cliPolRelationshipRepository;
	
	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;

	@Autowired
	private PolicyHolderService policyHolderService;
	
	@Autowired
	private PolicyCoverageService policyCoverageService;

	@Autowired
	private PolicyBeneficiaryClausesRepository policyBeneficiaryClausesRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.BeneficiaryChangeService#getBeneficiaryChange(lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest)
	 */
	@Override
	public BeneficiaryChangeDTO getBeneficiaryChange(BeneficiaryChangeRequest beneficiaryChangeRequest) {
		Assert.notNull(beneficiaryChangeRequest);

		String workflowItemId = beneficiaryChangeRequest.getWorkflowItemId() + "";
		ClientRoleActivationFlagDTO clientRoleActivationFlag = beneficiaryChangeRequest.getClientRoleActivationFlagDTO();
		String availableSubRolesStr = beneficiaryChangeRequest.getApplicationParams().get(CHANGE_BENEF_AVAILABLE_SUB_ROLES);

		BeneficiaryChangeDTO beneficiaryChange = new BeneficiaryChangeDTO();

		beneficiaryChange.setLifeBeneficiaries(beneficiaryService.getLifeBeneficiaries(workflowItemId, clientRoleActivationFlag));
		beneficiaryChange.setDeathBeneficiaries(beneficiaryService.getDeathBeneficiaries(workflowItemId, clientRoleActivationFlag));
		beneficiaryChange.setOtherClients(filterOtherClients(otherClientService.getOtherClients(workflowItemId, clientRoleActivationFlag), availableSubRolesStr));
		beneficiaryChange.setPolicyHolders(policyHolderService.getPolicyHolders(workflowItemId));
		beneficiaryChange.setPolicyId(beneficiaryChangeRequest.getPolicyId());
		PolicyClausesDTO clauses = policyClausesService.getClausesWithWorkflowItemId(workflowItemId);
		beneficiaryChange.setDeathBenefClauses(filterAndSortClauses(clauses.getDeath()));
		beneficiaryChange.setLifeBenefClauses(filterAndSortClauses(clauses.getMaturity()));
		beneficiaryChange.setDeathNominativeClauses(policyClausesService.createNominativeDeathClauses(workflowItemId));
		beneficiaryChange.setLifeNominativeClauses(policyClausesService.createNominativeLifeClauses(workflowItemId));

		return beneficiaryChange;
	}

	@Override
	public BeneficiaryChangeDTO updateBeneficiaryChange(UpdateBeneficiaryChangeRequest updateBeneficiaryChangeRequest) {
		BeneficiaryChangeDTO beneficiaryChange = updateBeneficiaryChangeRequest.getBeneficiaryChange();
		String workflowItemId = beneficiaryChange.getWorkflowItemId() + "";
		String policyId = beneficiaryChange.getPolicyId();

		cliPolRelationshipService.deleteDisabledWithModifyProcess(workflowItemId);

		beneficiaryService.updateDeathBeneficiaries(beneficiaryChange.getDeathBeneficiaries(), policyId, beneficiaryChange.getChangeDate(),
				workflowItemId);
		beneficiaryService.updateLifeBeneficiaries(beneficiaryChange.getLifeBeneficiaries(), policyId, beneficiaryChange.getChangeDate(),
				workflowItemId);
		policyHolderService.updatePolicyHolders(beneficiaryChange.getPolicyHolders(), policyId, beneficiaryChange.getChangeDate(), workflowItemId);

		// other client must be setup after other service. If a beneficiary is acceptant and the acceptant is setup in the other client relation.
		List<CliPolRelationshipEntity> otherCliPolRelationships = cliPolRelationshipService.getOtherCliPolRelationships(workflowItemId,
				updateBeneficiaryChangeRequest.getClientRoleActivationFlagDTO());

		// Delete relation saved with sub-roles on Beneficiary/PolicyHolder which are duplicated with the other relations.
		cliPolRelationshipRepository.delete(otherCliPolRelationships);

		otherClientService.updateOtherClients(beneficiaryChange.getOtherClients(), policyId, beneficiaryChange.getChangeDate(), workflowItemId);

		updatePolicyBeneficiaryClauses(beneficiaryChange);

		return beneficiaryChange;
	}

	private void updatePolicyBeneficiaryClauses(BeneficiaryChangeDTO beneficiaryChange) {

		// We only update the inactive clauses because if we update the active clauses that means those ones will be not linked anymore on the policy
		List<PolicyBeneficiaryClauseDTO> inactiveDeathBenefClauses = beneficiaryChange.getDeathBenefClauses().stream()
				.filter(x -> x.getStatus() != null && x.getStatus().intValue() == PolicyBeneficiaryClauseStatus.INACTIVE.getValue()).collect(Collectors.toList());
		List<PolicyBeneficiaryClauseDTO> inactiveLifeBenefClauses = beneficiaryChange.getLifeBenefClauses().stream()
				.filter(x -> x.getStatus() != null && x.getStatus().intValue() == PolicyBeneficiaryClauseStatus.INACTIVE.getValue()).collect(Collectors.toList());

		policyClausesService.updatePolicyBeneficiaryClauses(inactiveDeathBenefClauses, inactiveLifeBenefClauses, beneficiaryChange.getPolicyId(),
				beneficiaryChange.getWorkflowItemId() + "");
	}

	@Override
	public BeneficiaryChangeDTO applyBeneficiaryChange(ApplyBeneficiaryChangeRequest applyBeneficiaryChangeRequest) {
		BeneficiaryChangeDTO beneficiaryChange = applyBeneficiaryChangeRequest.getBeneficiaryChange();
		String policyId = beneficiaryChange.getPolicyId();
		Assert.notNull(policyId);
		PolicyEntity policyEntity = policyService.getPolicyEntity(policyId);
		Assert.notNull(policyEntity, "Policy " + policyId + " does not exist.");

		applyChangeOnCliPolRelationships(applyBeneficiaryChangeRequest);
		applyChangeOnPolicyBeneficiaryClauses(beneficiaryChange, policyId);

		return beneficiaryChange;
	}

	private void applyChangeOnPolicyBeneficiaryClauses(final BeneficiaryChangeDTO beneficiaryChange, String policyId) {
		// find the policy's text clauses
		List<PolicyBeneficiaryClauseEntity> clauses = policyBeneficiaryClausesRepository.findAllByPolicyAndTypeIgnoreCaseAndStatusOrderByRankAsc(policyId, LIFE_CLAUSE_TYPE, 1);
		clauses.addAll(policyBeneficiaryClausesRepository.findAllByPolicyAndTypeIgnoreCaseAndStatusOrderByRankAsc(policyId, DEATH_CLAUSE_TYPE, 1));

		// Disable old ones.
		clauses.forEach(x -> x.setStatus(Integer.valueOf(PolicyBeneficiaryClauseStatus.INACTIVE.getValue())));

		// Enable new ones.
		updateBeneficiaryClauses(beneficiaryChange.getDeathBenefClauses(), policyId);
		updateBeneficiaryClauses(beneficiaryChange.getLifeBenefClauses(), policyId);

		policyBeneficiaryClausesRepository.save(clauses);
		beneficiaryChange.getDeathBenefClauses().forEach(x -> policyClausesService.save(x, beneficiaryChange.getWorkflowItemId() + ""));
		beneficiaryChange.getLifeBenefClauses().forEach(x -> policyClausesService.save(x, beneficiaryChange.getWorkflowItemId() + ""));
	}

	private void updateBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> clauses, String policyId) {
		clauses.forEach(x -> {
			x.setStatus(Integer.valueOf(PolicyBeneficiaryClauseStatus.ACTIVE.getValue()));
			x.setFkPoliciespolId(policyId);
			x.setPolicy(policyId);
		});
	}

	@SuppressWarnings("boxing")
	private void applyChangeOnCliPolRelationships(ApplyBeneficiaryChangeRequest applyBeneficiaryChangeRequest) {
		BeneficiaryChangeDTO beneficiaryChange = applyBeneficiaryChangeRequest.getBeneficiaryChange();
		String policyId = beneficiaryChange.getPolicyId();

		String availableSubRolesStr = applyBeneficiaryChangeRequest.getApplicationParams().get(CHANGE_BENEF_AVAILABLE_SUB_ROLES);
		List<CliPolRelationshipType> availableSubRoles = Arrays.asList(availableSubRolesStr.split(",")).stream().map(x -> CliPolRelationshipType.toCliPolRelationshipType(Integer.valueOf(x)))
				.collect(Collectors.toList());
		availableSubRoles.addAll(CliPolRelationshipType.BENEFICIARY_PRINCIPAL_RELATIONSHIP_TYPE_GROUP);

		cliPolRelationshipService.disableCliPolRelationship(policyId, availableSubRoles, beneficiaryChange.getChangeDate());

		List<CliPolRelationshipType> excludedSubRoles = CliPolRelationshipType.POLICY_HOLDER_PRINCIPAL_RELATIONSHIP_TYPE_GROUP;
		cliPolRelationshipService.enableCliPolRelationships(beneficiaryChange.getWorkflowItemId(), excludedSubRoles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.BeneficiaryChangeService#initBeneficiaryChange(lu.wealins.common.dto.liability.services.BeneficiaryChangeRequest)
	 */
	@Override
	public BeneficiaryChangeDTO initBeneficiaryChange(BeneficiaryChangeRequest beneficiaryChangeRequest) {
		Assert.notNull(beneficiaryChangeRequest);
		Assert.notNull(beneficiaryChangeRequest.getPolicyId());
		Assert.notNull(beneficiaryChangeRequest.getWorkflowItemId());
		PolicyEntity policy = policyService.getPolicyEntity(beneficiaryChangeRequest.getPolicyId());
		BeneficiaryChangeDTO beneficiaryChange = new BeneficiaryChangeDTO();
		String availableSubRolesStr = beneficiaryChangeRequest.getApplicationParams().get(CHANGE_BENEF_AVAILABLE_SUB_ROLES);

		beneficiaryChange.setLifeBeneficiaries(beneficiaryService.getLifeBeneficiaries(policy, beneficiaryChangeRequest.getClientRoleActivationFlagDTO()));
		beneficiaryChange.setDeathBeneficiaries(beneficiaryService.getDeathBeneficiaries(policy, beneficiaryChangeRequest.getClientRoleActivationFlagDTO()));
		beneficiaryChange.setPolicyHolders(policyHolderService.getPolicyHolders(policy));
		beneficiaryChange.setOtherClients(otherClientService.getOtherClients(policy, beneficiaryChangeRequest.getClientRoleActivationFlagDTO()));
		beneficiaryChange.setOtherClients(filterOtherClients(otherClientService.getOtherClients(policy, beneficiaryChangeRequest.getClientRoleActivationFlagDTO()), availableSubRolesStr));
		beneficiaryChange.setChangeDate(null);
		beneficiaryChange.setPolicyId(beneficiaryChangeRequest.getPolicyId());
		PolicyClausesDTO clauses = initClauses(beneficiaryChangeRequest);
		beneficiaryChange.setDeathBenefClauses(filterAndSortClauses(clauses.getDeath()));
		beneficiaryChange.setLifeBenefClauses(filterAndSortClauses(clauses.getMaturity()));
		if (beneficiaryChangeRequest.getWorkflowItemId() != null) {
			beneficiaryChange.setWorkflowItemId(beneficiaryChangeRequest.getWorkflowItemId());
		}
		
		// check business rule regarding the nordic countries		
		if(policy != null && policy.getProduct() != null && (CountryCodeEnum.FINLAND.getCode().equals(policy.getProduct().getNlCountry()) || CountryCodeEnum.SWEDEN.getCode().equals(policy.getProduct().getNlCountry()))) {
			
			int policyHoldersCount = policyHolderService.getPolicyHolders(policy).size();
			PolicyCoverageDTO policyCoverageDTO = policyCoverageService.getFirstPolicyCoverage(policy);
			String nordicCountriesRuleText = beneficiaryChangeRequest.getApplicationParams().get("RULES_2_POLICYHOLDERS_NORD");
			
			if(policyHoldersCount > 1 && policyCoverageDTO.getLives() != null && policyCoverageDTO.getLives().getNumber() == DeathCoverageLives.JOINT_SECOND_DEATH.getCode().intValue()) {
				
				if(!beneficiaryChange.getDeathBenefClauses().stream().anyMatch(x -> "Death".equals(x.getType()) && "F".equals(x.getTypeOfClause()) && nordicCountriesRuleText.equals(x.getTextOfClause()))) {
					
					PolicyBeneficiaryClauseDTO newClause = new PolicyBeneficiaryClauseDTO();
					newClause.setFkPoliciespolId(policy.getPolId());
					newClause.setPolicy(policy.getPolId());
					newClause.setType("Death");
					newClause.setTypeOfClause("F");
					newClause.setTextOfClause(nordicCountriesRuleText);
					newClause.setStatus(Integer.valueOf(1));
					newClause.setRank(Integer.valueOf(1));					
					beneficiaryChange.getDeathBenefClauses().add(newClause);
				}				
			}
		}

		return beneficiaryChange;
	}

	private Collection<OtherClientDTO> filterOtherClients(Collection<OtherClientDTO> otherClients, String availableSubRolesStr) {
		List<String> availableSubRoles = Arrays.asList(availableSubRolesStr.split(","));

		otherClients.removeIf(x -> !availableSubRoles.contains(x.getRoleNumber() + ""));

		return otherClients;
	}

	private PolicyClausesDTO initClauses(BeneficiaryChangeRequest beneficiaryChangeRequest) {
		PolicyClausesDTO clauses = policyClausesService.getClauses(beneficiaryChangeRequest.getPolicyId());

		// reset ids
		clauses.getDeath().forEach(x -> x.setPbcId(null));

		return clauses;
	}

	private List<PolicyBeneficiaryClauseDTO> filterAndSortClauses(List<PolicyBeneficiaryClauseDTO> clauses) {
		if(clauses == null) {
			return Collections.emptyList();
		}
		return clauses.stream()
				.filter(clause -> !ClauseType.NOMINATIVE.getCode().equals(clause.getTypeOfClause()))
				.sorted(Comparator.comparing(PolicyBeneficiaryClauseDTO::getRank))
				.collect(Collectors.toList());
	}

}
