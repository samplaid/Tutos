/**
 * 
 */
package lu.wealins.webia.core.utils;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupAggregateDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.webia.core.constants.CommissionConstant;

/**
 * This class contains all commission utilities
 * 
 * @author oro
 *
 */
public class CommissionUtils {

	/**
	 * Determine the reconciliation of the commission based on the {@code GAP} value. If the {@code GAP} is equal to zero then the reconciliation status will become <b>{@code RECONCILED}</b>.
	 * Otherwise, it will become <b>{@code NOT RECONCILED}</b>
	 * 
	 * @param gap an amount
	 * @return the reconciliation status
	 */
	public static String determineStatus(BigDecimal gap) {
		String status = CommissionConstant.NOT_RECONCILED;

		if (isReconcilable(gap)) {
			status = CommissionConstant.RECONCILED;
		}

		return status;
	}

	/**
	 * Compare two reconciliation commissions using all fields
	 * 
	 * @param com1 first commission
	 * @param com2 second commission
	 * @return
	 */
	public static int compareReconciliationCommission(CommissionReconciliationDTO com1, CommissionReconciliationDTO com2) {
		if (com1 == com2) {
			return 0;
		}

		if (com1 == null) {
			return 1;
		}

		if (com2 == null) {
			return -1;
		}

		int comp = com1.compareTo(com2);

		if (comp != 0) {
			return comp;
		}

		if (StringUtils.isBlank(com1.getComType())) {
			if (StringUtils.isNotBlank(com2.getComType())) {
				return -1;
			}
		} else if (StringUtils.isBlank(com2.getComType())) {
			return 1;
		}

		return com1.getComType().compareTo(com2.getComType());

	}

	/**
	 * Convert the webia commission type to sap commission type.
	 * 
	 * @param webiaCommissionType the webia commission type.
	 * @return list of sap commission type
	 */
	public static String asSapCommissionTypes(String webiaCommissionType) {
		String commssionType = StringUtils.EMPTY;

		if (CommissionConstant.ENTRY.equalsIgnoreCase(webiaCommissionType)) {
			commssionType = CommissionConstant.ENTRY;
		} else if (StringUtils.isNotBlank(webiaCommissionType) && CommissionConstant.PORTFOLIO_GROUP.contains(webiaCommissionType)) {
			commssionType = CommissionConstant.PORTFOLIO;
		} else {
			commssionType = CommissionConstant.UNKNOWN;
		}

		return commssionType;
	}

	/**
	 * Search the commission reconciliations by the agent code or/and name.
	 * 
	 * @param groups a list of commission reconciliation
	 * @param text the search value
	 */
	public static List<CommissionReconciliationGroupDTO> searchInGroupByCodeName(List<CommissionReconciliationGroupDTO> groups, String text) {
		if (StringUtils.isBlank(text)) {
			return groups;
		}

		return groups.stream()
				.filter(commissionGroup -> contains(text, commissionGroup.getAggregate().getAgentId(), commissionGroup.getAggregate().getName()))
				.collect(Collectors.toList());

	}

	/**
	 * Search the commission reconciliations by the agent code or/and name.
	 * 
	 * @param commissionReconciliations a list of commission reconciliation
	 * @param text the search value
	 */
	public static List<CommissionReconciliationDTO> searchByCodeName(List<CommissionReconciliationDTO> commissionReconciliations, String text) {
		if (StringUtils.isBlank(text)) {
			return commissionReconciliations;
		}

		return commissionReconciliations.stream()
				.filter(commission -> contains(text, commission.getAgentId(), commission.getName()))
				.collect(Collectors.toList());
	}

	/**
	 * If all commission reconciliations {@code GAP} amount for an {@code agent} are equal to zero then the status of the reconciliation group will be {@link CommissionConstant#RECONCILED RECONCILED}
	 * Otherwise, it will be {@link CommissionConstant#NOT_RECONCILED NOT_RECONCILED}
	 * 
	 * @param groups the group of commission reconciliations.
	 */
	public static void setCommissionReconiliationGroupStatus(SortedSet<CommissionReconciliationGroupDTO> groups) {
		groups.forEach(group -> setCommissionReconiliationGroupStatus(group));
	}

	/**
	 * If all commission reconciliations {@code GAP} amount for an {@code agent} are equal to zero then the status of the reconciliation group will be {@link CommissionConstant#RECONCILED RECONCILED}
	 * Otherwise, it will be {@link CommissionConstant#NOT_RECONCILED NOT_RECONCILED}
	 * 
	 * @param group the group of commission reconciliations.
	 */
	public static void setCommissionReconiliationGroupStatus(CommissionReconciliationGroupDTO group) {
		boolean allGapZero = group.getCommissions().stream().map(CommissionReconciliationDTO::getGap)
				.allMatch(gap -> isReconcilable(gap));
		boolean isValidated = group.getCommissions().stream().map(CommissionReconciliationDTO::getStatus)
				.allMatch(status -> CommissionConstant.VALIDATED.equals(status));

		if (!group.getCommissions().isEmpty() && isValidated) {
			group.getAggregate().setStatus(CommissionConstant.VALIDATED);
		} else {
			if (!group.getCommissions().isEmpty() && allGapZero) {
				group.getAggregate().setStatus(CommissionConstant.RECONCILED);
			} else {
				group.getAggregate().setStatus(CommissionConstant.NOT_RECONCILED);
			}
		}
	}
	
