package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.tempuri.wssupdagt.WssupdagtImport.ImpGrpAco.Row.ImpItmAcoAgentContacts;

import lu.wealins.liability.services.core.persistence.entity.AgentContactEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class AgentContactMapper {

	@Autowired
	private AgentMapper agentMapper;

	@Mappings({
			@Mapping(source = "agent.agtId", target = "agentId"),
			@Mapping(ignore = true, source = "contact", target = "contact"),
	})
	public abstract AgentContactLiteDTO asAgentContactLiteDTO(AgentContactEntity in);

	@AfterMapping
	public AgentContactLiteDTO afterEntityMapping(AgentContactEntity in, @MappingTarget AgentContactLiteDTO target) {

		target.setContact(agentMapper.asAgentLightDTO(in.getContact()));

		return target;
	}

	public abstract Collection<AgentContactLiteDTO> asAgentContactLiteDTOs(Collection<AgentContactEntity> in);

	@Mappings({
			@Mapping(source = "agent.agtId", target = "agentId"),
			@Mapping(ignore = true, source = "contact", target = "contact"),
	})
	public abstract AgentContactDTO asAgentContactDTO(AgentContactEntity in);

	@AfterMapping
	public AgentContactDTO afterEntityMapping(AgentContactEntity in, @MappingTarget AgentContactDTO target) {

		target.setContact(agentMapper.asAgentDTO(in.getContact()));

		return target;
	}

	public abstract Collection<AgentContactDTO> asAgentContactDTOs(Collection<AgentContactEntity> in);

	@Mappings({
			@Mapping(source = "agentId", target = "agent.agtId"),
			@Mapping(ignore = true, source = "contact", target = "contact"),

	})
	public abstract AgentContactEntity asAgentContactEntity(AgentContactDTO in);

	@AfterMapping
	public AgentContactEntity afterEntityMapping(AgentContactDTO in, @MappingTarget AgentContactEntity target) {

		target.setContact(agentMapper.asAgentEntityWithOwnProperties(in.getContact()));

		return target;
	}

	public abstract Collection<AgentContactEntity> asAgentContactEntities(Collection<AgentContactDTO> in);

	@Mappings({
			@Mapping(source = "agentId", target = "agentNo"),
			@Mapping(source = "contact.agtId", target = "contactId")
	})
	public abstract ImpItmAcoAgentContacts asImpGrpAgentContact(AgentContactLiteDTO in);

	public abstract Collection<ImpItmAcoAgentContacts> asImpGrpAgentContacts(Collection<AgentContactLiteDTO> in);

}
