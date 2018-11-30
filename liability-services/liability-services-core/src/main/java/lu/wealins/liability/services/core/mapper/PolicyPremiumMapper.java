package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.PolicyPremiumEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.PolicyPremiumDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public interface PolicyPremiumMapper {

	PolicyPremiumDTO asPolicyPremiumDTO(PolicyPremiumEntity in);

	List<PolicyPremiumDTO> asPolicyPremiumDTOs(List<PolicyPremiumEntity> in);

}
