package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.PolicyEndorsementDTO;
import lu.wealins.liability.services.core.persistence.entity.PolicyEndorsementEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, PolicyEventMapper.class })
public interface PolicyEndorsementMapper {

	PolicyEndorsementDTO asPolicyEndorsementDTO(PolicyEndorsementEntity in);

	List<PolicyEndorsementDTO> asPolicyEndorsementDTOs(List<PolicyEndorsementEntity> in);

	PolicyEndorsementEntity asPolicyEndorsementEntity(PolicyEndorsementDTO in);

}
