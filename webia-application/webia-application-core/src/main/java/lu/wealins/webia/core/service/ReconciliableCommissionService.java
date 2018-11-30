/**
 * 
 */
package lu.wealins.webia.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionRequest;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;

/**
 * This interface provides methods that returns reconciliable commission.
 * 
 * @author oro
 *
 */
public interface ReconciliableCommissionService {

	/**
	 * Retrieve all reconcilable commissions from <b>{@code WEBIA}</b>. <br>
	 * <br>
	 * <b>Note:</b> The returned commission has a type belong to the list <b>{@code 'ENTRY','ADM','SWITCH','SURR','OPCVM','PRADM'}</b> which are retrieved from <b>{@code COMMISSION_TO_PAY}</b> table.
	 * 
	 * @see #retrieveSapReconcilableCommission(String) retrieveSapReconcilableCommission
	 * @param commissionType the type of commission <b>({@code ENTRY}, {@code PORTFOLIO})</b>
	 * @return The CommissionToPayWrapperDTO.
	 */
	List<CommissionToPayDTO> retrieveWebiaReconcilableCommission(String commissionType);

	/**
	 * Retrieve all reconcilable commissions from <b>{@code SAP}</b>. <br>
	 * <br>
	 * <b>Note:</b> The returned commission has a type belong to the list <b>{@code 'PORTFOILIO','ENTRY'}</b> which are retrieved from <b>{@code SAP_OPEN_BALANCE}</b> table.
	 * 
	 * @see #retrieveWebiaReconcilableCommission(String) retrieveWebiaReconcilableCommission
	 * @param reconcilableCommissionAggregate the aggregate of reconciliation commission
	 * @return list of commission
	 */
	List<CommissionToPayDTO> retrieveSapReconcilableCommission(String commissionType);

	/**
	 * Validate the reconciled commissions in parameter</b>. <br>
	 * <br>
	 * 
	 * @param reconciliedCommissionsToValidate the reconciled commissions to validate
	 * @return list of validated commissions
	 */
	List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> reconciledCommissionsToValidate);

	/**
	 * Done the validated commissions in parameter</b>. <br>
	 * <br>
	 * 
	 * @param validatedCommissionsToDone the validated commissions to done
	 * @return list of done commissions
	 */
	List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatedCommissionsToDone);
}
