package lu.wealins.webia.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AgentDataForTransfersResponse;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;
import lu.wealins.common.dto.liability.services.BrokerProcessDTO;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityCommissionServiceImpl implements LiabilityCommissionService {

	private static final String GET_BROKER_INFO_DESIRE_REPORT = "liability/payment-commission/extract-agent-data-transfers";

	@Autowired
	private RestClientUtils restClientUtils;

	@Override
	public AgentDataForTransfersResponse getInfoAvailableBrokerCommission(List<BrokerProcessDTO> availableBroker, ApplicationParameterDTO wealinsBroker) {
		return restClientUtils.post(GET_BROKER_INFO_DESIRE_REPORT+ "/" + wealinsBroker.getValue(), availableBroker, AgentDataForTransfersResponse.class);
	}

}
