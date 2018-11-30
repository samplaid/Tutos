package lu.wealins.webia.services.core.service;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.webia.services.AppFormEntryDTO;
import lu.wealins.common.dto.webia.services.PageResult;

public interface AppFormEntryService {

	/**
	 * Get app form entries.
	 * 
	 * @param page The page number.
	 * @param size Elements by page.
	 * @param fundId The fund id.
	 * @param clientId The client id.
	 * @param excludedPolicies The excluded policies.
	 * @param status The status.
	 * @return The app form entries.
	 */
	PageResult<AppFormEntryDTO> getAppFormEntries(int page, int size, String fundId, Integer clientId, List<String> excludedPolicies, List<String> status);

	/**
	 * Get app form entries.
	 * 
	 * @param page The page number.
	 * @param size Elements by page.
	 * @param partnerId The partner id.
	 * @param partnerCategory The partner form category.
	 * @param fundIds The fund ids.
	 * @param status The app form status.
	 * @return The app form entries.
	 */
	PageResult<AppFormEntryDTO> getAppFormEntries(int page, int size, String partnerId, String partnerCategory, Collection<String> fundIds, List<String> status);
	
	/**
	 * Get app form entries for a given policy.
	 * 
	 * @param page The page number.
	 * @param size Elements by page.
	 * @param policyId The policy id.
	 * @param status The app form status.
	 * @return The app form entries.
	 */
	PageResult<AppFormEntryDTO> getAppFormEntries(int page, int size, String policyId, List<String> status);
}
