/**
 * 
 */
package lu.wealins.liability.services.core.validation;

import java.util.List;

import lu.wealins.common.dto.liability.services.AgentDTO;

/**
 * This interface aims to provide functionality and rule to validate an agent.
 * 
 * @author oro
 *
 */
public interface AgentValidationService {

	/**
	 * Validates the agent to have only one active contact with CPS function if the centralized communication is checked.
	 * 
	 * @param agent the agent
	 * @return Empty if the condition is not met.
	 */
	List<String> validateOnlyOneActiveCpsContact(AgentDTO agent);
}
