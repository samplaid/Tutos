package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.UoptDetailEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.UoptDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface UoptDetailMapper {

	UoptDetailDTO asUoptDetailDTO(UoptDetailEntity in);

	List<UoptDetailDTO> asUoptDetailDTOs(List<UoptDetailEntity> in);

}
