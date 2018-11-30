package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;

public interface BeneficiaryService {

	/**
	 * Get life beneficiaries from client forms.
	 * 
	 * @param clientForms The client forms.
	 * @return The life beneficiaries.
	 */
	Collection<BeneficiaryFormDTO> getLifeBeneficiaries(Collection<ClientFormEntity> clientForms);

	/**
	 * Get death beneficiaries from client forms.
	 * 
	 * @param clientForms The client forms.
	 * @return The death beneficiaries.
	 */
	Collection<BeneficiaryFormDTO> getDeathBeneficiaries(Collection<ClientFormEntity> clientForms);

	Collection<ClientFormEntity> updateBeneficiaries(Collection<BeneficiaryFormDTO> beneficiaries, Integer formId);
}
