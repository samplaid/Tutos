package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.PolicyTransferEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyTransferDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class PolicyTransferMapper {

	public abstract PolicyTransferEntity asPolicyTransferEntity(PolicyTransferDTO in);

	public abstract Collection<PolicyTransferEntity> asPolicyTransferEntities(Collection<PolicyTransferDTO> in);

	public abstract PolicyTransferDTO asPolicyTransferDTO(PolicyTransferEntity entity);

	public abstract Collection<PolicyTransferDTO> asPolicyTransferDTOCollection(
			Collection<PolicyTransferEntity> entityList);

}
