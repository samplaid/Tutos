package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.webia.services.core.service.PartnerFormService;
import lu.wealins.webia.services.ws.rest.PartnerFormRESTService;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormSearchRequest;

@Component
public class PartnerFormRESTServiceImpl implements PartnerFormRESTService {

	@Autowired
	private PartnerFormService partnerFormService;

	@Override
	public PageResult<PartnerFormDTO> search(SecurityContext context, PartnerFormSearchRequest partnerFormSearchRequest) {
		return partnerFormService.search(partnerFormSearchRequest);
	}
}
