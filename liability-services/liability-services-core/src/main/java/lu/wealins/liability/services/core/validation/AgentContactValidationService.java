package lu.wealins.liability.services.core.validation;

import java.util.Collection;

import lu.wealins.common.dto.liability.services.AgentContactBasicDTO;

/**
 * This class provides the methods that are used to validation an agent contact.
 * 
 * @author oro
 *
 */
public interface AgentContactValidationService {

	/**
	 * Validates the rules that are linked to the contacts functions.
	 * 
	 * @param agentContacts list of contact.
	 * @param agentOwnerId THe agent contact owner id.
	 */
	void validateContactFunction(String agentOwnerId, Collection<? extends AgentContactBasicDTO> contacts);
}
