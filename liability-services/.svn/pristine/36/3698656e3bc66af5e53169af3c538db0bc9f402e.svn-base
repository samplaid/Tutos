package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.OptionDetailEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.OptionDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface OptionDetailMapper {

	@Mappings({
		@Mapping(source = "option.optId", target = "optId")
	})
	OptionDetailDTO asOptionDetailDTO(OptionDetailEntity in);

	List<OptionDetailDTO> asOptionDetailDTOs(List<OptionDetailEntity> in);

}