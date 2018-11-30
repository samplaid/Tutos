/**
 * 
 */
package lu.wealins.webia.services.core.service.validations.appform;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.common.dto.webia.services.ClientFormDTO;

public interface OtherClientRelationshipValidationService {

	/**
	 * Validate all client cession relationships parts are not null
	 * 
	 * @param otherClients the clients
	 * @param errors Empty if the condition is met.
	 */
	void assertClientCessionPartsNotNull(Collection<ClientFormDTO> otherClients, List<String> errors);

	/**
	 * Validate the client cession relationships part is not null
	 * 
	 * @param otherClient the client
	 * @param errors Empty if the condition is met.
	 */
	void assertClientCessionPartNotNull(ClientFormDTO otherClient, List<String> errors);

	/**
	 * Validate all client cession relationships part equal to 100.
	 * 
	 * @param otherClients the clients
	 * @param errors Empty if the condition is met.
	 */
	void assertClientCessionPartsEq100(Collection<ClientFormDTO> otherClients, List<String> errors);

	/**
	 * Validate the client cession relationship part equals to 100.
	 * 
	 * @param otherClient the client
	 * @param errors Empty if the condition is met.
	 */
	void assertClientCessionPartEq100(ClientFormDTO otherClient, List<String> errors);

	void assertClientCessionPartsNotNullForRole(Collection<ClientFormDTO> otherClients, ClientRelationType roleType, List<String> errors);

}
