package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.CodeLabelEntity;
import lu.wealins.common.dto.webia.services.CodeLabelDTO;

@Mapper(componentModel = "spring")
public interface CodeLabelMapper {

	CodeLabelEntity asCodeLabelEntity(CodeLabelDTO in);

	CodeLabelDTO asCodeLabelDTO(CodeLabelEntity in);

	Collection<CodeLabelDTO> asCodeLabelDTOs(Collection<CodeLabelEntity> in);

}
