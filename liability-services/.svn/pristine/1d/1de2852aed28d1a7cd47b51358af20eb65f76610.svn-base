package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyFundInstructionDetailEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyFundInstructionDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyFundInstructionDetailMapper {

	@Mappings({
			@Mapping(source = "policyFundInstruction.pfiId", target = "pfiId")
	})
	PolicyFundInstructionDetailDTO asPolicyFundInstructionDetailDTO(PolicyFundInstructionDetailEntity in);

	List<PolicyFundInstructionDetailDTO> asPolicyFundInstructionDetailDTOs(List<PolicyFundInstructionDetailEntity> in);

}
