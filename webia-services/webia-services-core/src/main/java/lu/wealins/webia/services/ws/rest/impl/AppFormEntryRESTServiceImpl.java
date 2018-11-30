package lu.wealins.webia.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.AppFormEntryDTO;
import lu.wealins.common.dto.webia.services.AppFormEntryRequest;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.webia.services.core.service.AppFormEntryService;
import lu.wealins.webia.services.core.service.AppFormService;
import lu.wealins.webia.services.ws.rest.AppFormEntryRESTService;

@Component
public class AppFormEntryRESTServiceImpl implements AppFormEntryRESTService {

	@Autowired
	private AppFormEntryService appFormEntryService;

	@Autowired
	@Qualifier(value = "appFormService")
	private AppFormService appFormService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.ws.rest.AppFormEntryRESTService#getAppFormEntries(javax.ws.rs.core.SecurityContext, int, int, java.lang.String, java.lang.Integer, java.util.List, java.util.List)
	 */
	@Override
	public PageResult<AppFormEntryDTO> getAppFormEntries(SecurityContext context, int page, int size, String fundId, Integer clientId, List<String> excludedPolicies, List<String> status) {
		return appFormEntryService.getAppFormEntries(page, size, fundId, clientId, excludedPolicies, status);
	}

	@Override
	public PageResult<AppFormEntryDTO> getAppFormEntries(SecurityContext context, AppFormEntryRequest request) {
		return appFormEntryService.getAppFormEntries(request.getPage(), request.getSize(), request.getPartnerId(), request.getPartnerCategory(), request.getFundIds(), request.getStatus());
	}

	@Override
	public PageResult<AppFormEntryDTO> getAppFormEntriesForBroker(SecurityContext context, int page, int size, String partnerId, List<String> status) {
		return appFormEntryService.getAppFormEntries(page, size, partnerId, AgentCategory.BROKER.getCategory(), null, status);
	}
	
	@Override
	public PageResult<AppFormEntryDTO> getAppFormEntriesForPolicy(SecurityContext context, int page, int size, String policyId, List<String> status) {
		return appFormEntryService.getAppFormEntries(page, size, policyId, status);
	}

	@Override
	@Deprecated
	public AppFormDTO getAppForm(SecurityContext context, int id) {
		return appFormService.getAppForm(id);
	}

}
