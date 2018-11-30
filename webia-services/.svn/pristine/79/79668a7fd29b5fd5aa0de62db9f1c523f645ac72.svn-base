package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.CheckDataEntity;
import lu.wealins.common.dto.webia.services.CheckDataDTO;

@Mapper(componentModel = "spring")
public interface CheckDataMapper {

	CheckDataEntity asCheckDataEntity(CheckDataDTO in);

	CheckDataDTO asCheckDataDTO(CheckDataEntity in);

	Collection<CheckDataDTO> asCheckDataDTOs(Collection<CheckDataEntity> in);
}
