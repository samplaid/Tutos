package lu.wealins.webia.core.service.impl;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.AppFormEntryDTO;
import lu.wealins.common.dto.webia.services.AppFormEntryRequest;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.webia.core.service.LiabilityFundService;
import lu.wealins.webia.core.service.WebiaAppFormEntryService;
import lu.wealins.webia.core.utils.RestClientUtils;

@Service
public class WebiaAppFormEntryServiceImpl implements WebiaAppFormEntryService {

	private static final String WEBIA_APP_FORM_ENTRY = "webia/appFormEntry/";
	private static final String APP_FORM_FOR_PARTNER = "appFormEntriesForPartner";
	private static final String APP_FORM_FOR_BROKER = "appFormEntriesForBroker";
	private static final String APP_FORM_FOR_POLICY = "appFormEntriesForPolicy";

	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private LiabilityFundService fundService;

	@Override
	public PageResult<AppFormEntryDTO> getAppFormEntriesByPartner(int page, int size, String partnerId, String partnerCategory, List<String> status) {

		if (AgentCategory.DEPOSIT_BANK.getCategory().equals(partnerCategory) || AgentCategory.ASSET_MANAGER.getCategory().equals(partnerCategory)) {
			Collection<String> fundIds = fundService.getFunds(partnerId);

			AppFormEntryRequest request = createAppFormEntryRequest(page, size, fundIds, status);

			return restClientUtils.post(WEBIA_APP_FORM_ENTRY + APP_FORM_FOR_PARTNER, request, new GenericType<PageResult<AppFormEntryDTO>>() {
			});
		} else if (AgentCategory.BROKER.getCategory().equals(partnerCategory)) {
			MultivaluedMap<String, Object> queryParams = initMultivaluedMap(page, size, status);
			queryParams.add("partnerId", partnerId);
			queryParams.add("partnerCategory", partnerCategory);

			return restClientUtils.get(WEBIA_APP_FORM_ENTRY, APP_FORM_FOR_BROKER, queryParams, new GenericType<PageResult<AppFormEntryDTO>>() {
			});
		}

		throw new IllegalArgumentException("Partner category " + partnerCategory + "is not managed.");
	}
	
	@Override
	public PageResult<AppFormEntryDTO> getAppFormEntriesByPolicy(int page, int size, String policyId, List<String> status) {

			MultivaluedMap<String, Object> queryParams = initMultivaluedMap(page, size, status);
			queryParams.add("policyId", policyId);

			return restClientUtils.get(WEBIA_APP_FORM_ENTRY, APP_FORM_FOR_POLICY, queryParams, new GenericType<PageResult<AppFormEntryDTO>>() {
			});
	}

	private MultivaluedMap<String, Object> initMultivaluedMap(int page, int size, List<String> status) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.add("page", page);
		queryParams.add("size", size);
		status.forEach(x -> queryParams.add("status", x));
		return queryParams;
	}	
	

	private AppFormEntryRequest createAppFormEntryRequest(int page, int size, Collection<String> fundIds, List<String> status) {
		AppFormEntryRequest request = new AppFormEntryRequest();

		request.setPage(page);
		request.setSize(size);
		request.setFundIds(fundIds);
		request.setStatus(status);

		return request;
	}

}
