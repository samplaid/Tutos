package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAgh.Row.ImpItmAghAgentHierarchies;

import lu.wealins.liability.services.core.persistence.entity.AgentHierarchyEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.AgentHierarchyDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class AgentHierarchyMapper {
	
	@Autowired
	private AgentMapper agentMapper;

	@Mappings({
			@Mapping(ignore = true, source = "masterBroker", target = "masterBroker"),
	})
	public abstract AgentHierarchyDTO asAgentHierarchyDTO(AgentHierarchyEntity in);
	
	@AfterMapping
	public AgentHierarchyDTO afterEntityMapping(AgentHierarchyEntity in, @MappingTarget AgentHierarchyDTO target) {

		target.setMasterBroker(agentMapper.asAgentLightDTO(in.getMasterBroker()));
		target.setSubBroker(agentMapper.asAgentLightDTO(in.getAgent()));

		return target;
	}

	public abstract Collection<AgentHierarchyDTO> asAgentHierarchyDTOs(Collection<AgentHierarchyEntity> in);
	
	@Mappings({
		@Mapping(source="masterBroker.agtId", target="agentId"),
		@Mapping(source="subBroker.agtId", target="writingAgent")
	})
	public abstract ImpItmAghAgentHierarchies asImpGrpAgentHierarchy(AgentHierarchyDTO in);
	
	public abstract Collection<ImpItmAghAgentHierarchies> asImpGrpAgentHierarchies(Collection<AgentHierarchyDTO> in);
	
}
