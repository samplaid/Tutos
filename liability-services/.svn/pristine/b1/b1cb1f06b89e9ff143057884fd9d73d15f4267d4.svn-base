package lu.wealins.liability.services.core.business;

import java.util.Date;

import lu.wealins.common.dto.liability.services.PolicyEventDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyEventType;

public interface PolicyEventService {

	PolicyEventDTO getPolicyEvent(PolicyEventType eventType, String workflowItemId);

	PolicyEventDTO createOrUpdatePolicyEvent(String polId, PolicyEventType eventType, Date effectiveDate, String valueBefore, String valueAfter, String workflowItemId, Integer... coverage);
}
