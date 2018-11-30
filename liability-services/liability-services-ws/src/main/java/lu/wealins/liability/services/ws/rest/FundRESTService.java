package lu.wealins.liability.services.ws.rest;

import java.text.ParseException;
import java.util.Collection;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.FundSearchRequest;
import lu.wealins.common.dto.liability.services.FundSearcherRequest;
import lu.wealins.common.dto.liability.services.FundValuationDTO;
import lu.wealins.common.dto.liability.services.FundValuationRequest;
import lu.wealins.common.dto.liability.services.FundValuationSearchRequest;
import lu.wealins.common.dto.liability.services.SearchResult;

/**
 * CurrencyRESTService is a REST service responsible to manipulate Currency objects.
 *
 */
@Path("fund")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface FundRESTService {

	/**
	 * Get the fund according its id.
	 * 
	 * @param context The security context.
	 * @param id The fund id.
	 * @return The fund. Return a <code>ObjectNotFoundException</code> if the fund does not exist.
	 */
	@GET
	@Path("one")
	FundDTO getFund(@Context SecurityContext context, @QueryParam("id") String id);

	@GET
	@Path("/")
	Collection<FundLiteDTO> getFunds(@Context SecurityContext context, @QueryParam("ids") List<String> ids);

	@GET
	@Path("/agent/")
	Collection<String> getFundIds(@Context SecurityContext context, @QueryParam("agentId") String agentId);

	/**
	 * Update an existing fund with the WSSUPDFDS soap service. The principal in the security context will be used as the update user of the fund.
	 * 
	 * Fund to be updated must exist in the database, otherwise the method will throw an exception. All the values of the fund will be replaced with the data in the DTO parameter.
	 * 
	 * @param context The security context.
	 * @param fund The fund.
	 * @return The updated fund.
	 */
	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	FundDTO update(@Context SecurityContext context, FundDTO fund);

	/**
	 * Create a new fund with the WSSUPDFDS soap service. The principal in the security context will be used as the update user of the fund.
	 * 
	 * @param context The security context.
	 * @param fund The fund.
	 * @return The created fund.
	 */
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	FundDTO create(@Context SecurityContext context, FundDTO fund);

	/**
	 * Fund search service. The two search parameters : the fund type and the text filter are mandatory. The elements in the returned fund list will be in the same type.
	 * 
	 * The search request and returned page are one base indexed, the first page's number is one.
	 * 
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<FundLiteDTO> search(@Context SecurityContext context, FundSearchRequest request);

	@POST
	@Path("advanceSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<FundLiteDTO> search(@Context SecurityContext context, FundSearcherRequest fundSearcherRequest);

	@POST
	@Path("valuation")
	@Consumes(MediaType.APPLICATION_JSON)
	SearchResult<FundValuationDTO> getValuation(@Context SecurityContext context, FundValuationSearchRequest request) throws ParseException;

	@GET
	@Path("extract-lissia-fid")
	Response extractFidFund(@Context SecurityContext context);

	@POST
	@Path("update-lissia-fid-flag")
	Response updateLissiaFidFlag(@Context SecurityContext context, List<String> fids);

	@POST
	@Path("initValorization")
	@Consumes(MediaType.APPLICATION_JSON)
	boolean initValorization(@Context SecurityContext context, FundValuationRequest request);

	/**
	 * Valid if a fund can be saved/updated or not
	 * 
	 * @param context
	 * @param fund
	 * @return true or errors
	 */
	@POST
	@Path("validateFund")
	@Consumes(MediaType.APPLICATION_JSON)
	boolean validateFund(@Context SecurityContext context, FundDTO fund);

	@POST
	@Path("validateFunds")
	@Consumes(MediaType.APPLICATION_JSON)
	boolean validateFunds(@Context SecurityContext context, List<String> fdsIds);

	/**
	 * Validate if the funds are active otherwise it returns the error messages with the non-active funds.
	 * 
	 * @param context The security context.
	 * @param fundIds The fund ids.
	 * @return list of errors.
	 */
	@GET
	@Path("validateActiveFunds")
	List<String> validateActiveFunds(@Context SecurityContext context, @QueryParam("fundIds") List<String> fundIds);

	@POST
	@Path("performFundValuation")
	@Consumes(MediaType.APPLICATION_JSON)
	boolean performFundValuation(@Context SecurityContext context, FundValuationRequest request);
	
	/**
	 * Returns : <br>
	 * <b>Null</b>: if the one of the parameter is null or empty or a fund price is not found. <br>
	 * <b>false</b>: if a fund price is found but its valuation date is equal to the date passed in parameter. <br>
	 * <b>true</b>: otherwise.
	 * 
	 * @param fdsId the fund id
	 * @param date the payment date
	 * @param priceType the type of price to be added.
	 * @return a boolean value or null.
	 */
	@GET
	@Path("canAddFIDorFASFundValuationAmount")
	Boolean canAddFIDorFASFundValuationAmount(@Context SecurityContext context, @QueryParam("fdsId") String fdsId, @QueryParam("valuationDate") String valuationDate, @QueryParam("priceType") short priceType);

	@GET
	@Path("/investedFunds")
	Collection<FundLiteDTO> getInvestedFunds(@Context SecurityContext context, @QueryParam("policyId") String policyId);

}
