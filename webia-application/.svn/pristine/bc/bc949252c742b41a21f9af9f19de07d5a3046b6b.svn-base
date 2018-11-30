package lu.wealins.webia.ws.rest.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.OperationDTO;
import lu.wealins.common.dto.webia.services.enums.OperationStatus;
import lu.wealins.webia.core.service.OperationService;
import lu.wealins.webia.ws.rest.OperationRESTService;

@Component
public class OperationRESTServiceImpl implements OperationRESTService {

	@Autowired
	private OperationService operationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.OperationRESTService#getPartnerOpeningOperations(javax.ws.rs.core.SecurityContext, int, int, java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<OperationDTO> getPartnerOpeningOperations(SecurityContext context, String partnerId, String partnerCategory) {
		List<String> status = Arrays.asList(OperationStatus.CREATED.name(), OperationStatus.IN_FORCE.name(), OperationStatus.DRAFT.name(), OperationStatus.NEW.name());
		return operationService.getPartnerOperations(partnerId, partnerCategory, status);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.ws.rest.OperationRESTService#getPolicyOpeningOperations(javax.ws.rs.core.SecurityContext, int, int, java.lang.String)
	 */
	@Override
	public Collection<OperationDTO> getPolicyOpeningOperations(SecurityContext context, String policyId) {
		List<String> status = Arrays.asList(OperationStatus.CREATED.name(), OperationStatus.IN_FORCE.name(), OperationStatus.DRAFT.name(), OperationStatus.NEW.name());
		return operationService.getPolicyOperations(policyId, status);
	}	

}
