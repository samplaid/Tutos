package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.BankAccountEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.account.ClientAccountDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface ClientAccountMapper {

	@BeanMapping(resultType = ClientAccountDTO.class)
	ClientAccountDTO asClientLinkedPersonDTO(BankAccountEntity in);

	Collection<ClientAccountDTO> asClientAccountsDto(Collection<BankAccountEntity> in);
}
