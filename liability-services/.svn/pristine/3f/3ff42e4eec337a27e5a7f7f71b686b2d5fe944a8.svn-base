package lu.wealins.liability.services.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tempuri.wssupdagt.WssupdagtImport.ImpAgtAgents;

import lu.wealins.liability.services.core.mapper.factory.AgentFactory;
import lu.wealins.liability.services.core.persistence.entity.AgentEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;

@Mapper(componentModel = "spring", uses = { AgentFactory.class, AssetManagerStrategyMapper.class, AgentBankAccountMapper.class, AgentHierarchyMapper.class, AgentContactMapper.class,
		StringToTrimString.class })
public interface AgentMapper {

	@Mappings({
			@Mapping(source = "agentCategory.acaId", target = "category"),
			@Mapping(source = "country.ctyId", target = "country"),
			@Mapping(source = "country.ptCode", target = "countryCode")
	})
	@BeanMapping(resultType = AgentDTO.class)
	AgentDTO asAgentDTO(AgentEntity in);

	default List<AgentDTO> asAgentDTOs(Collection<AgentEntity> in) {
		List<AgentDTO> agents = new ArrayList<>();

		in.forEach(x -> agents.add(asAgentDTO(x)));

		return agents;
	}

	@Mappings({
			@Mapping(source = "category", target = "agentCategory.acaId"),
			@Mapping(source = "country", target = "country.ctyId"),
			@Mapping(source = "countryCode", target = "country.ptCode"),
			
			@Mapping(source = "assetManagerStrategies", target = "assetManagerStrategies", ignore = true),
			@Mapping(source = "bankAccounts", target = "bankAccounts", ignore = true),
			@Mapping(source = "agentContacts", target = "agentContacts", ignore = true),
			@Mapping(source = "agentHierarchies", target = "agentHierarchies", ignore = true),
	})
	AgentEntity asAgentEntityWithOwnProperties(AgentDTO in);

	Collection<AgentEntity> asAgentEntitiesWithOwnProperties(Collection<AgentDTO> in);

	@Mappings({
			@Mapping(source = "agentCategory.acaId", target = "category"),
			@Mapping(source = "country.ctyId", target = "country"),
			@Mapping(source = "country.ptCode", target = "countryCode"),
			@Mapping(source = "crmRefererence", target = "crmId")
	})
	@BeanMapping(resultType = AgentLightDTO.class)
	AgentLightDTO asAgentLightDTO(AgentEntity in);

	default List<AgentLightDTO> asAgentLightDTOs(List<AgentEntity> in) {
		List<AgentLightDTO> agents = new ArrayList<>();

		in.forEach(x -> agents.add(asAgentLightDTO(x)));

		return agents;
	}

	@Mappings({
			@Mapping(source = "in.appointmentDate", target = "appointmentDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "in.effectiveDate", target = "effectiveDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "in.nextCategoryDate", target = "nextCategoryDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "in.sdateLastStmtRun", target = "sdateLastStmtRun", dateFormat = "yyyyMMdd"),
			@Mapping(source = "in.dateLastStmtRunTo", target = "dateLastStmtRunTo", dateFormat = "yyyyMMdd"),
			@Mapping(source = "in.dateOfTermination", target = "dateOfTermination", dateFormat = "yyyyMMdd"),
			@Mapping(source = "in.lastCategoryChange", target = "lastCategoryChange", dateFormat = "yyyyMMdd")
	})
	ImpAgtAgents asImpAgtAgents(AgentDTO in);

}
