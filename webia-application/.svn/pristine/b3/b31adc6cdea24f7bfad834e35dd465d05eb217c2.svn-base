package lu.wealins.webia.core.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;
import lu.wealins.editing.common.webia.PolicyHolder;

/**
 * 
 * @author bqv55
 *
 */
@Mapper(componentModel = "spring")
public interface PolicyHolderMapper {

	PolicyHolder asPolicyHolder(PolicyHolderDTO in);

	List<PolicyHolder> asPolicyHolderList(List<PolicyHolderDTO> in);

	@Mappings({
		@Mapping(source = "cliId", target = "clientId"),
			@Mapping(source = "type", target = "clientRelationTp"),
			@Mapping(source = "typeNumber", target = "rankNumber"),
	})
	PolicyHolderFormDTO asPolicyHolderFormDTO(PolicyHolderDTO in);

	@Mappings({
			@Mapping(source = "clientId", target = "cliId"),
			@Mapping(source = "clientRelationTp", target = "type"),
			@Mapping(source = "rankNumber", target = "typeNumber"),
	})
	PolicyHolderDTO asPolicyHolderDTO(PolicyHolderFormDTO in);

	Collection<PolicyHolderFormDTO> asPolicyHolderFormDTO(Collection<PolicyHolderDTO> in);
}
