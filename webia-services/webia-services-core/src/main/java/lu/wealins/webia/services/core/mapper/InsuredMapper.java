package lu.wealins.webia.services.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.common.dto.webia.services.InsuredFormDTO;

@Mapper(componentModel = "spring")
public interface InsuredMapper {

	InsuredFormDTO asInsuredDTO(ClientFormEntity in);

}
