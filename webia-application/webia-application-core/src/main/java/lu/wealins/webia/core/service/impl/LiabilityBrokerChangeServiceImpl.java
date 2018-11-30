package lu.wealins.webia.core.service.impl;

import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.AdminFeeChangeDTO;
import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.webia.core.service.LiabilityBrokerChangeService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class LiabilityBrokerChangeServiceImpl implements LiabilityBrokerChangeService {

	@Autowired
	private RestClientUtils restClientUtils;

	private static final String LIABILITY_BROKER_CHANGE = "liability/brokerChange/";
	private static final String APPLY_CHANGE = "applyChange";
	private static final String APPLY_CHANGE_TO_ADMIN_FEES = "applyChangeToAdminFees";

	private static final String UPDATE = "update";
	private static final String LOAD = "load";
	private static final String INIT = "initBrokerChange";
	private static final String ADM_CHANGED = "adminFeesChanged";
	private static final String ADMIN_FEE_CHANGES = "adminFeeChanges";

	@Override
	public BrokerChangeDTO initBrokerChange(AmendmentRequest request) {
		return restClientUtils.post(LIABILITY_BROKER_CHANGE + INIT, request, BrokerChangeDTO.class);
	}

	@Override
	public BrokerChangeDTO applyChange(BrokerChangeDTO brokerChange) {
		return restClientUtils.post(LIABILITY_BROKER_CHANGE + APPLY_CHANGE, brokerChange, BrokerChangeDTO.class);
	}

	@Override
	public BrokerChangeDTO applyChangeToAdminFees(BrokerChangeDTO brokerChange) {
		return restClientUtils.post(LIABILITY_BROKER_CHANGE + APPLY_CHANGE_TO_ADMIN_FEES, brokerChange, BrokerChangeDTO.class);
	}

	@Override
	public Collection<AdminFeeChangeDTO> getAdminFeeChanges(BrokerChangeDTO brokerChange) {
		return restClientUtils.post(LIABILITY_BROKER_CHANGE + ADMIN_FEE_CHANGES, brokerChange, new GenericType<Collection<AdminFeeChangeDTO>>() {
		});
	}

	@Override
	public BrokerChangeDTO update(BrokerChangeDTO brokerChange) {
		return restClientUtils.post(LIABILITY_BROKER_CHANGE + UPDATE, brokerChange, BrokerChangeDTO.class);
	}

	@Override
	public BrokerChangeDTO load(Integer workflowItemId) {
		MultivaluedMap<String, Object> params = new MultivaluedHashMap<String, Object>();

		params.add("workflowItemId", workflowItemId);
		return restClientUtils.get(LIABILITY_BROKER_CHANGE, LOAD, params, BrokerChangeDTO.class);
	}

	@Override
	public Boolean hasAdminFeeChanged(BrokerChangeDTO brokerChange) {
		return restClientUtils.post(LIABILITY_BROKER_CHANGE + ADM_CHANGED, brokerChange, Boolean.class);
	}

}
