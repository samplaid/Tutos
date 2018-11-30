package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.PolicyClausesService;
import lu.wealins.liability.services.core.mapper.PolicyBeneficiaryClauseMapper;
import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyBeneficiaryClauseEntity;
import lu.wealins.liability.services.core.persistence.repository.CliPolRelationshipRepository;
import lu.wealins.liability.services.core.persistence.repository.PolicyBeneficiaryClausesRepository;
import lu.wealins.liability.services.core.utils.HistoricManager;
import lu.wealins.common.dto.liability.services.PolicyBeneficiaryClauseDTO;
import lu.wealins.common.dto.liability.services.PolicyClausesDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyBeneficiaryClauseStatus;

@Service
public class PolicyClausesServiceImpl implements PolicyClausesService {

	public static final String LIFE_CLAUSE_TYPE = "Life";
	public static final String DEATH_CLAUSE_TYPE = "Death";
	private static final int LIFE_RELATION_TYPE = 16;
	private static final int DEATH_RELATION_TYPE = 7;
	private static final Integer INACTIF = Integer.valueOf(2);
	private static final String NOMINATIVE = "N";

	@Autowired
	private PolicyBeneficiaryClausesRepository repository;
	@Autowired
	private CliPolRelationshipRepository cliPolRelationshipRepository;
	@Autowired
	private PolicyBeneficiaryClauseMapper policyBeneficiaryClauseMapper;

	@Autowired
	private HistoricManager historicManager;

	@SuppressWarnings("boxing")
	@Override
	public void save(PolicyBeneficiaryClauseDTO policyBeneficiaryClause) {
		save(policyBeneficiaryClause, null);
	}

	@SuppressWarnings("boxing")
	@Override
	public void save(PolicyBeneficiaryClauseDTO policyBeneficiaryClause, String modifyProcess) {
		Assert.notNull(policyBeneficiaryClause);
		Assert.isTrue(StringUtils.isNotEmpty(policyBeneficiaryClause.getPolicy()), "Policy is mandatory.");
		PolicyBeneficiaryClauseEntity policyBeneficiaryClauseEntity = new PolicyBeneficiaryClauseEntity();
		if (policyBeneficiaryClause.getPbcId() != null) {
			policyBeneficiaryClauseEntity = repository.findOne(policyBeneficiaryClause.getPbcId());

			if (policyBeneficiaryClauseEntity == null) {
				policyBeneficiaryClauseEntity = new PolicyBeneficiaryClauseEntity();
			}
		}

		if (policyBeneficiaryClause.getPbcId() == null) {
			policyBeneficiaryClause.setPbcId(repository.findMaxPbcId() + 1);
		}

		policyBeneficiaryClauseMapper.asPolicyBeneficiaryClauseEntity(policyBeneficiaryClause, policyBeneficiaryClauseEntity);

		PolicyBeneficiaryClauseEntity savedPolicyBeneficiaryClause = save(policyBeneficiaryClauseEntity, modifyProcess);

		policyBeneficiaryClause.setPbcId(savedPolicyBeneficiaryClause.getPbcId());
	}

	@Override
	public PolicyBeneficiaryClauseEntity save(PolicyBeneficiaryClauseEntity policyBeneficiaryClause, String modifyProcess) {
		boolean isModified = historicManager.historize(policyBeneficiaryClause);

		if (isModified) {
			if (StringUtils.isNotBlank(modifyProcess)) {
				policyBeneficiaryClause.setModifyProcess(modifyProcess);
			}
			repository.save(policyBeneficiaryClause);
		}

		return policyBeneficiaryClause;
	}

