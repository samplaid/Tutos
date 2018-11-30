package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.webia.services.TransferSecurityDTO;
import lu.wealins.webia.services.core.persistence.entity.TransferSecurityEntity;

@Mapper(componentModel = "spring")
public interface TransferSecurityMapper {

	TransferSecurityDTO asTransferSecurityDTO(TransferSecurityEntity in);

	TransferSecurityEntity asTransferSecurityEntity(TransferSecurityDTO in);

	Collection<TransferSecurityEntity> asTransferSecurityEntities(Collection<TransferSecurityDTO> in);

	Collection<TransferSecurityDTO> asTransferSecurityDTOs(Collection<TransferSecurityEntity> in);
}