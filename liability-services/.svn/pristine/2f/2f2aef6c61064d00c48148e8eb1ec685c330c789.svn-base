package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.PolicyFundInstructionEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyFundInstructionDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, PolicyFundInstructionDetailMapper.class, FundTransactionMapper.class })
public interface PolicyFundInstructionMapper {

	@Mappings({
			@Mapping(source = "policy.polId", target = "polId"),
			@Mapping(source = "transaction.trnId", target = "trnId")
	})
	PolicyFundInstructionDTO asPolicyFundInstructionDTO(PolicyFundInstructionEntity in);

	List<PolicyFundInstructionDTO> asPolicyFundInstructionDTOs(List<PolicyFundInstructionEntity> in);

}
