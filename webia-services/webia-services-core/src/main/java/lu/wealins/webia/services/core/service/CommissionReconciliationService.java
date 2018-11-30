/**
 * 
 */
package lu.wealins.webia.services.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.services.ws.rest.request.CommissionRequest;

/**
 * This interface contains methods that are used during the reconciliation of the commission.
 * 
 * @author oro
 *
 */
public interface CommissionReconciliationService {

	/**
	 * This method will check if all commissions inside the commission group are reconciled. If any element is not reconciled then an exception will be thrown. Then, it updates the commissions status
	 * to <b>{@code VALIDATED}</b>.
	 * 
	 * @param validatableCommissionGroup a set of validatable commissions group
	 * @return the updated commissions.
	 * @throws IllegalArgumentException if any element is not reconciled inside a group.
	 */
	List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatableCommissionGroup);
	
	/**
	 * This method will check if all commissions inside the commission group are validated. If any element is not validated then an exception will be thrown. Then, it updates the commissions status
	 * to <b>{@code DONE}</b>.
	 * 
	 * @param donableCommissionGroup a set of donable commissions group
	 * @return the updated commissions.
	 * @throws IllegalArgumentException if any element is not validated inside a group.
	 */
	List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> donableCommissionGroup);
}
