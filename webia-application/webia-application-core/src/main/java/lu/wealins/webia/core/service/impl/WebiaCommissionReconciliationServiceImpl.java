/**
 * 
 */
package lu.wealins.webia.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.PageResultBuilder;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionRequest;
import lu.wealins.common.dto.webia.services.CommissionToPayAggregatedDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.core.constants.CommissionConstant;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.ReconciliableCommissionService;
import lu.wealins.webia.core.service.WebiaCommissionReconciliationService;
import lu.wealins.webia.core.utils.CommissionUtils;

/**
 * An implementation class of commission reconciliation interface
 * 
 * @author oro
 *
 */
@Service
public class WebiaCommissionReconciliationServiceImpl implements WebiaCommissionReconciliationService {

	@Autowired
	private ReconciliableCommissionService reconciliableCommissionService;

	@Autowired
	private LiabilityAgentService liabilityAgentService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageResult<CommissionReconciliationDTO> searchCommissionReconciliations(String commissionType, String text, int page, int size) {
		List<CommissionReconciliationDTO> commissionReconciliations = retrieveCommissionReconciliations(commissionType);
		commissionReconciliations.sort(CommissionUtils.gapComparator());
		commissionReconciliations = CommissionUtils.searchByCodeName(commissionReconciliations, text);
		return new PageResultBuilder<CommissionReconciliationDTO>().createPageResult(page, size, commissionReconciliations, commissionReconciliations.size());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageResult<CommissionReconciliationGroupDTO> searchCommissionReconciliationGroup(String commissionType, String text, int page, int size) {
		List<CommissionReconciliationDTO> commissionReconciliations = retrieveCommissionReconciliations(commissionType);
		List<CommissionReconciliationGroupDTO> groups = CommissionUtils.groupCommissionReconciliations(commissionReconciliations);
		groups = CommissionUtils.searchInGroupByCodeName(groups, text);
		return new PageResultBuilder<CommissionReconciliationGroupDTO>().createPageResult(page, size, groups, groups.size());
	}

	/**
	 * {@inheritDoc} <br>
	 * The {@code WEBIA} commission is used as reference. <br>
	 * <br>
	 * The commissions that are passed as parameter are grouped by {@link CommissionToPayAggregatedDTO}. The {@code sapCommissions} will be matched with the {@code webiaCommissions} using the
	 * {@link CommissionToPayAggregatedDTO aggregate} as key and the result commission of reconciliation will be created that will contains the sum of the sap commission amount (stored in
	 * {@code sapBalance} field), the sum of the webia commission amount (stored in {@code statementBalance} field), the {@code GAP} between the two balances and the {@code status} of the
	 * reconciliation. <br>
	 * <br>
	 * If the {@code GAP} has a value equal to zero, the result commission is {@code RECONCILED}. Otherwise, it is {@code NOT RECONCILED}. <br>
	 * <br>
	 * In case of the {@code sapCommissions} are not matched in the {@code webiaCommissions}, the {@code webiaCommissions} is taken with {@code NOT RECONCILED}
	 */
	@Override
	public List<CommissionReconciliationDTO> reconcileCommissions(List<CommissionToPayDTO> webiaCommissions, List<CommissionToPayDTO> sapCommissions) {
		List<CommissionReconciliationDTO> commissionReconciliations = new ArrayList<>();

		Map<String, AgentLightDTO> agentById = groupAgentsById(webiaCommissions, sapCommissions);

		Map<CommissionToPayAggregatedDTO, List<CommissionToPayDTO>> webiaAggregatedCommissions = groupCommissionByAggregate(webiaCommissions);
		Map<CommissionToPayAggregatedDTO, List<CommissionToPayDTO>> sapAggregatedCommissions = groupCommissionByAggregate(sapCommissions);

		Map<CommissionToPayAggregatedDTO, List<CommissionToPayDTO>> commissions = new HashMap<>();
		commissions.putAll(sapAggregatedCommissions);
		commissions.putAll(webiaAggregatedCommissions);

		for (Entry<CommissionToPayAggregatedDTO, List<CommissionToPayDTO>> commissionsAggregated : commissions.entrySet()) {
			CommissionToPayAggregatedDTO aggregated = commissionsAggregated.getKey();

			// if the agent is not on the agentById list (IE : it's not a commission payable agent)
			if (agentById.get(aggregated.getAgentId()) == null) {
				continue;
			}

			List<CommissionToPayDTO> qualifiedSapCommissions = sapAggregatedCommissions.getOrDefault(aggregated, new ArrayList<>());
			List<CommissionToPayDTO> qualifiedwebiaCommissions = webiaAggregatedCommissions.getOrDefault(aggregated, new ArrayList<>());;

			if (qualifiedwebiaCommissions.isEmpty() && qualifiedSapCommissions.isEmpty()) {
				continue;
			}

			BigDecimal sapBalance = sumCommissionAmount(qualifiedSapCommissions);
			BigDecimal statementBalance = sumCommissionAmount(qualifiedwebiaCommissions);

			if ((sapBalance != null && BigDecimal.ZERO.compareTo(sapBalance) != 0) || (statementBalance != null && BigDecimal.ZERO.compareTo(statementBalance) != 0)) {

				BigDecimal gap = CommissionUtils.gap(sapBalance, statementBalance);

				CommissionReconciliationDTO commissionReconciliation = new CommissionReconciliationDTO();
				commissionReconciliation.setAgentId(aggregated.getAgentId());
				commissionReconciliation.setName(agentById.get(aggregated.getAgentId()).getName());
				commissionReconciliation.setCrmId(agentById.get(aggregated.getAgentId()).getCrmId());
				commissionReconciliation.setPeriod(aggregated.getPeriod());
				commissionReconciliation.setCurrency(aggregated.getCurrency());
				commissionReconciliation.setSapBalance(sapBalance);
				commissionReconciliation.setStatementBalance(statementBalance);
				commissionReconciliation.setGap(gap);
				commissionReconciliation.setComType(commissionsAggregated.getKey().getType());
				if (isValidated(qualifiedwebiaCommissions)) {
					commissionReconciliation.setStatus(CommissionConstant.VALIDATED);
				} else {
					if (isValidated(qualifiedSapCommissions)) {
						commissionReconciliation.setStatus(CommissionConstant.VALIDATED);
					} else {
						commissionReconciliation.setStatus(CommissionUtils.determineStatus(gap));
					}
				}
				commissionReconciliation.setWebiaCommissionToPay(qualifiedwebiaCommissions);
				commissionReconciliation.setSapCommissionsToPay(qualifiedSapCommissions);
				commissionReconciliations.add(commissionReconciliation);
			}
		}

		return commissionReconciliations;
	}


	@Override
	public List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> reconciledCommissionsToValidate) {
		return reconciliableCommissionService.validateReconciledCommissions(reconciledCommissionsToValidate);
	}

