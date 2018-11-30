package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.webia.services.core.persistence.entity.TransactionTaxDetailsEntity;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;

@Mapper(componentModel = "spring")
public interface TransactionTaxDetailsMapper {

	@Mappings({
			@Mapping(source = "transactionTax.id", target = "transactionTaxId")
	})
	TransactionTaxDetailsDTO asTransactionTaxDetailsDTO(TransactionTaxDetailsEntity in);

	List<TransactionTaxDetailsDTO> asTransactionTaxDetailsDTOs(List<TransactionTaxDetailsEntity> in);

	@Mappings({
			@Mapping(source = "transactionTaxId", target = "transactionTax.id")
	})
	TransactionTaxDetailsEntity asTransactionTaxDetailsEntity(TransactionTaxDetailsDTO in);

	List<TransactionTaxDetailsEntity> asTransactionTaxDetailsEntities(List<TransactionTaxDetailsDTO> in);

}
