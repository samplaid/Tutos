package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.BenefClauseStdEntity;
import lu.wealins.common.dto.webia.services.BenefClauseStdDTO;

@Mapper(componentModel = "spring")
public interface BenefClauseStdMapper {

	BenefClauseStdEntity asBenefClauseStdEntity(BenefClauseStdDTO in);

	BenefClauseStdDTO asBenefClauseStdDTO(BenefClauseStdEntity in);

	Collection<BenefClauseStdDTO> asBenefClauseStdDTOs(Collection<BenefClauseStdEntity> in);
}
