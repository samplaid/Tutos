package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.ScoreBCFTEntity;
import lu.wealins.common.dto.webia.services.ScoreBCFTDTO;

@Mapper(componentModel = "spring")
public interface ScoreBCFTMapper {

	ScoreBCFTDTO asScoreBCFTDTO(ScoreBCFTEntity in);

	ScoreBCFTEntity asScoreBCFTEntity(ScoreBCFTDTO in);

	Collection<ScoreBCFTDTO> asScoreBCFTDTOs(Collection<ScoreBCFTEntity> in);

}
