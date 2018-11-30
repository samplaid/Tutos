package lu.wealins.webia.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.common.dto.liability.services.PolicyValuationHoldingDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;

@Mapper(componentModel = "spring")
public abstract class FundFormMapper {

	@Mappings({
			@Mapping(source = "fundSubType", target = "fundTp")
	})
	public abstract FundFormDTO asFundFormDTO(PolicyValuationHoldingDTO in);

}
