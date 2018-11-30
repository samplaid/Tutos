package lu.wealins.webia.services.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.services.ws.rest.request.CommissionRequest;

/**
 * PaymentCommissionRESTService is a REST service to handle commission to pay services.
 * 
 * @param <T>
 *
 */
@Path("commission/reconciliation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface CommissionReconciliationRESTService {

	/**
	 * Retrieve all webia reconcilable commissions by type. The type having value {@code PORTFOLIO} is mapped to the set of values {@code ADM}, {@code SWITCH}, {@code SURR}, {@code OPCVM},
	 * {@code PRADM} and the {@code ENTRY} type takes the same value.
	 * 
	 * @param context the security context
	 * @param commissionType the commission type
	 * @return a list of reconcilable commission
	 */
	@GET
	@Path("/webia")
	List<CommissionToPayDTO> retrieveWebiaReconcilableCommission(@Context SecurityContext context, @QueryParam("commissionType") String commissionType);

	/**
	 * Retrieve all sap reconcilable commissions by type.
	 * 
	 * @param context the security context
	 * @param commissionType the commission type
	 * @return a list of reconcilable commission
	 */
	@POST
	@Path("/sap")
	List<CommissionToPayDTO> retrieveSapReconcilableCommission(@Context SecurityContext context, String commissionType);

	/**
	 * This method will check if all commissions inside the commission group are reconciled. If any element is not reconciled then an exception will be thrown. Then, it updates the commissions status
	 * to <b>{@code VALIDATED}</b>.
	 * 
	 * @param context the security context
	 * @return the updated commissions.
	 */
	@POST
	@Path("/validate/reconciled")
	List<CommissionToPayDTO> validateReconciledCommissions(CommissionRequest<CommissionReconciliationGroupDTO> reconciliedCommissionsToValidate);

	/**
	 * This method will check if all commissions inside the commission group are validated. If any element is not validated then an exception will be thrown. Then, it updates the commissions status
	 * to <b>{@code DONE}</b>.
	 * 
	 * @param context the security context
	 * @return the updated commissions.
	 */
	@POST
	@Path("/done/validated")
	List<CommissionToPayDTO> doneValidatedCommissions(CommissionRequest<CommissionReconciliationGroupDTO> validatedCommissionsToDone);

}
