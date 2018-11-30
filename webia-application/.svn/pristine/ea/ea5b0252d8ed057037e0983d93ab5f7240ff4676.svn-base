package lu.wealins.webia.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.editing.common.webia.User;
import lu.wealins.webia.ws.rest.request.EditingUser;

/**
 * 
 * @author bqv55
 *
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mappings({
			@Mapping(target = "userTrigram", source = "id"),
			@Mapping(target = "firstname", source = "firstName"),
			@Mapping(target = "phone", source = "phone")
	})
	User asUser(EditingUser in);
}