	/**
	 * If all commission reconciliations {@code statemetnId} are not null then the statement is <code>true</code>
	 * Otherwise, it will be <code>false</code>
	 * 
	 * @param group the group of commission reconciliations.
	 */
	public static void setCommissionReconiliationGroupStatement(CommissionReconciliationGroupDTO group) {
		boolean statement = group.getCommissions().stream().flatMap(reconciliation -> reconciliation.getWebiaCommissionToPay().stream())
				.allMatch(comTopay -> Objects.nonNull(comTopay.getStatementId()));
		group.getAggregate().setStatement(statement);
	}

	/**
	 * Returns true if the {@code GAP} is equal to zero.
	 * 
	 * @param gap the {@code GAP} value
	 * @return a true if the condition is met.
	 */
	public static boolean isReconcilable(BigDecimal gap) {
		return gap != null && BigDecimal.ZERO.compareTo(gap) == 0;
	}

	/**
	 * Group the commission reconciliation and evaluate the status of the reconciliation group.
	 * 
	 * @param commissionReconciliations the commission to group
	 * @return a commission list
	 */
	public static List<CommissionReconciliationGroupDTO> groupCommissionReconciliations(List<CommissionReconciliationDTO> commissionReconciliations) {
		List<CommissionReconciliationGroupDTO> groups = new ArrayList<>();

		for (CommissionReconciliationDTO commission : commissionReconciliations) {
			CommissionReconciliationGroupAggregateDTO aggregate = new CommissionReconciliationGroupAggregateDTO(commission);
			aggregate.setCrmId(commission.getCrmId());

			Optional<CommissionReconciliationGroupDTO> groupItem = groups.stream()
					.filter(group -> group.getAggregate().equals(aggregate))
					.findFirst();

			if (!groupItem.isPresent()) {
				CommissionReconciliationGroupDTO newGroup = new CommissionReconciliationGroupDTO(aggregate);
				newGroup.getCommissions().add(commission);
				CommissionUtils.setCommissionReconiliationGroupStatus(newGroup);
				CommissionUtils.setCommissionReconiliationGroupStatement(newGroup);
				newGroup.getCommissions().sort(gapComparator());
				groups.add(newGroup);
			} else {
				CommissionReconciliationGroupDTO presentGroup = groupItem.get();
				presentGroup.getCommissions().add(commission);
				CommissionUtils.setCommissionReconiliationGroupStatus(presentGroup);
				CommissionUtils.setCommissionReconiliationGroupStatement(presentGroup);
				presentGroup.getCommissions().sort(gapComparator());
			}
		}


		return groups.stream().sorted().collect(Collectors.toList());
	}

	/**
	 * Calculate the difference ({@code GAP}) between the WEBIA balance amount and the SAP balance amount in case of the two amount is not null. <br>
	 * <b>Note</b>: The method returns always a <b>positive</b> value.
	 * 
	 * @param sapBalance the SAP balance amount
	 * @param statementBalance WEBIA balance amount
	 * @return Null if one of the amount is null. Otherwise, it returns the
	 */
	public static BigDecimal gap(BigDecimal sapBalance, BigDecimal statementBalance) {
		BigDecimal gap = null;

		if (sapBalance == null) {
			gap = statementBalance;
		} else if (statementBalance == null) {
			gap = sapBalance;
		} else {
			gap = statementBalance.subtract(sapBalance);
		}

		return gap != null ? gap.abs() : null;
	}

	/**
	 * Returns a function interface comparator of an commission reconciliation object by the {@link CommissionReconciliationDTO#getGap() GAP}.
	 * 
	 * @return a comparator.
	 */
	public static Comparator<? super CommissionReconciliationDTO> gapComparator() {
		return (com1, com2) -> {
			if (com1 == com2) {
				return 0;
			}

			if (com1 == null) {
				return 1;
			}

			if (com2 == null) {
				return -1;
			}

			return ObjectUtils.compare(com1.getGap(), com2.getGap());
		};
	}

	private static boolean contains(String searchChar, String... characters) {
		boolean exists = false;

		for (String character : characters) {
			String char1 = StringUtils.stripToEmpty(character);
			
			// Case insensitive and accent insensitive character string
			String normalizedCharl = Normalizer.normalize(char1, Normalizer.Form.NFD);
			String CIAICharl = normalizedCharl.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
			
			// Case insensitive and accent insensitive search string
			String normalizedSearchChar = Normalizer.normalize(searchChar, Normalizer.Form.NFD);
			String CIAISearchChar = normalizedSearchChar.replaceAll("[^\\p{ASCII}]", "").toLowerCase();

			if (StringUtils.contains(CIAICharl, CIAISearchChar)) {
				exists = true;
				break;
			}
		}

		return exists;
	}
}
