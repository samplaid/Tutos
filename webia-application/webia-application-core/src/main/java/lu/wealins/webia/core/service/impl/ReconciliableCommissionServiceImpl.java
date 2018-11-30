/**
 * 
 */
package lu.wealins.webia.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionRequest;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.ReconciliableCommissionService;
import lu.wealins.webia.core.utils.CommissionUtils;
import lu.wealins.webia.core.utils.RestClientUtils;

/**
 * Default implementation of {@link ReconciliableCommissionService} service.
 * 
 * @author oro
 *
 */
@Service
public class ReconciliableCommissionServiceImpl implements ReconciliableCommissionService {
	private static final String WEBIA_RECONCILIATION_COMMISSIONS_WEBIA = "webia/commission/reconciliation/webia";
	private static final String WEBIA_RECONCILIATION_COMMISSIONS_SAP = "webia/commission/reconciliation/sap";
	private static final String WEBIA_VALIDATE_RECONCILED_COMMISSIONS = "webia/commission/reconciliation/validate/reconciled";
	private static final String WEBIA_DONE_VALIDATED_COMMISSIONS = "webia/commission/reconciliation/done/validated";

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private LiabilityAgentService liabilityAgentService;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> retrieveWebiaReconcilableCommission(String commissionType) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();
		params.add("commissionType", commissionType);
		List<CommissionToPayDTO> webiaReconcilableCommissions = restClientUtils.get(WEBIA_RECONCILIATION_COMMISSIONS_WEBIA, "/", params, new GenericType<List<CommissionToPayDTO>>() {
		});
		List<String> agentIds = webiaReconcilableCommissions.stream()
				.map(commission -> commission.getAgentId())
				.distinct()
				.collect(Collectors.toList());

		List<AgentLightDTO> agents = liabilityAgentService.retrievePayableCommissionAgentOwner(agentIds);

		return webiaReconcilableCommissions.stream().filter(commission -> {
			return agents.stream().anyMatch(agent -> StringUtils.equals(StringUtils.stripToEmpty(agent.getAgtId()), StringUtils.stripToEmpty(commission.getAgentId())));
		}).map(com -> {
			com.setComType(CommissionUtils.asSapCommissionTypes(com.getComType()));
			return com;
		}).collect(Collectors.toList());
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> retrieveSapReconcilableCommission(String commissionType) {
		return restClientUtils.post(WEBIA_RECONCILIATION_COMMISSIONS_SAP, commissionType, new GenericType<List<CommissionToPayDTO>>() {
		});
	}

	@Override
	public List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> reconciledCommissionsToValidate) {
		return restClientUtils.post(WEBIA_VALIDATE_RECONCILED_COMMISSIONS, reconciledCommissionsToValidate, new GenericType<List<CommissionToPayDTO>>() {
		});
	}

	@Override
	public List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatedCommissionsToDone) {
		return restClientUtils.post(WEBIA_DONE_VALIDATED_COMMISSIONS, validatedCommissionsToDone, new GenericType<List<CommissionToPayDTO>>() {
		});
	}

}
