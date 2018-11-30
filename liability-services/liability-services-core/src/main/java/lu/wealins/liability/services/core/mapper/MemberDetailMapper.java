package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.MemberDetailEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.MemberDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, MemberHistoryDetailMapper.class })
public interface MemberDetailMapper {

	@Mappings({
			@Mapping(source = "client.cliId", target = "cliId"),
			@Mapping(source = "generalNote.ponId", target = "ponId"),
			@Mapping(source = "policy.polId", target = "polId")
	})
	MemberDetailDTO asMemberDetailDTO(MemberDetailEntity in);

	List<MemberDetailDTO> asMemberDetailDTOs(List<MemberDetailEntity> in);

}
