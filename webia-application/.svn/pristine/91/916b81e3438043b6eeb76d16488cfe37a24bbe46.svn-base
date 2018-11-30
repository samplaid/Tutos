package lu.wealins.webia.core.service;

import java.util.List;
import java.util.Map;

import lu.wealins.common.dto.webia.services.CheckDataContainerDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;

public interface WebiaCheckDataService {

	/**
	 * Get the check data collection according to the workflow item id and their check code.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param checkCodes The check codes.
	 * @return The check data caollection.
	 */
	Map<String, CheckDataDTO> getCheckData(Integer workflowItemId, List<String> checkCodes);

	CheckDataDTO getCheckData(Integer workflowItemId, String checkCode);

	/**
	 * Get the check data collection according to the policy id and their check code.
	 * 
	 * @param policyId The policy id.
	 * @param checkCodes The check codes.
	 * @return The check data collection.
	 */
	CheckDataDTO getCheckData(String policyId, Integer checkId);

	/**
	 * Update the check data collection.
	 * 
	 * @param checkData The check data collection.
	 * @return The updated check data collection.
	 */
	CheckDataContainerDTO update(CheckDataContainerDTO checkData);
}
