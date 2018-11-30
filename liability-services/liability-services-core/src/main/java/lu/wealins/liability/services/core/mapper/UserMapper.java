package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.UserDTO;
import lu.wealins.liability.services.core.persistence.entity.UserEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface UserMapper {

	UserDTO asUserDTO(UserEntity in);

}
