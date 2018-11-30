package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormSearchRequest;
import lu.wealins.webia.services.core.mapper.PartnerFormMapper;
import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;
import lu.wealins.webia.services.core.persistence.repository.PartnerFormRepository;
import lu.wealins.webia.services.core.persistence.specification.PartnerFormSpecifications;
import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.core.service.PartnerFormService;

@Service
public class PartnerFormServiceImpl implements PartnerFormService {

	private static final String FORM_ID_CANNOT_BE_NULL = "Form id cannot be null";
	private static final String PARTNER_CANNOT_BE_NULL = "Partner cannot be null.";
	private static final String WEALINS_BROKER_ID = "WEALINS_BROKER_ID";

	@Autowired
	private PartnerFormMapper partnerFormMapper;

	@Autowired
	private PartnerFormRepository partnerFormRepository;

	@Autowired
	private ApplicationParameterService applicationParameterService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.PartnerFormService#update(java.util.Collection, java.lang.Integer)
	 */
	@Override
	public Collection<PartnerFormEntity> update(Collection<PartnerFormDTO> partnerForms, Integer formId) {
		Collection<PartnerFormEntity> updatedPartnerForms = new ArrayList<>();
		for (PartnerFormDTO partnerForm : partnerForms) {
			updatedPartnerForms.add(update(partnerForm, formId));
		}
		return updatedPartnerForms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.PartnerFormService#update(lu.wealins.common.dto.webia.services.PartnerFormDTO, java.lang.Integer)
	 */
	@Override
	public PartnerFormEntity update(PartnerFormDTO partner, Integer formId) {
		Assert.notNull(partner, PARTNER_CANNOT_BE_NULL);

		partner.setFormId(formId);

		PartnerFormEntity partnerEntity = partnerFormMapper.asPartnerFormEntity(partner);
		return partnerFormRepository.save(partnerEntity);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.PartnerFormService#getPartnerForms(java.lang.Integer)
	 */
	@Override
	public Collection<PartnerFormEntity> getPartnerForms(Integer formId) {
		Assert.notNull(formId, FORM_ID_CANNOT_BE_NULL);

		return partnerFormRepository.findByFormId(formId);
	}

	@Override
	public PageResult<PartnerFormDTO> search(PartnerFormSearchRequest partnerFormSearchRequest) {

		Pageable pageable = new PageRequest(partnerFormSearchRequest.getPage(), partnerFormSearchRequest.getSize());
		PageResult<PartnerFormDTO> result = new PageResult<PartnerFormDTO>();

		Page<PartnerFormEntity> pageResult = partnerFormRepository.findAll(createSpecification(partnerFormSearchRequest), pageable);

		result.setSize(partnerFormSearchRequest.getSize());
		result.setTotalPages(pageResult.getTotalPages());
		result.setTotalRecordCount(pageResult.getTotalElements());
		result.setCurrentPage(pageResult.getNumber());
		result.setContent(partnerFormMapper.asPartnerFormDTOs(pageResult.getContent()).stream().collect(Collectors.toList()));

		return result;
	}

	private Specifications<PartnerFormEntity> createSpecification(PartnerFormSearchRequest criteria) {
		Specifications<PartnerFormEntity> specifs = Specifications.where(PartnerFormSpecifications.initial());

		if (criteria.getFormId() != null) {
			specifs = specifs.and(PartnerFormSpecifications.withFormId(criteria.getFormId()));
		}
		String partnerCategory = criteria.getPartnerCategory();
		if (StringUtils.isNotBlank(partnerCategory)) {
			if (!AgentCategory.getCategories().contains(partnerCategory)) {
				throw new IllegalStateException("Unknown partner category " + partnerCategory + ".");
			}
			specifs = specifs.and(PartnerFormSpecifications.withPartnerCategory(partnerCategory));
		}
		return specifs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.PartnerFormService#delete(java.util.Collection)
	 */
	@Override
	public void delete(Collection<Integer> ids) {
		for (Integer id : ids) {
			partnerFormRepository.delete(id);
		}
	}

	@Override
	public boolean isWealinsBroker(PartnerFormDTO broker) {
		String wealinsBrokerId = applicationParameterService.getStringValue(WEALINS_BROKER_ID);
		Assert.hasText(wealinsBrokerId, "Parameter setting error: Wealins broker id is not defined in database.");

		return broker != null && AgentCategory.BROKER.getCategory().equalsIgnoreCase(broker.getPartnerCategory()) && StringUtils.isNotBlank(broker.getPartnerId())
				&& wealinsBrokerId.trim().equalsIgnoreCase(broker.getPartnerId());
	}
}