	@Override
	public List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatedCommissionsToDone) {
		return reconciliableCommissionService.doneValidatedCommissions(validatedCommissionsToDone);
	}

	/**
	 * @param commissionType
	 * @return
	 */
	private List<CommissionReconciliationDTO> retrieveCommissionReconciliations(String commissionType) {
		List<CommissionToPayDTO> webiaCommissions = reconciliableCommissionService.retrieveWebiaReconcilableCommission(commissionType);
		List<CommissionToPayDTO> sapCommissions = reconciliableCommissionService.retrieveSapReconcilableCommission(commissionType);

		List<CommissionReconciliationDTO> commissionReconciliations = reconcileCommissions(webiaCommissions, sapCommissions);

		return commissionReconciliations;
	}

	/**
	 * Group commission by the {@link CommissionToPayAggregatedDTO}.
	 * 
	 * @param webiaCommissions the list to group.
	 * @return grouped commission by the aggregate.
	 */
	private Map<CommissionToPayAggregatedDTO, List<CommissionToPayDTO>> groupCommissionByAggregate(List<CommissionToPayDTO> webiaCommissions) {
		Map<CommissionToPayAggregatedDTO, List<CommissionToPayDTO>> aggregatedCommissions = webiaCommissions.stream().collect(
				Collectors.groupingBy(CommissionToPayAggregatedDTO::new));
		return aggregatedCommissions;
	}

	private Map<String, AgentLightDTO> groupAgentsById(List<CommissionToPayDTO> webiaCommissions, List<CommissionToPayDTO> sapCommissions) {
		List<CommissionToPayDTO> commissions = new ArrayList<>();
		commissions.addAll(webiaCommissions);
		commissions.addAll(sapCommissions);

		List<String> agentIds = commissions.stream().map(commission -> commission.getAgentId()).distinct().collect(Collectors.toList());
		List<AgentLightDTO> agents = liabilityAgentService.retrievePayableCommissionAgentOwner(agentIds);
		Map<String, AgentLightDTO> agentById = agents.stream().collect(Collectors.toMap(AgentLightDTO::getAgtId, Function.identity()));
		return agentById;
	}

	/**
	 * Sum the commission amount in the list and returns a new amount as commission reconciliation balance.
	 * 
	 * @param commissions a set of commissions.
	 * @return
	 */
	private BigDecimal sumCommissionAmount(List<CommissionToPayDTO> commissions) {
		BigDecimal sapBalance = null;

		if (!commissions.isEmpty()) {
			sapBalance = commissions.stream()
					.map(commission -> commission.getComAmount())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}

		return sapBalance;
	}

	/**
	 * Check if the commission to pay is already validated.
	 * 
	 * @param commissions a set of commissions.
	 * @return
	 */
	private boolean isValidated(List<CommissionToPayDTO> commissions) {
		boolean isValidated = false;

		if (!commissions.isEmpty()) {
			isValidated = commissions.stream()
					.allMatch(commission -> CommissionConstant.VALIDATED.equals(commission.getStatus()));
		}

		return isValidated;
	}

}
