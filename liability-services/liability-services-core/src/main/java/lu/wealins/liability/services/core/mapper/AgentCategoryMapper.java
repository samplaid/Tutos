package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.AgentCategoryEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.AgentCategoryDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface AgentCategoryMapper {	
	
	AgentCategoryDTO asAgentCategoryDTO(AgentCategoryEntity in);
	List<AgentCategoryDTO> asAgentCategoryDTOs(List<AgentCategoryEntity> in);
}
