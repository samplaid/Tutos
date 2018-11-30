package lu.wealins.liability.services.core.mapper;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.PolicyEventDTO;
import lu.wealins.liability.services.core.persistence.entity.PolicyEventEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, PolicyEndorsementMapper.class })
public abstract class PolicyEventMapper {

	public abstract PolicyEventDTO asPolicyEventDTO(PolicyEventEntity in);

	public abstract PolicyEventEntity asPolicyEventEntity(PolicyEventDTO in);

}
