package lu.wealins.webia.services.ws.rest;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;

/**
 * BenefClauseStdRESTService is a REST service responsible to manipulate BenefClauseStd objects.
 * 
 * @param <T>
 *
 */
@Path("benefClauseStd")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface BenefClauseStdRESTService {

	/**
	 * Get all the standard beneficiaries clauses available
	 * 
	 * @param context The security context
	 * @param productCd
	 * @return the collection of standard clauses
	 */
	@GET
	@Path("{productCd}")
	Collection<BenefClauseStdDTO> getUniqueByProductCd(@Context SecurityContext context, @PathParam("productCd") String productCd);

	/**
	 * Get all the standard beneficiaries clauses available for a given product in a given language
	 * 
	 * @param context The security context
	 * @param productCd
	 * @param langCd
	 * @return the collection of standard clauses
	 */
	@GET
	@Path("{productCd}/{langCd}")
	Collection<BenefClauseStdDTO> getByProductCdAndLangCd(@Context SecurityContext context, @PathParam("productCd") String productCd, @PathParam("langCd") String langCd);

	/**
	 * Get all the standard beneficiaries clauses available for a given id
	 * 
	 * @param context The security context
	 * @param productCd
	 * @param langCd
	 * @return the collection of standard clauses
	 */
	@GET
	@Path("byId/{benefClauseCd}")
	Collection<BenefClauseStdDTO> getByBenefClauseCd(@Context SecurityContext context, @PathParam("benefClauseCd") String benefClauseCd);

}
