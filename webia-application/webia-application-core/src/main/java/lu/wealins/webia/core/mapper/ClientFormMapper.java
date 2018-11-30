package lu.wealins.webia.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.webia.services.ClientFormDTO;

@Mapper(componentModel = "spring")
public interface ClientFormMapper {

	@Mappings({
			@Mapping(source = "cliId", target = "clientId"),
			@Mapping(source = "roleNumber", target = "clientRelationTp"),
	})
	ClientFormDTO asClientFormDTO(OtherClientDTO in);

	Collection<ClientFormDTO> asClientFormDTOs(Collection<OtherClientDTO> in);

	@Mappings({
			@Mapping(source = "clientId", target = "cliId"),
			@Mapping(source = "clientRelationTp", target = "roleNumber"),
	})
	OtherClientDTO asOtherClientDTO(ClientFormDTO in);

	Collection<OtherClientDTO> asOtherClientDTOs(Collection<ClientFormDTO> in);

}
