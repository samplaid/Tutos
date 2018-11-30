package lu.wealins.webia.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.InsuredFormDTO;
import lu.wealins.editing.common.webia.OrderedPerson;

/**
 * 
 * @author bqv55
 *
 */
@Mapper(componentModel = "spring")
public interface OrderedPersonMapper {
	// @Mappings({
	// @Mapping(target = "order", source = "rankNumber"),
	//
	// @Mapping(target = "lastName", source = "client.name"),
	// @Mapping(target = "firstName", source = "client.firstName"),
	//
	// @Mapping(target = "address.zipCode", source = "client."),
	// @Mapping(target = "address.city", source = "client."),
	// @Mapping(target = "address.country", source = "country"),
	// @Mapping(target = "address.phone", source = "mobile"),
	// @Mapping(target = "address.street", source = "addressLine2"),
	// })
	OrderedPerson asOrderedPerson(InsuredFormDTO in);

	List<OrderedPerson> asOrderedPersonList(List<InsuredFormDTO> in);

}