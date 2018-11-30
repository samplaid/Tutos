package lu.wealins.webia.services.ws.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveSapAccountingResponse;

/**
 * Mathematical reserve service
 * 
 * @param <T>
 *
 */
@Path("mathematicalReserve")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("rest-user")
public interface MathematicalReserveRESTService {

	/**
	 * save mathematical reserve
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Path("/save")
	public SaveMathematicalReserveResponse save(@Context SecurityContext context, SaveMathematicalReserveRequest request);
	
	/**
	 * get mathematical reserve 
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Path("/getByModeAndDate")
	public GetMathematicalReserveResponse getMathematicalReserveByModeDate(@Context SecurityContext context, GetMathematicalReserveRequest request);

	
	/**
	 * get mathematical reserve 
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Path("/saveSapAccounting")
	SaveSapAccountingResponse saveSapAccounting(@Context SecurityContext context, SaveSapAccountingRequest request);


	/**
	 * delete mathematical reserve 
	 * @param context
	 * @param request
	 * @return
	 */
	@POST
	@Path("/deleteMathematicalReserve")
	DeleteMathematicalReserveResponse deleteMathematicalReserve(@Context SecurityContext context, DeleteMathematicalReserveRequest request);
	
	
	
}
