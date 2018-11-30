package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import uk.co.liss.webservice.workflowitem.domain.xsd.WorkflowItemData;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, MetadataMapper.class })
public interface WorkflowItemDataMapper {

	@Mappings({
			@Mapping(source = "workflowItemMetadatas", target = "metadata"),
	})
	WorkflowItemDataDTO asWorkflowItemDataDTO(WorkflowItemData in);
}
