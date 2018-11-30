/**
 * 
 */
package lu.wealins.webia.services.core.service;

import java.util.List;

import lu.wealins.common.dto.webia.services.CommissionToPayDTO;

/**
 * This interface exposes methods to manipulate reconcilable commissions.
 * 
 * @author oro
 *
 */
public interface ReconciliableCommissionService {

	/**
	 * Find all webia reconcilable commissions by type. The type having value {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#PORTFOLIO PORTFOLIO} is mapped to the set of values
	 * {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#ADM ADM}, {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#SWITCH SWITCH},
	 * {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#SURR SURR}, {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#OPCVM OPCVM},
	 * {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#PRADM PRADM} and the {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#ENTRY ENTRY} type takes the same
	 * value.
	 * 
	 * @param commissionType the commission type
	 * @return a list of reconcilable commission
	 */
	List<CommissionToPayDTO> retrieveWebiaReconcilableCommission(String commissionType);

	/**
	 * Find all sap reconcilable commissions by type. The type having value {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#PORTFOLIO PORTFOLIO} is mapped to the set of values
	 * {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#ADM ADM}, {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#SWITCH SWITCH},
	 * {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#SURR SURR}, {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#OPCVM OPCVM},
	 * {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#PRADM PRADM} and the {@link lu.wealins.common.dto.webia.services.constantes.CommissionConstant#ENTRY ENTRY} type takes the same
	 * value.
	 * 
	 * @param commissionType the commission type
	 * @return a list of reconcilable commission
	 */
	List<CommissionToPayDTO> retrieveSapReconcilableCommission(String commissionType);
}
