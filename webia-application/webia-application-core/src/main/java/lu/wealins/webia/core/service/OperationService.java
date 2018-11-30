package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.OperationDTO;

public interface OperationService {

	Collection<OperationDTO> getPartnerOperations(String partnerId, String partnerCategory, List<String> status);
	
	Collection<OperationDTO> getPolicyOperations(String policyId, List<String> status);

}
