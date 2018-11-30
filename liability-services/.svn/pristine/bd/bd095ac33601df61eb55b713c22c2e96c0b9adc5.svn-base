package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.CurrencyEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.CurrencyDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface CurrencyMapper {

	CurrencyDTO asCurrencyDTO(CurrencyEntity in);

	List<CurrencyDTO> asCurrencyDTOs(List<CurrencyEntity> in);

}