package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.PolicyChangeDTO;
import lu.wealins.liability.services.core.persistence.entity.PolicyChangeEntity;

@Mapper(componentModel = "spring")
public abstract class PolicyChangeMapper {
	
	public abstract PolicyChangeDTO asPolicyChangeDTO(PolicyChangeEntity entity);
	
	public abstract Collection<PolicyChangeDTO> asPolicyChangeDTOs(Collection<PolicyChangeEntity> entity);
	
}
