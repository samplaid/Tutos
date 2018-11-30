package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.liability.services.core.business.OptionDetailService;
import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyCoverageDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, OptionDetailMapper.class })
public abstract class PolicyCoverageMapper {

	@Autowired
	private OptionDetailService optionDetailService;

	@Mappings({
			@Mapping(source = "policy.polId", target = "polId"),
	})
	public abstract PolicyCoverageDTO asPolicyCoverageDTO(PolicyCoverageEntity in);

	public abstract List<PolicyCoverageDTO> asPolicyCoverageDTOs(List<PolicyCoverageEntity> in);

	@AfterMapping
	public PolicyCoverageDTO afterEntityMapping(PolicyCoverageEntity policyCoverageEntity, @MappingTarget PolicyCoverageDTO policyCoverageDTO) {
		policyCoverageDTO.setLives(optionDetailService.getOptionDetail(optionDetailService.getLives(), policyCoverageEntity.getLivesType()));
		return policyCoverageDTO;
	}
}
