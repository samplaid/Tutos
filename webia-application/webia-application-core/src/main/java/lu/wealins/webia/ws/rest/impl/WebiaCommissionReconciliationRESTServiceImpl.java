package lu.wealins.webia.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionRequest;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.core.service.WebiaCommissionReconciliationService;
import lu.wealins.webia.ws.rest.WebiaCommissionReconciliationRESTService;

/**
 * Default implementation class for {@link WebiaCommissionReconciliationRESTService} service.
 * 
 * @author oro
 *
 */
@Component
public class WebiaCommissionReconciliationRESTServiceImpl implements WebiaCommissionReconciliationRESTService {

	@Autowired
	private WebiaCommissionReconciliationService commissionReconciliationService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageResult<CommissionReconciliationDTO> searchCommissionReconciliations(SecurityContext context, String commissionType, String text, int page, int size) {
		return commissionReconciliationService.searchCommissionReconciliations(commissionType, text, page, size);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageResult<CommissionReconciliationGroupDTO> searchCommissionReconciliationGroup(SecurityContext context, String commissionType, String text, int page, int size) {
		return commissionReconciliationService.searchCommissionReconciliationGroup(commissionType, text, page, size);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> validateReconciledCommissions(SecurityContext context, CommissionRequest<CommissionReconciliationGroupDTO> reconciledCommissionsToValidate) {
		return commissionReconciliationService.validateReconciledCommissions(reconciledCommissionsToValidate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> doneValidatedCommissions(SecurityContext context, CommissionRequest<CommissionReconciliationGroupDTO> validatedCommissionsToDone) {
		return commissionReconciliationService.doneValidatedCommissions(validatedCommissionsToDone);
	}


}
