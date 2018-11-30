package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.BenefClauseFormEntity;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;

@Mapper(componentModel = "spring")
public interface BenefClauseFormMapper {

	BenefClauseFormEntity asBenefClauseFormEntity(BenefClauseFormDTO in);

	BenefClauseFormDTO asBenefClauseFormDTO(BenefClauseFormEntity in);

	Collection<BenefClauseFormDTO> asBenefClauseFormDTOs(Collection<BenefClauseFormEntity> in);
}
