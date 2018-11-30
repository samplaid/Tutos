package lu.wealins.liability.services.core.mapper.factory;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;

@Mapper(componentModel = "spring")
public class FundFactory {

	public FundDTO createFundDTO() {
		return new FundDTO();
	}

	public FundLiteDTO createFundLiteDTO() {
		return new FundLiteDTO();
	}

}
