/**
 * 
 */
package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.common.dto.webia.services.constantes.Constantes;
import lu.wealins.common.dto.webia.services.enums.CommissionStatus;
import lu.wealins.webia.services.core.service.CommissionReconciliationService;
import lu.wealins.webia.services.ws.rest.request.CommissionRequest;

/**
 * Default implementation of the {@link CommissionReconciliationService} interface.
 * 
 * @author oro
 *
 */
@Service
public class CommissionReconciliationServiceImpl implements CommissionReconciliationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommissionReconciliationServiceImpl.class);
	private static final String QUERY_UPDATE_WEBIA_COMMISSIONS_STATUS = "UPDATE COMMISSION_TO_PAY SET STATUS = :status WHERE COM_ID = :comId";
	private static final String QUERY_UPDATE_SAP_COMMISSIONS_STATUS = "UPDATE SAP_OPEN_BALANCE SET STATUS = :status WHERE SAP_OPENBAL_ID = :comId";
	private static final int COMMISSION_BATCH_SIZE = 500;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatableCommissionGroups) {
		LOGGER.info("validating reconciled commissions", validatableCommissionGroups.getCommissions());

		List<CommissionToPayDTO> allWebiaCommissions = validatableCommissionGroups.getCommissions().stream()
				.flatMap(commissionGroup -> commissionGroup.getCommissions().stream())
				.filter(commission -> !CommissionStatus.VALIDATED.getValue().equals(commission.getStatus()))
				.flatMap(commission -> commission.getWebiaCommissionToPay().stream())
				.collect(ArrayList<CommissionToPayDTO>::new, ArrayList<CommissionToPayDTO>::add, ArrayList<CommissionToPayDTO>::addAll);

		// Only update the status field because the webia service is not aware of PORTFOLIO commission type
		// and this method should not update data field other than the status.
		// Use batch update because data can be huge.
		List<List<CommissionToPayDTO>> webiaPartitioned = ListUtils.partition(allWebiaCommissions, COMMISSION_BATCH_SIZE);
		webiaPartitioned.forEach(commissions -> batchUpdateWebiaStatus(commissions, CommissionStatus.VALIDATED.getValue()));

		List<CommissionToPayDTO> allSapCommissions = validatableCommissionGroups.getCommissions().stream()
				.flatMap(commissionGroup -> commissionGroup.getCommissions().stream())
				.filter(commission -> !CommissionStatus.VALIDATED.getValue().equals(commission.getStatus()))
				.flatMap(commission -> commission.getSapCommissionsToPay().stream())
				.collect(ArrayList<CommissionToPayDTO>::new, ArrayList<CommissionToPayDTO>::add, ArrayList<CommissionToPayDTO>::addAll);

		// Only update the status field because the webia service is not aware of PORTFOLIO commission type
		// and this method should not update data field other than the status.
		// Use batch update because data can be huge.
		List<List<CommissionToPayDTO>> sapPartitioned = ListUtils.partition(allSapCommissions, COMMISSION_BATCH_SIZE);
		sapPartitioned.forEach(commissions -> batchUpdateSapStatus(commissions, CommissionStatus.VALIDATED.getValue()));

		return allWebiaCommissions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> donableCommissionGroups) {
		LOGGER.info("done validated commissions", donableCommissionGroups.getCommissions());
		
		List<CommissionToPayDTO> allWebiaCommissions = donableCommissionGroups.getCommissions().stream()
				.flatMap(commissionGroup -> commissionGroup.getCommissions().stream())
				.filter(commission -> CommissionStatus.VALIDATED.getValue().equals(commission.getStatus()))
				.flatMap(commission -> commission.getWebiaCommissionToPay().stream())
				.collect(ArrayList<CommissionToPayDTO>::new, ArrayList<CommissionToPayDTO>::add, ArrayList<CommissionToPayDTO>::addAll);
		
		// Only update the status field because the webia service is not aware of PORTFOLIO commission type
		// and this method should not update data field other than the status.
		// Use batch update because data can be huge.
		List<List<CommissionToPayDTO>> webiaPartitioned = ListUtils.partition(allWebiaCommissions, COMMISSION_BATCH_SIZE);
		webiaPartitioned.forEach(commissions -> batchUpdateWebiaStatus(commissions, CommissionStatus.DONE.getValue()));

		List<CommissionToPayDTO> allSapCommissions = donableCommissionGroups.getCommissions().stream()
				.flatMap(commissionGroup -> commissionGroup.getCommissions().stream())
				.filter(commission -> CommissionStatus.VALIDATED.getValue().equals(commission.getStatus()))
				.flatMap(commission -> commission.getSapCommissionsToPay().stream())
				.collect(ArrayList<CommissionToPayDTO>::new, ArrayList<CommissionToPayDTO>::add, ArrayList<CommissionToPayDTO>::addAll);

		// Only update the status field because the webia service is not aware of PORTFOLIO commission type
		// and this method should not update data field other than the status.
		// Use batch update because data can be huge.
		List<List<CommissionToPayDTO>> sapPartitioned = ListUtils.partition(allSapCommissions, COMMISSION_BATCH_SIZE);
		sapPartitioned.forEach(commissions -> batchUpdateSapStatus(commissions, CommissionStatus.DONE.getValue()));

		return allWebiaCommissions;
	}

	/**
	 * Update the status of the commission to <b>{@code VALIDATED}</b> by batch.
	 * 
	 * @param commissions a set of commission
	 * @return an array of the number of rows affected by each statement
	 */
	private int[] batchUpdateWebiaStatus(List<CommissionToPayDTO> commissions, String status) {
		LOGGER.info("Executing validation reconciled commission...");
		
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[commissions.size()];
		
		for (int i = 0; i < commissions.size(); i++) {
			Map<String, Object> valueMap = new HashMap<>();
			CommissionToPayDTO commission = commissions.get(i);
			Assert.notNull(commission.getComId(), "Cannot process the update of the commission status to 'Validated'. The commission [" + getCommissionInfo(commission) + "] has a null id.");

			String id = commission.getComId();
			commission.setStatus(status);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Validation reconcilied commission : Batch update parameter: new status={0}, id={1}", status, id);
			}

			valueMap.putIfAbsent("comId", id);
			valueMap.putIfAbsent("status", status);
			batchValues[i] = valueMap;
		}

		return jdbcTemplate.batchUpdate(QUERY_UPDATE_WEBIA_COMMISSIONS_STATUS, batchValues);
	}

	/**
	 * Update the status of the commission to <b>{@code VALIDATED}</b> by batch.
	 * 
	 * @param commissions a set of commission
	 * @return an array of the number of rows affected by each statement
	 */
	private int[] batchUpdateSapStatus(List<CommissionToPayDTO> commissions, String status) {
		LOGGER.info("Executing validation reconciled commission...");

		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[commissions.size()];

		for (int i = 0; i < commissions.size(); i++) {
			Map<String, Object> valueMap = new HashMap<>();
			CommissionToPayDTO commission = commissions.get(i);
			Assert.notNull(commission.getComId(), "Cannot process the update of the commission status to 'Validated'. The commission [" + getCommissionInfo(commission) + "] has a null id.");

			String id = commission.getComId();
			commission.setStatus(status);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Validation reconcilied commission : Batch update parameter: new status={0}, id={1}", status, id);
			}

			valueMap.putIfAbsent("comId", id);
			valueMap.putIfAbsent("status", status);
			batchValues[i] = valueMap;
		}
		
		return jdbcTemplate.batchUpdate(QUERY_UPDATE_SAP_COMMISSIONS_STATUS, batchValues);
	}

	private String getCommissionInfo(CommissionToPayDTO commission) {
		final StringBuilder info = new StringBuilder();
		info.append(commission.getAgentId());
		info.append(Constantes.HYPHEN);
		info.append(commission.getComDate());
		info.append(Constantes.HYPHEN);
		info.append(commission.getComCurrency());
		info.append(Constantes.HYPHEN);
		info.append(commission.getComType());
		return info.toString();
	}

}
