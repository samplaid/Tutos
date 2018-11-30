package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.ApplicationParameterEntity;
import lu.wealins.common.dto.webia.services.ApplicationParameterDTO;

@Mapper(componentModel = "spring")
public interface ApplicationParameterMapper {

	ApplicationParameterEntity asApplicationParameterEntity(ApplicationParameterDTO in);

	ApplicationParameterDTO asApplicationParameterDTO(ApplicationParameterEntity in);

	Collection<ApplicationParameterDTO> asApplicationParameterDTOs(Collection<ApplicationParameterEntity> in);

}
