package lu.wealins.liability.services.ws.rest.impl;

import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.AdminFeeChangeDTO;
import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;
import lu.wealins.liability.services.core.business.BrokerChangeService;
import lu.wealins.liability.services.ws.rest.BrokerChangeRESTService;

@Component
public class BrokerChangeRESTServiceImpl implements BrokerChangeRESTService {

	@Autowired
	private BrokerChangeService brokerChangeService;

	@Override
	public BrokerChangeDTO initBrokerChange(SecurityContext context, AmendmentRequest brokerChangeRequest) {
		return brokerChangeService.initBrokerChange(brokerChangeRequest);
	}

	@Override
	public BrokerChangeDTO updateBrokerChange(SecurityContext context, BrokerChangeDTO brokerChange) {
		return brokerChangeService.updateBrokerChange(brokerChange);
	}

	@Override
	public BrokerChangeDTO applyChangeToPolicy(SecurityContext context, BrokerChangeDTO brokerChange) {
		return brokerChangeService.applyChangeToPolicy(brokerChange);
	}

	@Override
	public BrokerChangeDTO applyChangeToAdminFees(SecurityContext context, BrokerChangeDTO brokerChange) {
		return brokerChangeService.applyChangeToAdminFees(brokerChange);
	}

	@Override
	public Collection<AdminFeeChangeDTO> getAdminFeeChanges(SecurityContext context, BrokerChangeDTO brokerChange) {
		return brokerChangeService.getAdminFeeChanges(brokerChange);
	}

	@Override
	public BrokerChangeDTO getBrokerChange(SecurityContext context, Integer workflowItemId) {
		return brokerChangeService.getBrokerChange(workflowItemId);
	}

	@Override
	public BrokerChangeDTO getBrokerChangeBefore(SecurityContext context, Integer workflowItemId) {
		return brokerChangeService.getBrokerChangeBefore(workflowItemId);
	}
	
	@Override
	public Boolean hasAdminFeesChanged(SecurityContext context, BrokerChangeDTO brokerChangeDTO) {
		return brokerChangeService.hasAdminFeesChanged(brokerChangeDTO);
	}
}
