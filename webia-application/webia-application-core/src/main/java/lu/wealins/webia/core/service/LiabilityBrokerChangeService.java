package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.AdminFeeChangeDTO;
import lu.wealins.common.dto.liability.services.AmendmentRequest;
import lu.wealins.common.dto.liability.services.BrokerChangeDTO;

public interface LiabilityBrokerChangeService {

	BrokerChangeDTO initBrokerChange(AmendmentRequest request);

	BrokerChangeDTO applyChange(BrokerChangeDTO brokerChange);

	BrokerChangeDTO applyChangeToAdminFees(BrokerChangeDTO brokerChange);

	Collection<AdminFeeChangeDTO> getAdminFeeChanges(BrokerChangeDTO brokerChange);

	BrokerChangeDTO update(BrokerChangeDTO brokerChange);

	BrokerChangeDTO load(Integer workflowItemId);

	Boolean hasAdminFeeChanged(BrokerChangeDTO brokerChange);
}
