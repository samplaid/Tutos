package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.services.core.persistence.entity.TransferEntity;

@Mapper(componentModel = "spring", uses = { TransferSecurityMapper.class })
public interface TransferMapper {

	TransferDTO asTransferDTO(TransferEntity in);

	Collection<TransferDTO> asTransferDTOs(Collection<TransferEntity> in);

	TransferEntity asTransferEntity(TransferDTO in);

	Collection<TransferEntity> asTransferEntities(Collection<TransferDTO> in);
}
