package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.FundTransactionDateEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.FundTransactionDateDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, FundTransactionMapper.class })
public interface FundTransactionDateMapper {

	@Mappings({
			@Mapping(source = "fund.fdsId", target = "fdsId")
	})
	FundTransactionDateDTO asFundTransactionDateDTO(FundTransactionDateEntity in);

	List<FundTransactionDateDTO> asFundTransactionDateDTOs(List<FundTransactionDateEntity> in);

}
