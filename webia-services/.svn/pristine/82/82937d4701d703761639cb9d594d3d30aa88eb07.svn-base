package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.DRMMappingDTO;
import lu.wealins.webia.services.core.persistence.entity.DRMMappingEntity;

@Mapper(componentModel = "spring")
public interface DRMMappingMapper {

	DRMMappingEntity asDRMMappingEntity(DRMMappingDTO in);

	DRMMappingDTO asDRMMappingDTO(DRMMappingEntity in);

	List<DRMMappingEntity> asDRMMappingEntities(List<DRMMappingDTO> in);

	List<DRMMappingDTO> asDRMMappingDTOs(List<DRMMappingEntity> in);

}
