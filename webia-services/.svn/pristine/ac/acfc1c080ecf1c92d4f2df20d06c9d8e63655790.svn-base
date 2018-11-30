package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.LabelEntity;
import lu.wealins.common.dto.webia.services.LabelDTO;

@Mapper(componentModel = "spring")
public interface LabelMapper {

	LabelDTO asLabelDTO(LabelEntity in);

	LabelEntity asLabelEntity(LabelDTO in);

	Collection<LabelDTO> asLabelDTOs(Collection<LabelEntity> in);

}
