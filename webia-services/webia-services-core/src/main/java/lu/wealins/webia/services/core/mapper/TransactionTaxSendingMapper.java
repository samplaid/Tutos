package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.webia.services.TransactionTaxSendingDTO;
import lu.wealins.webia.services.core.persistence.entity.TransactionTaxSendingEntity;

@Mapper(componentModel = "spring")
public interface TransactionTaxSendingMapper {

	@Mappings({
			@Mapping(source = "transactionTax.id", target = "transactionTaxId")
	})
	TransactionTaxSendingDTO asTransactionTaxDetailsDTO(TransactionTaxSendingEntity in);

	List<TransactionTaxSendingDTO> asTransactionTaxSendingDetailsDTOs(List<TransactionTaxSendingEntity> in);



}
