package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.PartnerFormEntity;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;

@Mapper(componentModel = "spring")
public interface PartnerFormMapper {

	PartnerFormDTO asPartnerFormDTO(PartnerFormEntity in);

	PartnerFormEntity asPartnerFormEntity(PartnerFormDTO in);

	Collection<PartnerFormDTO> asPartnerFormDTOs(Collection<PartnerFormEntity> in);

}
