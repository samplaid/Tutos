package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.TransactionEntity;
import lu.wealins.common.dto.webia.services.TransactionDTO;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

	TransactionDTO asTransactionDTO(TransactionEntity in);

	Collection<TransactionDTO> asTransactionDTOs(Collection<TransactionEntity> in);

	TransactionEntity asTransactionEntity(TransactionDTO in);

	Collection<TransactionEntity> asTransactionEntities(Collection<TransactionDTO> in);
}
