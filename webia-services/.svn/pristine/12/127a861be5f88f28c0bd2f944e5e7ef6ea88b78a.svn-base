package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.services.core.persistence.entity.TransactionFormEntity;

@Mapper(componentModel = "spring", uses = { FundTransactionFormMapper.class, TransferMapper.class })
public abstract class TransactionFormMapper {

	public abstract TransactionFormDTO asTransactionFormDTO(TransactionFormEntity in);

	public abstract TransactionFormEntity asTransactionFormEntity(TransactionFormDTO in);

	public abstract Collection<TransactionFormDTO> asTransactionFormDTOs(Collection<TransactionFormEntity> in);

}
