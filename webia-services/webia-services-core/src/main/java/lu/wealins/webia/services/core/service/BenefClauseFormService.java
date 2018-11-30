package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.webia.services.core.persistence.entity.BenefClauseFormEntity;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;

public interface BenefClauseFormService {

	Collection<BenefClauseFormEntity> update(Collection<BenefClauseFormDTO> benefClauseForms, Integer formId);

	Collection<BenefClauseFormEntity> getLifeBenefClauseForms(Integer formId);

	Collection<BenefClauseFormEntity> getDeathBenefClauseForms(Integer formId);

	void delete(Collection<Integer> ids);
}
