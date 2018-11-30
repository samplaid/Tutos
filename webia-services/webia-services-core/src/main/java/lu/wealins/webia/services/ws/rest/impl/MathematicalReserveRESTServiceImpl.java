package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.DeleteMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.GetMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveRequest;
import lu.wealins.common.dto.webia.services.SaveMathematicalReserveResponse;
import lu.wealins.common.dto.webia.services.SaveSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveSapAccountingResponse;
import lu.wealins.webia.services.core.service.MathematicalReserveService;
import lu.wealins.webia.services.ws.rest.MathematicalReserveRESTService;

@Component
public class MathematicalReserveRESTServiceImpl implements MathematicalReserveRESTService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MathematicalReserveRESTServiceImpl.class);

	@Autowired
	private MathematicalReserveService mathematicalReserveService;


	@Override
	public SaveMathematicalReserveResponse save(SecurityContext context, SaveMathematicalReserveRequest request) {
		return mathematicalReserveService.saveMathematicalReserve(request);
	}


	@Override
	public GetMathematicalReserveResponse getMathematicalReserveByModeDate(SecurityContext context, GetMathematicalReserveRequest request) {
		return mathematicalReserveService.getMathematicalReserve(request);
	}
	
	@Override
	public SaveSapAccountingResponse saveSapAccounting(SecurityContext context, SaveSapAccountingRequest request) {
		return mathematicalReserveService.saveSapAccounting(request);
	}


	@Override
	public DeleteMathematicalReserveResponse deleteMathematicalReserve(SecurityContext context, DeleteMathematicalReserveRequest request) {
		return mathematicalReserveService.deleteByModeAndDate(request);
	}

	

	
}
