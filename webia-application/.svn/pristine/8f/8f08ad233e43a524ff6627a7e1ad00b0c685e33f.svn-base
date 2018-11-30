/**
 * 
 */
package lu.wealins.webia.ws.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.CommissionReconciliationDTO;
import lu.wealins.common.dto.webia.services.CommissionReconciliationGroupDTO;
import lu.wealins.common.dto.webia.services.CommissionRequest;
import lu.wealins.common.dto.webia.services.CommissionToPayDTO;

/**
 * This interface exposes the functionalities of commission to pay.
 * 
 * @author skg
 *
 */
@Path("commission/reconciliation")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface WebiaCommissionReconciliationRESTService {

	/**
	 * Search all reconcilable commissions by type. The PORTFOLIO commission type is mapped to the set of values ADM, SWITCH, SURR, OPCVM, PRADM. <br>
	 * <br>
	 * The method returns a pageable commissions.
	 * 
	 * @param context the security context
	 * @param commissionType the commission type
	 * @param page the page number
	 * @param size the size limit.
	 * @return the pageable commissions
	 */
	@Path("search")
	@GET
	PageResult<CommissionReconciliationDTO> searchCommissionReconciliations(@Context SecurityContext context, @QueryParam("type") String commissionType, @QueryParam("text") String text,
			@QueryParam("page") int page,
			@QueryParam("size") int size);

	/**
	 * This method returns the reconciliation of {@code WEBIA} commissions and {@code SAP} commissions grouped by {@code agent code}, {@code agent name}
	 * 
	 * @param commissionType the commission type
	 * @param page the page number
	 * @param size the page size.
	 * @return a pageable commission group.
	 */
	@Path("searchGroup")
	@GET
	PageResult<CommissionReconciliationGroupDTO> searchCommissionReconciliationGroup(@Context SecurityContext context, @QueryParam("type") String commissionType, @QueryParam("text") String text,
			@QueryParam("page") int page,
			@QueryParam("size") int size);

	/**
	 * Validate the list of reconciled commissions. <br>
	 * The method returns a list of updated commissions.
	 * 
	 * @param context the security context
	 * @param reconciledCommissionsToValidate the list of commissions to validate
	 * @return the updated commissions
	 */
	@Path("validate/reconciled")
	@POST
	List<CommissionToPayDTO> validateReconciledCommissions(@Context SecurityContext context, CommissionRequest<CommissionReconciliationGroupDTO> reconciledCommissionsToValidate);

	/**
	 * Update the status to done for the list of validated commissions. <br>
	 * The method returns a list of updated commissions.
	 * 
	 * @param context the security context
	 * @param validatedCommissionsToDone the list of commissions to done
	 * @return the updated commissions
	 */
	@Path("done/validated")
	@POST
	List<CommissionToPayDTO> doneValidatedCommissions(@Context SecurityContext context, CommissionRequest<CommissionReconciliationGroupDTO> validateedCommissionsToDone);
	 
}
