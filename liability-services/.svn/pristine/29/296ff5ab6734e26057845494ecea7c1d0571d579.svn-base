package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.BillsHistoryEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.BillsHistoryDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface BillsHistoryMapper {

	@Mappings({
			@Mapping(source = "billingRun.birId", target = "birId"),
			@Mapping(source = "policy.polId", target = "polId")
	})
	BillsHistoryDTO toBillsHistoryDTO(BillsHistoryEntity in);

	List<BillsHistoryDTO> toBillsHistoryDTOs(List<BillsHistoryEntity> in);
}
