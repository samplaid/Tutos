package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.common.dto.webia.services.ClientFormDTO;

public interface ClientFormService {

	/**
	 * Return true if the client has a relation with the given type.
	 * 
	 * @param formId The form id.
	 * @param clientId The client id.
	 * @param relationTYpe The relation type.
	 * @return True, if successful.
	 */
	Boolean hasRelation(Integer formId, Integer clientId, ClientRelationType relationTYpe);

	/**
	 * Get the other clients. Exclude clients having a type given in the application parameter with the code EXEMPT_CPR_TYPE.
	 * 
	 * @param clientForms The client forms.
	 * @return The other clients.
	 */
	Collection<ClientFormDTO> getOtherClients(Collection<ClientFormEntity> clientForms);

	Collection<ClientFormEntity> updateOtherClients(Collection<ClientFormDTO> otherClients, Integer formId);

	void deleteWithFormId(Integer formId);
}
