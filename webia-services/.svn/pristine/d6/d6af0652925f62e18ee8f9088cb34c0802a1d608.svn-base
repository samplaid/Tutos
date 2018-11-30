package lu.wealins.webia.services.ws.rest.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.OperationDTO;
import lu.wealins.webia.services.core.service.OperationService;
import lu.wealins.webia.services.ws.rest.OperationRESTService;

@Component
public class OperationRESTServiceImpl implements OperationRESTService {

	@Autowired
	private OperationService operationService;

	@Override
	public Collection<OperationDTO> getOpenedOperations(SecurityContext context, List<String> policyIds){	
		return operationService.getOpenedOperations(policyIds);
	}

}