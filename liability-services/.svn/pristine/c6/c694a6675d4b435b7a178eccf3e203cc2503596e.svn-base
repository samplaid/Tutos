package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.DocumentRequestEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.DocumentRequestDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface DocumentRequestMapper {

	@Mappings({
			@Mapping(source = "client.cliId", target = "cliId"),
			@Mapping(source = "policy.polId", target = "polId"),
			@Mapping(source = "transaction.trnId", target = "trnId")
	})
	DocumentRequestDTO asDocumentRequestDTO(DocumentRequestEntity in);

	List<DocumentRequestDTO> asDocumentRequestDTOs(List<DocumentRequestEntity> in);

}
