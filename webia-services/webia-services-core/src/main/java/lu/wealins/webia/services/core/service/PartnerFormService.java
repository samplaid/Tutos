package lu.wealins.webia.services.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormSearchRequest;
import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;

public interface PartnerFormService {

	Collection<PartnerFormEntity> update(Collection<PartnerFormDTO> partnerForms, Integer formId);

	PartnerFormEntity update(PartnerFormDTO partner, Integer formId);

	void delete(Collection<Integer> ids);

	Collection<PartnerFormEntity> getPartnerForms(Integer formId);

	PageResult<PartnerFormDTO> search(PartnerFormSearchRequest partnerFormSearchRequest);

	boolean isWealinsBroker(PartnerFormDTO broker);
}
