package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.SapMappingEntity;
import lu.wealins.common.dto.webia.services.SapMappingDTO;

@Mapper(componentModel = "spring")
public interface SapMappingMapper {

	SapMappingEntity asSapMappingEntity(SapMappingDTO in);

	SapMappingDTO asSapMappingDTO(SapMappingEntity in);

	List<SapMappingEntity> asSapMappingEntities(List<SapMappingDTO> in);

	List<SapMappingDTO> asSapMappingDTOs(List<SapMappingEntity> in);

}
