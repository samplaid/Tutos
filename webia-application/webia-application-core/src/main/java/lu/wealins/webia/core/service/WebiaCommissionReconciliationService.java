/**
 * 
 */
package lu.wealins.webia.core.service;

import java.util.List;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionRequest;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;

/**
 * This interface exposes methods for commission reconciliation.
 * 
 * @author oro
 *
 */
public interface WebiaCommissionReconciliationService {

	/**
	 * Search all reconcilable commissions by type. The PORTFOLIO commission type is mapped to the set of values ADM, SWITCH, SURR, OPCVM, PRADM. <br>
	 * <br>
	 * The method returns a pageable commissions.
	 * 
	 * @param commissionType the commission type
	 * @param text
	 * @param page the page number
	 * @param size the size limit.
	 * @return the pageable commissions
	 */
	PageResult<CommissionReconciliationDTO> searchCommissionReconciliations(String commissionType, String text, int page, int size);

	/**
	 * This method returns the reconciliation of {@code WEBIA} commissions and {@code SAP} commissions grouped by {@code agent code}, {@code agent name}
	 * 
	 * @param commissionType the commission type
	 * @param text
	 * @param page the page number
	 * @param size the page size.
	 * @return a pageable commission group.
	 */
	PageResult<CommissionReconciliationGroupDTO> searchCommissionReconciliationGroup(String commissionType, String text, int page, int size);

	/**
	 * This method returns the reconciliation of {@code WEBIA} commissions and {@code SAP} commissions.
	 * 
	 * @param webiaCommissions the webia commissions
	 * @param sapCommissions the webia commissions
	 * @return the commission reconciliation
	 */
	List<CommissionReconciliationDTO> reconcileCommissions(List<CommissionToPayDTO> webiaCommissions, List<CommissionToPayDTO> sapCommissions);

	/**
	 * This method returns the list of validated commissions.
	 * 
	 * @param reconciliedCommissionsToValidate the commissions to validate
	 * @return the list of validated commissions
	 */
	List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> reconciledCommissionsToValidate);

	/**
	 * This method returns the list of done commissions.
	 * 
	 * @param validatedCommissionsToDone the commissions to done
	 * @return the list of done commissions
	 */
	List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatedCommissionsToDone);


}
