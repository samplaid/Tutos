package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.ControlEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ControlDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface ControlMapper {

	ControlDTO asControlDTO(ControlEntity in);

	Collection<ControlDTO> asControlDTOs(Collection<ControlEntity> in);

}
