package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.MemberHistoryDetailEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.MemberHistoryDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface MemberHistoryDetailMapper {

	@Mappings({
			@Mapping(source = "memberDetail.memId", target = "memId")
	})
	MemberHistoryDetailDTO asMemberHistoryDetailDTO(MemberHistoryDetailEntity in);

	List<MemberHistoryDetailDTO> asMemberHistoryDetailDTOs(List<MemberHistoryDetailEntity> in);
}
