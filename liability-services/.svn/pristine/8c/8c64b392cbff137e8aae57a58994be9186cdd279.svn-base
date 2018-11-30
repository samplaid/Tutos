package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tempuri.wssupers.WssupersImport.ImpGrpClp.Row.ImpItmClpClientLinkedPersons;

import lu.wealins.liability.services.core.persistence.entity.ClientLinkedPersonEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ClientLinkedPersonDTO;

@Mapper(componentModel = "spring", uses = { AgentMapper.class, StringToTrimString.class })
public interface ClientLinkedPersonMapper {

	@Mappings({
			@Mapping(source = "client.cliId", target = "cliId")
	})
	ClientLinkedPersonDTO asClientLinkedPersonDTO(ClientLinkedPersonEntity in);

	Collection<ClientLinkedPersonDTO> asClientLinkedPersonDTOs(Collection<ClientLinkedPersonEntity> in);

	@Mappings({
			@Mapping(source = "startDate", target = "startDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "endDate", target = "endDate", dateFormat = "yyyyMMdd")
	})
	ImpItmClpClientLinkedPersons asImpItmClpClientLinkedPersons(ClientLinkedPersonDTO in);
}