	@Override
	public PolicyClausesDTO getClausesWithWorkflowItemId(String workflowItemId) {

		Collection<PolicyBeneficiaryClauseDTO> PolicyBeneficiaryClauses = policyBeneficiaryClauseMapper.asPolicyBeneficiaryClauseDTOs(repository.findAllByModifyProcess(workflowItemId));

		// Return value
		PolicyClausesDTO dto = new PolicyClausesDTO();
		dto.setMaturity(PolicyBeneficiaryClauses.stream().filter(x -> LIFE_CLAUSE_TYPE.equalsIgnoreCase(x.getType())).collect(Collectors.toList()));
		dto.setDeath(PolicyBeneficiaryClauses.stream().filter(x -> DEATH_CLAUSE_TYPE.equalsIgnoreCase(x.getType())).collect(Collectors.toList()));

		return dto;
	}

	@Override
	public PolicyClausesDTO getClauses(String policy) {

		// Trim the spaces before and after
		policy = policy.trim();

		// Maturity clauses
		List<PolicyBeneficiaryClauseDTO> maturity = _maturity_clauses(policy);
		List<PolicyBeneficiaryClauseDTO> death = _death_clause(policy);

		// Return value
		PolicyClausesDTO dto = new PolicyClausesDTO();
		dto.setMaturity(maturity);
		dto.setDeath(death);

		return dto;
	}

	/**
	 * Return the policy's death benefit clauses
	 * 
	 * @param policy
	 * @return
	 */
	private List<PolicyBeneficiaryClauseDTO> _death_clause(String policy) {
		return _clauses(policy, DEATH_CLAUSE_TYPE, DEATH_RELATION_TYPE);
	}

	/**
	 * Return the policy's maturity benefit clauses
	 * 
	 * @param policy
	 * @return
	 */
	private List<PolicyBeneficiaryClauseDTO> _maturity_clauses(String policy) {
		return _clauses(policy, LIFE_CLAUSE_TYPE, LIFE_RELATION_TYPE);
	}

	/**
	 * Merge the policy's text clauses with the nominated clauses, and sort the clauses by the clause rank and clause type.
	 * 
	 * The method will return a list of formatted clauses ins string.
	 * 
	 * @param policy
	 * @param clauseT
	 * @param relationT
	 * @return
	 */
	private List<PolicyBeneficiaryClauseDTO> _clauses(String policy, String clauseT, int relationT) {

		List<PolicyBeneficiaryClauseDTO> clauses = new ArrayList<PolicyBeneficiaryClauseDTO>();

		// find the policy's text clauses
		List<PolicyBeneficiaryClauseEntity> texts = repository.findAllByPolicyAndTypeIgnoreCaseAndStatusOrderByRankAsc(policy, clauseT, 1);
		if (!texts.isEmpty()) {
			for (PolicyBeneficiaryClauseEntity text : texts) {
				PolicyBeneficiaryClauseDTO clause = policyBeneficiaryClauseMapper.asPolicyBeneficiaryClauseDTO(text);
				clause.setTextOfClause(PolicyClauseWrapper.withTextClauses(text).toString());
				clause.setCode(text.getCode());
				clauses.add(clause);
			}
		}

		// load the beneficiaries from the policy's relationships
		List<CliPolRelationshipEntity> rels = cliPolRelationshipRepository.findAllByPolicyIdAndTypeAndStatusOrderByTypeNumberAsc(policy, relationT, 1);
		clauses.addAll(createNominativeClauses(clauseT, rels));

		clauses.sort(new Comparator<PolicyBeneficiaryClauseDTO>() {

			@Override
			public int compare(PolicyBeneficiaryClauseDTO o1, PolicyBeneficiaryClauseDTO o2) {

				if (o1.getRank() == o2.getRank()) {

					// If we have two clauses in the same rank, and that the first
					// one is nominated, the second one is not.
					//
					// This can normally never happen, but if it happens, we place
					// the text clause before the nominated.
					//
					return (!"N".equals(o1.getTypeOfClause()) && "N".equals(o2.getTypeOfClause())) ? 1 : 0;

				}
				return o1.getRank().compareTo(o2.getRank());
			}
		});

		return clauses;
	}

