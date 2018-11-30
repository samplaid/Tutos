package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.ClientEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.OtherClientDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, ClientLinkedPersonMapper.class, ClientClaimsDetailMapper.class })
public interface OtherClientMapper {

	OtherClientDTO asOtherClientDTO(ClientEntity in);

}
