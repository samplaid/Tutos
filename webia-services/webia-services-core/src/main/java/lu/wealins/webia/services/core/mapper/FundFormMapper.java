package lu.wealins.webia.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import lu.wealins.webia.services.core.persistence.entity.FundFormEntity;
import lu.wealins.common.dto.webia.services.FundFormDTO;

@Mapper(componentModel = "spring", uses = {EncashmentFundFormMapper.class})
public interface FundFormMapper {

	FundFormDTO asFundFormDTO(FundFormEntity in);

	FundFormEntity asFundFormEntity(FundFormDTO in);

	Collection<FundFormDTO> asFundFormDTOs(Collection<FundFormEntity> in);

	
}
