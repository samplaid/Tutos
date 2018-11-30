package lu.wealins.liability.services.core.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.GeneralNoteEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.GeneralNoteDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, MemberDetailMapper.class })
public interface GeneralNoteMapper {

	@Mappings({
			@Mapping(source = "client.cliId", target = "cliId"),
			@Mapping(source = "agent.agtId", target = "agtId"),
			@Mapping(source = "policy.polId", target = "polId"),
			@Mapping(source = "policyRequirement.prqId", target = "prqId")
	})
	GeneralNoteDTO asGeneralNoteDTO(GeneralNoteEntity in);

	List<GeneralNoteDTO> asGeneralNoteDTOs(Set<GeneralNoteEntity> in);
}
