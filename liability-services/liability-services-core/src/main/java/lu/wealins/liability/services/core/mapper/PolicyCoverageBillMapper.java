package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyCoverageBillEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyCoverageBillDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyCoverageBillMapper {

	@Mappings({
			@Mapping(source = "bill.bilNo", target = "bilNo"),
			@Mapping(source = "policy.polId", target = "polId")
	})
	PolicyCoverageBillDTO toPolicyCoverageBillDTO(PolicyCoverageBillEntity in);

	List<PolicyCoverageBillDTO> toPolicyCoverageBillDTOs(List<PolicyCoverageBillEntity> in);

}
