package lu.wealins.webia.services.ws.rest;

import java.text.ParseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.AccountingNavToInject;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;
import lu.wealins.common.dto.webia.services.GetAccountingNavRequest;
import lu.wealins.common.dto.webia.services.GetAccountingNavResponse;

import lu.wealins.common.dto.webia.services.AccountingNavToInject;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;

/**
 * ExtractRESTService is a REST service responsible to extract objects or data.
 * 
 * @param <T>
 *
 */

@Path("accountingNav")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface AccountingNavRESTService {

	/**
	 * Get an accounting nav function of the fund id and a date
	 * 
	 * @param context The security context.
	 * @return AccountingNav
	 */
	@POST
	@Path("/getAccountingNavByFundAndDate")
	public GetAccountingNavResponse getAccountingNavByFundAndDate(@Context SecurityContext context, GetAccountingNavRequest request);


	/**
	 * Get an accounting nav function of the fund id and a date
	 * 
	 * @param context The security context.
	 * @return AccountingNav
	 */
	@POST
	@Path("/getAccountingNavByIsinAndDate")
	public GetAccountingNavResponse getAccountingNavByIsinAndDate(@Context SecurityContext context, GetAccountingNavRequest request);

	/**
	 * Check accounting Nav.
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/checkCloturedVni")
	CloturedVniInjectionControlResponse checkAccountingNav(@Context SecurityContext context,
			CloturedVniInjectionControlRequest request) throws ParseException;

	/***
	 * Inject Accounting Nav.
	 * 
	 * @param context
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/injectCloturedVni")
	CloturedVniInjectionControlResponse injectAccountingNav(@Context SecurityContext context,
			AccountingNavToInject request) throws ParseException;

}
