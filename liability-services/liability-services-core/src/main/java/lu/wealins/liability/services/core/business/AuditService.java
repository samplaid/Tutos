package lu.wealins.liability.services.core.business;

import lu.wealins.common.dto.liability.services.WorkflowTriggerActionDTO;
import uk.co.liss.webservice.core.domain.xsd.AuditData;

public interface AuditService {

	/**
	 * Submit a trigger action for a workflow item.
	 * 
	 * @param workflowTriggerAction The trigger action.
	 * @param context The security context.
	 */
	void submit(WorkflowTriggerActionDTO workflowTriggerAction);

	/**
	 * Create audit data.
	 * 
	 * @param usrId The user id.
	 * 
	 * @return The audit data.
	 */
	public AuditData createAuditData(String usrId);
}
