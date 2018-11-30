package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.CheckDataDTO;
import lu.wealins.common.dto.webia.services.CheckStepDTO;
import lu.wealins.common.dto.webia.services.CheckWorkflowDTO;
import lu.wealins.common.dto.webia.services.enums.CheckType;
import lu.wealins.webia.core.service.LiabilityWorkflowService;
import lu.wealins.webia.core.service.MetadataService;


@Service
public class MetadataServiceImpl implements MetadataService {

	private static final String YES = "YES";
	private static final String NO = "NO";
	private static final String WORKFLOW_ITEM_ID_CANNOT_BE_NULL = "Workflow item id cannot be null.";
	private static final String WORKFLOW_ITEM_DATA_CANNOT_BE_NULL = "Workflow item data cannot be null.";
	private static final String METADATA_CANNOT_BE_NULL = "Metadata cannot be null.";
	private static final String METADATA_NAME_CANNOT_BE_NULL = "Metadata name cannot be null.";

	@Autowired
	private LiabilityWorkflowService workflowService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.MetadataService#createWorkflowItemData(java.lang.Long, java.lang.String, lu.wealins.common.dto.liability.services.MetadataDTO[])
	 */
	@Override
	public WorkflowItemDataDTO createWorkflowItemData(Long workflowItemId, String usrId, MetadataDTO... metadata) {
		List<MetadataDTO> list = new ArrayList<>();
		for (MetadataDTO m : metadata) {
			if(m != null) {
				list.add(m);
			}
		}
		return createWorkflowItemData(workflowItemId, list, usrId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.MetadataService#createWorkflowItemData(java.lang.Long, java.util.List, java.lang.String)
	 */
	@Override
	public WorkflowItemDataDTO createWorkflowItemData(Long workflowItemId, List<MetadataDTO> metadata, String usrId) {
		Assert.notNull(workflowItemId, WORKFLOW_ITEM_ID_CANNOT_BE_NULL);
		Assert.notNull(metadata, METADATA_CANNOT_BE_NULL);

		WorkflowItemDataDTO workflowItemDataDTO = new WorkflowItemDataDTO();
		workflowItemDataDTO.setWorkflowItemId(workflowItemId);
		workflowItemDataDTO.setMetadata(metadata);
		workflowItemDataDTO.setUsrId(usrId);
		return workflowItemDataDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.MetadataService#createMetadata(java.lang.String, java.lang.String)
	 */
	@Override
	public MetadataDTO createMetadata(String name, String value) {
		Assert.notNull(name, METADATA_NAME_CANNOT_BE_NULL);

		MetadataDTO metadata = new MetadataDTO();
		metadata.setName(name);
		metadata.setValue(value);
		return metadata;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.MetadataService#addMetadata(lu.wealins.common.dto.liability.services.WorkflowItemDataDTO, java.lang.String, java.lang.String)
	 */
	@Override
	public void addMetadata(WorkflowItemDataDTO workflowItemData, String name, String value) {
		Assert.notNull(workflowItemData, WORKFLOW_ITEM_DATA_CANNOT_BE_NULL);

		Collection<MetadataDTO> metadata = new ArrayList<>(workflowItemData.getMetadata());
		metadata.add(createMetadata(name, value));

		workflowItemData.setMetadata(metadata);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.MetadataService#addMetadata(lu.wealins.common.dto.liability.services.WorkflowItemDataDTO, java.util.List)
	 */
	@Override
	public void addMetadata(WorkflowItemDataDTO workflowItemData, List<MetadataDTO> newMetadata) {
		Assert.notNull(workflowItemData, WORKFLOW_ITEM_DATA_CANNOT_BE_NULL);

		Collection<MetadataDTO> metadata = new ArrayList<>(workflowItemData.getMetadata());
		metadata.addAll(newMetadata);

		workflowItemData.setMetadata(metadata);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.MetadataService#createMetadata(java.util.Collection)
	 */
	@Override
	public List<MetadataDTO> createMetadata(Collection<CheckStepDTO> checkSteps) {
		List<MetadataDTO> metadataLst = new ArrayList<MetadataDTO>();
		for (CheckStepDTO checkStep : checkSteps) {
			String metadata = checkStep.getMetadata();
			if (metadata != null) {
				CheckWorkflowDTO check = checkStep.getCheck();
				if (check == null) {
					continue;
				}
				CheckDataDTO checkData = check.getCheckData();
				if (checkData == null) {
					continue;
				}
				String value = null;
				if (CheckType.AMOUNT.getType().equals(check.getCheckType()) && checkData.getDataValueAmount() != null) {
					value = checkData.getDataValueAmount().toString();
				} else if (CheckType.DATE.getType().equals(check.getCheckType()) && checkData.getDataValueDate() != null) {
					value = checkData.getDataValueDate().toString();
				} else if (CheckType.NUMBER.getType().equals(check.getCheckType()) && checkData.getDataValueNumber() != null) {
					value = checkData.getDataValueNumber().toString();
				} else if (CheckType.TEXT.getType().equals(check.getCheckType()) || CheckType.LIST.getType().equals(check.getCheckType())) {
					value = checkData.getDataValueText();
				} else if (CheckType.YES_NO.getType().equals(check.getCheckType()) || CheckType.YES_NO_NA.getType().equals(check.getCheckType())) {
					value = checkData.getDataValueYesNoNa();
				} else {
					throw new IllegalStateException("Unknown check type.");
				}

				metadataLst.add(createMetadata(metadata, value));
			}
		}

		return metadataLst;
	}

	private Collection<MetadataDTO> getMetadata(String workflowItemId, String usrId) {
		WorkflowItemDataDTO workflowItem = workflowService.getWorkflowItem(workflowItemId, usrId);

		if (workflowItem == null) {
			return new ArrayList<MetadataDTO>();
		}

		return workflowItem.getMetadata();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.MetadataService#getMetadata(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public MetadataDTO getMetadata(String workflowItemId, String metadataName, String usrId) {
		Collection<MetadataDTO> metadata = getMetadata(workflowItemId, usrId);

		return metadata.stream().filter(x -> metadataName.equals(x.getName())).findFirst().orElse(null);
	}

	@Override
	public boolean isNo(Integer workflowItemId, Metadata metadata, String usrId) {
		MetadataDTO metadataDTO = getMetadata(workflowItemId + "", metadata.getMetadata(), usrId);
		if (metadataDTO == null) {
			return false;
		}

		return metadataDTO.getValue().equals(NO);
	}

	@Override
	public boolean isYes(Integer workflowItemId, Metadata metadata, String usrId) {
		MetadataDTO metadataDTO = getMetadata(workflowItemId + "", metadata.getMetadata(), usrId);
		if (metadataDTO == null) {
			return false;
		}

		return metadataDTO.getValue().equals(YES);
	}

}
