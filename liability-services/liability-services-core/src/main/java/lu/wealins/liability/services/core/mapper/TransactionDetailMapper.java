package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.TransactionDetailEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.TransactionDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface TransactionDetailMapper {

	@Mappings({
			@Mapping(source = "transaction.trnId", target = "trnId")
	})
	TransactionDetailDTO asTransactionDetailDTO(TransactionDetailEntity in);

	List<TransactionDetailDTO> asTransactionDetailDTOs(List<TransactionDetailEntity> in);

}
