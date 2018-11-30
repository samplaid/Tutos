package lu.wealins.webia.services.core.service;

import java.util.List;
import java.util.Map;

import lu.wealins.common.dto.webia.services.CheckDataContainerDTO;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.StepDTO;

public interface CheckDataService {

	/**
	 * Create Check data.
	 * 
	 * @param checkId
	 * @param workflowItemId
	 * @return
	 */
	CheckDataDTO createCheckData(Integer checkId, Integer workflowItemId);

	/**
	 * Get the check data according the worflow item id and the check id.
	 * 
	 * @param workflowItemId The worflow item id.
	 * @param checkId The check id.
	 * @return The check data.
	 */
	CheckDataDTO getCheckData(Integer workflowItemId, Integer checkId);

	/**
	 * Get the check data according the worflow item id and the check codes.
	 * 
	 * @param workflowItemId The worflow item id.
	 * @param checkCodes The check codes.
	 * @return The check data.
	 */
	Map<String, CheckDataDTO> getCheckData(Integer workflowItemId, List<String> checkCodes);

	CheckDataDTO getCheckData(Integer workflowItemId, String checkCode);

	/**
	 * Get the check data according the policy Id and the check codes.
	 * 
	 * @param policyId The policy id.
	 * @param checkCodes The check codes.
	 * @return The check data.
	 */
	CheckDataDTO getCheckData(String policyId, Integer checkId);

	/**
	 * Update the check data according its DTO representation.
	 * 
	 * @param checkData The ckeck data DTO
	 * @return The check data.
	 */
	CheckDataDTO update(CheckDataDTO checkData);

	/**
	 * Update the check data according its DTOs representation.
	 * 
	 * @param <T>
	 * 
	 * @param checkData The ckeck data DTOs
	 * @return The check data.
	 */
	CheckDataContainerDTO update(CheckDataContainerDTO checkData);

	/**
	 * check if one of the values are set in the checkData
	 * 
	 * @param checkData The ckeck data DTO
	 * @return true or false
	 */
	boolean hasValue(CheckDataDTO checkData);

	void updateContextualRules(StepDTO step);

	boolean hasYesValue(Integer workflowItemId, String paymentTypeCheckCode);
}
