package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.enums.ClauseFormType;
import lu.wealins.webia.services.core.mapper.BenefClauseFormMapper;
import lu.wealins.webia.services.core.persistence.entity.BenefClauseFormEntity;
import lu.wealins.webia.services.core.persistence.repository.BenefClauseFormRepository;
import lu.wealins.webia.services.core.service.BenefClauseFormService;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;

@Service
public class BenefClauseFormServiceImpl implements BenefClauseFormService {

	private static final String FORM_ID_CANNOT_BE_NULL = "Form id cannot be null";
	private static final String BENEF_CLAUSE_FORM_CANNOT_BE_NULL = "Benef clause form cannot be null.";

	@Autowired
	private BenefClauseFormRepository benefClauseFormRepository;

	@Autowired
	private BenefClauseFormMapper benefClauseFormMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseFormService#update(java.util.Collection, java.lang.Integer)
	 */
	@Override
	public Collection<BenefClauseFormEntity> update(Collection<BenefClauseFormDTO> benefClauseForms, Integer formId) {
		Collection<BenefClauseFormEntity> updatedBenefClauseForms = new ArrayList<>();
		for (BenefClauseFormDTO benefClauseForm : benefClauseForms) {
			benefClauseForm.setFormId(formId);
			updatedBenefClauseForms.add(update(benefClauseForm));
		}
		return updatedBenefClauseForms;
	}

	private BenefClauseFormEntity update(BenefClauseFormDTO benefClauseForm) {
		Assert.notNull(benefClauseForm, BENEF_CLAUSE_FORM_CANNOT_BE_NULL);

		BenefClauseFormEntity partnerEntity = benefClauseFormMapper.asBenefClauseFormEntity(benefClauseForm);

		return benefClauseFormRepository.save(partnerEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseFormService#getLifeBenefClauseForms(java.lang.Integer)
	 */
	@Override
	public Collection<BenefClauseFormEntity> getLifeBenefClauseForms(Integer formId) {
		Assert.notNull(formId, FORM_ID_CANNOT_BE_NULL);

		return benefClauseFormRepository.findByFormIdAndClauseFormTp(formId, ClauseFormType.LIFE.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseFormService#getDeathBenefClauseForms(java.lang.Integer)
	 */
	@Override
	public Collection<BenefClauseFormEntity> getDeathBenefClauseForms(Integer formId) {
		Assert.notNull(formId, FORM_ID_CANNOT_BE_NULL);

		return benefClauseFormRepository.findByFormIdAndClauseFormTp(formId, ClauseFormType.DEATH.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.BenefClauseFormService#delete(java.util.Collection)
	 */
	@Override
	public void delete(Collection<Integer> ids) {
		for (Integer id : ids) {
			benefClauseFormRepository.delete(id);
		}
	}

}
