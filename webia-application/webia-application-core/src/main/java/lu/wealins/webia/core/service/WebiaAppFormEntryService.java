package lu.wealins.webia.core.service;

import java.util.List;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.AppFormEntryDTO;

public interface WebiaAppFormEntryService {

	PageResult<AppFormEntryDTO> getAppFormEntriesByPartner(int page, int size, String partnerId, String partnerCategory, List<String> status);
	
	PageResult<AppFormEntryDTO> getAppFormEntriesByPolicy(int page, int size, String policyId, List<String> status);

}
