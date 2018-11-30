package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.common.dto.webia.services.InsuredFormDTO;

public interface InsuredService {

	/**
	 * Get the insureds from the client forms.
	 * 
	 * @param clientForms The client forms.
	 * @return The insureds.
	 */
	Collection<InsuredFormDTO> getInsureds(Collection<ClientFormEntity> clientForms);

	Collection<ClientFormEntity> update(Collection<InsuredFormDTO> insureds, Integer formId);
}
