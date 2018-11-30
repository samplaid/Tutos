package lu.wealins.webia.core.service;

import java.util.Collection;
import java.util.List;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.CheckStepDTO;

public interface MetadataService {

	/**
	 * Create metadata.
	 * 
	 * @param name The metadata name.
	 * @param value The value.
	 * @return The metadata.
	 */
	MetadataDTO createMetadata(String name, String value);

	/**
	 * Create a metadata and it to the workflow item data.
	 * 
	 * @param workflowItemData The workflow item data.
	 * @param name The metadata name.
	 * @param value The metadata value.
	 */
	void addMetadata(WorkflowItemDataDTO workflowItemData, String name, String value);
	
	/**
	 * Adds existing metadata to the workflow item data.
	 * 
	 * @param workflowItemData The workflow item data.
	 * @param newMetadata the metadata to be added.
	 */
	void addMetadata(WorkflowItemDataDTO workflowItemData, List<MetadataDTO> newMetadata);

	/**
	 * Create the workflow item.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param usrId The user id.
	 * @param metadata The metadata.
	 * @return The workflow item.
	 */
	WorkflowItemDataDTO createWorkflowItemData(Long workflowItemId, String usrId, MetadataDTO... metadata);

	/**
	 * Create the workflow item.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param metadata The metadata.
	 * @param usrId The user id.
	 * @return The workflow item.
	 */
	WorkflowItemDataDTO createWorkflowItemData(Long workflowItemId, List<MetadataDTO> metadata, String usrId);

	/**
	 * Create metadata linked to the check steps.
	 * 
	 * @param checkSteps The check steps.
	 * @return The metadata.
	 */
	List<MetadataDTO> createMetadata(Collection<CheckStepDTO> checkSteps);

	/**
	 * Get metadata according to the workflow item id and the metadata name.
	 * 
	 * @param workflowItemId The workflow item id.
	 * @param metadataName The metadata name.
	 * @param usrId The user id.
	 * @return The metadata.
	 */
	MetadataDTO getMetadata(String workflowItemId, String metadataName, String usrId);

	boolean isYes(Integer workflowItemId, Metadata metadata, String usrId);

	boolean isNo(Integer workflowItemId, Metadata metadata, String usrId);
}
