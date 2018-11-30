package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgb.Row.ImpItmAgbAgentBankAccounts;

import lu.wealins.liability.services.core.persistence.entity.AgentBankAccountEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.AgentBankAccountDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface AgentBankAccountMapper {

	AgentBankAccountDTO asAgentBankAccountDTO(AgentBankAccountEntity in);

	Collection<AgentBankAccountDTO> asAgentBankAccountDTOs(Collection<AgentBankAccountEntity> in);

	@Mappings({
			@Mapping(source = "agent0", target = "agent"),
	})
	ImpItmAgbAgentBankAccounts asImpGrpAgentBankAccount(AgentBankAccountDTO in);

	Collection<ImpItmAgbAgentBankAccounts> asImpGrpAgentBankAccounts(Collection<AgentBankAccountDTO> in);
}
