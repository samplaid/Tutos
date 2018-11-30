package lu.wealins.webia.services.ws.rest.impl;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.FindSapEncashmentsResponse;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingRequest;
import lu.wealins.common.dto.webia.services.SaveEncashmentsInSapAccountingResponse;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsRequest;
import lu.wealins.common.dto.webia.services.UpdateEncashmentsResponse;
import lu.wealins.webia.services.core.service.EncashmentFundFormService;
import lu.wealins.webia.services.ws.rest.EncashmentRESTService;

@Component
public class EncashmentRESTServiceImpl implements EncashmentRESTService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(EncashmentRESTServiceImpl.class);

	/**
	 * The extract service
	 */
	@Autowired
	private EncashmentFundFormService encashmentFundFormService;

	@Override
	public FindSapEncashmentsResponse getSapEncashments(SecurityContext context) {
		return encashmentFundFormService.findSapEncashments();
	}

	@Override
	public SaveEncashmentsInSapAccountingResponse saveEncashmentsInSAPAccounting(SecurityContext context, SaveEncashmentsInSapAccountingRequest request) {
		return encashmentFundFormService.saveInSapAccounting(request);
	}

	@Override
	public UpdateEncashmentsResponse updateEncashments(SecurityContext context, UpdateEncashmentsRequest request) {
		return encashmentFundFormService.updateEncashments(request);
	}

}
