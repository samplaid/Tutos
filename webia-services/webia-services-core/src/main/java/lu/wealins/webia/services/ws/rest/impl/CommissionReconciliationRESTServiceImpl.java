package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.services.core.service.CommissionReconciliationService;
import lu.wealins.webia.services.core.service.ReconciliableCommissionService;
import lu.wealins.webia.services.ws.rest.CommissionReconciliationRESTService;
import lu.wealins.webia.services.ws.rest.request.CommissionRequest;

@Component
public class CommissionReconciliationRESTServiceImpl implements CommissionReconciliationRESTService {

	@Autowired
	private ReconciliableCommissionService reconciliableCommissionService;

	@Autowired
	private CommissionReconciliationService reconciliationService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> retrieveWebiaReconcilableCommission(SecurityContext context, String commissionType) {
		return reconciliableCommissionService.retrieveWebiaReconcilableCommission(commissionType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> retrieveSapReconcilableCommission(SecurityContext context, String commissionType) {
		return reconciliableCommissionService.retrieveSapReconcilableCommission(commissionType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> reconciliedCommissionsToValidate) {
		return reconciliationService.validateReconciledCommissions(reconciliedCommissionsToValidate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatedCommissionsToDone) {
		return reconciliationService.doneValidatedCommissions(validatedCommissionsToDone);
	}

}
