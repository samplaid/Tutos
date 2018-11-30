package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.AccountPaymentDTO;
import lu.wealins.webia.services.core.persistence.entity.AccountPaymentEntity;

@Mapper(componentModel = "spring")
public interface AccountPaymentMapper {

	AccountPaymentDTO asAccountPaymentDTO(AccountPaymentEntity in);

	Collection<AccountPaymentDTO> asAccountPaymentDTOs(Collection<AccountPaymentEntity> in);

	AccountPaymentEntity asAccountPaymentEntity(AccountPaymentDTO in);
}
