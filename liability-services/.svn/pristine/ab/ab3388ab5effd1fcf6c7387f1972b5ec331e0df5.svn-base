package lu.wealins.liability.services.core.business;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.PolicyEndorsementDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyEndorsementType;
import lu.wealins.common.dto.liability.services.enums.PolicyEventType;
import lu.wealins.liability.services.core.persistence.entity.PolicyEndorsementEntity;

public interface PolicyEndorsementService {

	Collection<PolicyEndorsementEntity> createOrUpdatePolicyEndorsement(long pevId, String polId, PolicyEndorsementType type, String valueBefore, String valueAfter, String workflowItemId,
			Integer... coverages);

	String getValueAfter(PolicyEventType eventType, String workflowItemId, Integer... coverages);

	String getValueBefore(PolicyEventType eventType, String workflowItemId, Integer... coverages);

	Collection<PolicyEndorsementDTO> getPolicyEndorsements(PolicyEventType eventType, String workflowItemId, Integer... coverages);

	Collection<PolicyEndorsementDTO> getChangedPolicyEndorsements(PolicyEventType eventType, String workflowItemId, Integer... coverages);
}
