package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.SapAccountingEntity;
import lu.wealins.webia.services.core.persistence.entity.SapAccountingRowNoEntity;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;

@Mapper(componentModel = "spring")
public interface SapAccountingMapper {

	SapAccountingEntity asSapAccountingEntity(SapAccountingDTO in);

	SapAccountingDTO asSapAccountingDTO(SapAccountingEntity in);

	Collection<SapAccountingDTO> asSapAccountingDTOs(Collection<SapAccountingEntity> in);

	Collection<SapAccountingEntity> asSapAccountingEntities(Collection<SapAccountingDTO> in);

	Collection<SapAccountingEntity> asSapAccountingEntitiesForNoEntity(Collection<SapAccountingRowNoEntity> in);

	Collection<SapAccountingRowNoEntity> asSapAccountingNoEntities(Collection<SapAccountingEntity> in);

	SapAccountingRowNoEntity asSapAccountingNoEntity(SapAccountingEntity in);

	SapAccountingEntity asSapAccountingEntityForNoEntity(SapAccountingRowNoEntity in);

}