	private List<PolicyBeneficiaryClauseDTO> createNominativeClauses(String clauseT, Collection<CliPolRelationshipEntity> rels) {
		List<PolicyBeneficiaryClauseDTO> nominativeClauses = new ArrayList<>();
		if (!rels.isEmpty()) {
			for (CliPolRelationshipEntity rel : rels) {
				PolicyBeneficiaryClauseDTO clause = new PolicyBeneficiaryClauseDTO();
				clause.setRank(rel.getTypeNumber());
				clause.setPercentageSplit(rel.getPercentageSplit());
				clause.setType(clauseT);
				clause.setTypeOfClause("N"); // Nominative
				clause.setTextOfClause(PolicyClauseWrapper.withNominatedClauses(rel).toString());
				nominativeClauses.add(clause);
			}
		}
		return nominativeClauses;
	}


	@Override
	public void updatePolicyBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> deathPolicyBeneficiaryClauses, Collection<PolicyBeneficiaryClauseDTO> lifePolicyBeneficiaryClauses,
			String policyId) {
		PolicyClausesDTO clausesFromDB = getClauses(policyId);
		updatePolicyBeneficiaryClauses(clausesFromDB.getDeath(), deathPolicyBeneficiaryClauses);
		updatePolicyBeneficiaryClauses(clausesFromDB.getMaturity(), lifePolicyBeneficiaryClauses);
	}

	@Override
	public void updatePolicyBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> deathPolicyBeneficiaryClauses, Collection<PolicyBeneficiaryClauseDTO> lifePolicyBeneficiaryClauses,
			String policyId, String workflowItemId) {

		repository.deleteWithModifyProcess(workflowItemId + "", Integer.valueOf(PolicyBeneficiaryClauseStatus.INACTIVE.getValue()));
		deathPolicyBeneficiaryClauses.forEach(clause -> updateClause(clause, policyId, workflowItemId));
		lifePolicyBeneficiaryClauses.forEach(clause -> updateClause(clause, policyId, workflowItemId));
	}

	private void updateClause(PolicyBeneficiaryClauseDTO clause, String policyId, String workflowItemId) {
		clause.setStatus(Integer.valueOf(PolicyBeneficiaryClauseStatus.INACTIVE.getValue()));
		clause.setPolicy(policyId);
		clause.setFkPoliciespolId(policyId);
		save(clause, workflowItemId);
	}

	private void updatePolicyBeneficiaryClauses(Collection<PolicyBeneficiaryClauseDTO> clausesFromDB, Collection<PolicyBeneficiaryClauseDTO> clauses) {
		for (PolicyBeneficiaryClauseDTO policyBeneficiaryClause : clauses) {
			save(policyBeneficiaryClause);
		}

		// keep only clauses that we need to disabled
		clausesFromDB.removeIf(x -> contains(clauses, x) || NOMINATIVE.equalsIgnoreCase(x.getTypeOfClause()));

		for (PolicyBeneficiaryClauseDTO policyBeneficiaryClause : clausesFromDB) {
			policyBeneficiaryClause.setStatus(INACTIF);

			save(policyBeneficiaryClause);
		}

	}

	private boolean contains(Collection<PolicyBeneficiaryClauseDTO> clauses, PolicyBeneficiaryClauseDTO x) {
		return clauses.stream().anyMatch(y -> y.getPbcId().equals(x.getPbcId()));
	}

	@Override
	public Collection<PolicyBeneficiaryClauseDTO> createNominativeDeathClauses(String workflowItemId) {
		Collection<CliPolRelationshipEntity> cliPolEntities = cliPolRelationshipRepository.findAllByModifyProcessAndType(workflowItemId, DEATH_RELATION_TYPE);
		return createNominativeClauses(DEATH_CLAUSE_TYPE, cliPolEntities);
	}

	@Override
	public Collection<PolicyBeneficiaryClauseDTO> createNominativeLifeClauses(String workflowItemId) {
		Collection<CliPolRelationshipEntity> cliPolEntities = cliPolRelationshipRepository.findAllByModifyProcessAndType(workflowItemId, LIFE_RELATION_TYPE);
		return createNominativeClauses(LIFE_CLAUSE_TYPE, cliPolEntities);
	}
}
