package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.MetadataDTO;
import uk.co.liss.webservice.workflowitem.domain.xsd.WorkflowItemMetadata;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface MetadataMapper {

	MetadataDTO asMetadataDTO(WorkflowItemMetadata in);

	Collection<MetadataDTO> asMetadataDTOs(Collection<WorkflowItemMetadata> in);
}
