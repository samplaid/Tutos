package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.liability.services.core.business.PolicyService;
import lu.wealins.liability.services.core.persistence.entity.PolicyAgentShareEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, ProductValueMapper.class })
public abstract class PolicyAgentShareMapper {

	@Autowired
	private PolicyService policyService;
	@Autowired
	private AgentMapper agentMapper;

	@Mappings({
			@Mapping(ignore = true, source = "agent", target = "agent"),
	})
	public abstract PolicyAgentShareDTO asPolicyAgentShareDTO(PolicyAgentShareEntity in);

	@Mappings({
			@Mapping(source = "agent.agtId", target = "agent.agtId"),
	})
	public abstract PolicyAgentShareEntity asPolicyAgentShareEntity(PolicyAgentShareDTO in);

	public abstract Collection<PolicyAgentShareDTO> asPolicyAgentShareDTOs(Collection<PolicyAgentShareEntity> in);

	@AfterMapping
	public PolicyAgentShareDTO afterEntityMapping(PolicyAgentShareEntity in, @MappingTarget PolicyAgentShareDTO out) {

		String polId = in.getPolId();
		if (StringUtils.isNotBlank(polId)) {
			out.setContractReference(policyService.getBrokerRefContract(polId));
		}
		out.setAgent(agentMapper.asAgentLightDTO(in.getAgent()));

		return out;
	}
}
