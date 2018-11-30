package lu.wealins.webia.core.service.helper.metadata;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WebiaWorkflowUserService;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.WorkflowUserDTO;

@Component
public class CpsMetadataHelper {
	
	@Autowired
	private MetadataService metadataService;
	
	@Autowired
	private WebiaWorkflowUserService webiaWorkflowUserService;
	
	public List<MetadataDTO> createFirstCpsMetadata(String cpsUserCode) {
		return createCpsMetadata(cpsUserCode, Metadata.FIRST_CPS_USER, Metadata.FIRST_CPS_USER_FIRSTNAME);
	}
	
	public List<MetadataDTO> createSecondCpsMetadata(String cpsUserCode) {
		//Add SubscriptionMetadata.SECOND_CPS_USER_FIRSTNAME as soon as E-lissia is ready for CPS 2.
		return createCpsMetadata(cpsUserCode, Metadata.SECOND_CPS_USER, null);
	}
	
	public String getFirstCpsUser(String workflowItemId, String usrId) {
		return getCpsUser(workflowItemId, Metadata.FIRST_CPS_USER, usrId);
	}

	public String getSecondCpsUser(String workflowItemId, String usrId) {
		return getCpsUser(workflowItemId, Metadata.SECOND_CPS_USER, usrId);
	}

	public String getCpsUser(String workflowItemId, Metadata metadata, String usrId) {
		Assert.notNull(workflowItemId);
		Assert.notNull(metadata);
		Assert.isTrue(metadata == Metadata.FIRST_CPS_USER || metadata == Metadata.SECOND_CPS_USER, "Only cps user metadata is supported");

		MetadataDTO metadataDTO = metadataService.getMetadata(workflowItemId + "", metadata.getMetadata(), usrId);
		if (metadataDTO != null && !StringUtils.isEmpty(metadataDTO.getValue())) {
			return metadataDTO.getValue();
		}

		return null;
	}

	private List<MetadataDTO> createCpsMetadata(String cpsUserCode, Metadata userCodeKey, Metadata userFirstNameKey) {
		
		List<MetadataDTO> metadata = new ArrayList<>();
		
		metadata.add(metadataService.createMetadata(userCodeKey.getMetadata(), cpsUserCode));
		if(userFirstNameKey != null) {
			String cpsFirstname = firstNameFromUserCode(cpsUserCode);
			metadata.add(metadataService.createMetadata(userFirstNameKey.getMetadata(), cpsFirstname));
		}
		return metadata;
	}
	
	private String firstNameFromUserCode(String cpsUserCode) {
		if(StringUtils.isEmpty(cpsUserCode)) {
			return null;
		}
		WorkflowUserDTO cpsUser = webiaWorkflowUserService.getWorkflowUser(cpsUserCode);
		return cpsUser != null ? cpsUser.getName0() : null;
	}
}
