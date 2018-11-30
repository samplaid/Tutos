package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyAgentHierarchyEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyAgentHierarchyDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyAgentHierarchyMapper {
	@Mappings({
			@Mapping(source = "policy.polId", target = "polId")
	})
	PolicyAgentHierarchyDTO asPolicyAgentHierarchyDTO(PolicyAgentHierarchyEntity in);

	List<PolicyAgentHierarchyDTO> asPolicyAgentHierarchyDTOs(List<PolicyAgentHierarchyEntity> in);
}
