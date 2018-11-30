package lu.wealins.webia.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.webia.services.core.persistence.entity.TransactionTaxEntity;
import lu.wealins.common.dto.webia.services.TransactionTaxDTO;

@Mapper(componentModel = "spring")
public interface TransactionTaxMapper {

	@Mappings({
			@Mapping(source = "id", target = "transactionTaxId"),
			@Mapping(source = "previousTransactionTax.id", target = "previousTransactionId")
	})

	TransactionTaxDTO asTransactionTaxDTO(TransactionTaxEntity in);

	List<TransactionTaxDTO> asTransactionTaxDTOs(List<TransactionTaxEntity> in);

	TransactionTaxEntity asTransactionTaxEntity(TransactionTaxDTO in);

	List<TransactionTaxEntity> asTransactionTaxEntities(List<TransactionTaxDTO> in);

}
