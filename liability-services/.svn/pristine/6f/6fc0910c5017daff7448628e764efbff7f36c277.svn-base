package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import uk.co.liss.webservice.workflowitem.domain.xsd.WorkflowItemData;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface WorkflowGeneralInformationMapper {

	WorkflowGeneralInformationDTO asWorkflowGeneralInformationDTO(WorkflowItemData in);

}
