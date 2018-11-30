package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.StatementEntity;
import lu.wealins.common.dto.webia.services.StatementDTO;

@Mapper(componentModel = "spring")
public interface StatementMapper {

	StatementDTO asStatementDTO(StatementEntity in);

	Collection<StatementDTO> asStatementDTOs(Collection<StatementEntity> in);

	StatementEntity asStatementEntity(StatementDTO in);

	Collection<StatementEntity> asStatementEntities(Collection<StatementDTO> in);
}
