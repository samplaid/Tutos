package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.FundTransactionFormDTO;
import lu.wealins.webia.services.core.persistence.entity.FundTransactionFormEntity;

@Mapper(componentModel = "spring")
public abstract class FundTransactionFormMapper {

	public abstract FundTransactionFormDTO asFundTransactionFormDTO(FundTransactionFormEntity in);

	public abstract FundTransactionFormEntity asFundTransactionFormEntity(FundTransactionFormDTO in);

	public abstract Collection<FundTransactionFormEntity> asFundTransactionFormEntities(Collection<FundTransactionFormDTO> in);

	public abstract Collection<FundTransactionFormDTO> asFundTransactionFormDTOs(Collection<FundTransactionFormEntity> in);

}