package lu.wealins.webia.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lu.wealins.common.dto.liability.services.WorkflowGeneralInformationDTO;
import lu.wealins.common.dto.webia.services.AppFormEntryDTO;
import lu.wealins.common.dto.webia.services.OperationDTO;

@Mapper(componentModel = "spring")
public abstract class OperationMapper {

	public abstract OperationDTO asOperationDTO(AppFormEntryDTO in);

	public abstract void asOperationDTO(WorkflowGeneralInformationDTO in, @MappingTarget OperationDTO out);

}
