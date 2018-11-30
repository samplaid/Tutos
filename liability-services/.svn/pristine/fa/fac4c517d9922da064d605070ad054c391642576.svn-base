package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.AdminFeeChangeDTO;
import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;

public interface BrokerChangeService {

	BrokerChangeDTO initBrokerChange(AmendmentRequest beneficiaryChangeRequest);

	BrokerChangeDTO updateBrokerChange(BrokerChangeDTO brokerChange);

	BrokerChangeDTO applyChangeToPolicy(BrokerChangeDTO brokerChange);

	BrokerChangeDTO applyChangeToAdminFees(BrokerChangeDTO brokerChange);

	Collection<AdminFeeChangeDTO> getAdminFeeChanges(BrokerChangeDTO brokerChange);

	BrokerChangeDTO getBrokerChange(Integer workflowItemId);
	
	BrokerChangeDTO getBrokerChangeBefore(Integer workflowItemId);

	Boolean hasAdminFeesChanged(BrokerChangeDTO brokerChange);
}
