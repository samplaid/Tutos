package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.CurrencyEntity;
import lu.wealins.liability.services.core.persistence.entity.ExchangeRateEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.CurrencyDTO;
import lu.wealins.common.dto.liability.services.ExchangeRateDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface ExchangeRateMapper {

	ExchangeRateDTO asExchangeRateDTO(ExchangeRateEntity in);

	List<ExchangeRateDTO> asExchangeRateDTOs(List<ExchangeRateEntity> in);

}