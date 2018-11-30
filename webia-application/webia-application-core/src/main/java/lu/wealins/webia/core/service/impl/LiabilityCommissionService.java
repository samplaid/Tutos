package lu.wealins.webia.core.service.impl;

import java.util.List;

import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;

public interface LiabilityCommissionService {

	/**
	 * Get Information Available Broker Commission
	 * @param availableBroker 
	 * @param wealinsBroker 
	 * 
	 * @return AgentDataForTransfersResponse
	 * @throws Exception 
	 */
	AgentDataForTransfersResponse getInfoAvailableBrokerCommission(List<BrokerProcessDTO> availableBroker, ApplicationParameterDTO wealinsBroker);
}
