package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.liability.services.core.business.OptionDetailService;
import lu.wealins.liability.services.core.persistence.entity.FundPriceEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.FundPriceDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, OptionDetailMapper.class })
public abstract class FundPriceMapper {

	@Autowired
	private OptionDetailService optionDetailService;

	public abstract FundPriceDTO asFundPriceDTO(FundPriceEntity in);

	public abstract List<FundPriceDTO> asFundPriceDTOs(List<FundPriceEntity> in);

	@AfterMapping
	public FundPriceDTO afterEntityMapping(FundPriceEntity fundPriceEntity, @MappingTarget FundPriceDTO fundPriceDTO) {
		fundPriceDTO.setType(optionDetailService.getOptionDetail(optionDetailService.getPriceTypes(), fundPriceEntity.getPriceType()));
		return fundPriceDTO;
	}
}
